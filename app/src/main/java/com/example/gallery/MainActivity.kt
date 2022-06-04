package com.example.gallery
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.adapter.MyAdapter
import com.example.gallery.data.DataSource


class MainActivity : AppCompatActivity() {
    private var mCurrentAnimator: Animator? = null
    private var mShortAnimationDuration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShortAnimationDuration = resources.getInteger(
            android.R.integer.config_shortAnimTime)

        init()
    }

    private fun init() {
        val myDataset = DataSource().loadImages()
        val recyclerView: RecyclerView = findViewById(R.id.rc_view)
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        recyclerView.adapter = MyAdapter(this, myDataset)
    }

    fun openImage(view: View) {
        val imageView : ImageView = findViewById(R.id.image)
        val cl : ConstraintLayout = findViewById(R.id.cl2)

        imageView.layoutParams.height = cl.layoutParams.height
        imageView.layoutParams.width = cl.layoutParams.width
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        mCurrentAnimator?.cancel()

        val expandedImageView = findViewById<View>(R.id.expanded_image) as ImageView
        expandedImageView.setImageResource(imageResId)

        val startBounds = Rect()
        val finalBounds = Rect()
        val globalOffset = Point()

        thumbView.getGlobalVisibleRect(startBounds)
        findViewById<View>(R.id.container)
            .getGlobalVisibleRect(finalBounds, globalOffset)
        startBounds.offset(-globalOffset.x, -globalOffset.y)
        finalBounds.offset(-globalOffset.x, -globalOffset.y)
        val startScale: Float
        if (finalBounds.width() as Float / finalBounds.height()
            > startBounds.width() as Float / startBounds.height()
        ) {
            startScale = startBounds.height() as Float / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth
            startBounds.right += deltaWidth
        } else {
            startScale = startBounds.width() as Float / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2
            startBounds.top -= deltaHeight
            startBounds.bottom += deltaHeight
        }
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f
        val set = AnimatorSet()
        set.play(
            ObjectAnimator.ofFloat<View>(
                expandedImageView, View.X,
                startBounds.left, finalBounds.left
            )
        )
            .with(
                ObjectAnimator.ofFloat<View>(
                    expandedImageView, View.Y,
                    startBounds.top, finalBounds.top
                )
            )
            .with(
                ObjectAnimator.ofFloat(
                    expandedImageView, View.SCALE_X,
                    startScale, 1f
                )
            ).with(
                ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.SCALE_Y, startScale, 1f
                )
            )
        set.duration = mShortAnimationDuration
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mCurrentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set
        expandedImageView.setOnClickListener {
            if (mCurrentAnimator != null) {
                mCurrentAnimator.cancel()
            }
            val set = AnimatorSet()
            set.play(
                ObjectAnimator
                    .ofFloat(
                        expandedImageView,
                        View.X,
                        startBounds.left
                    )
            )
                .with(
                    ObjectAnimator
                        .ofFloat(
                            expandedImageView,
                            View.Y, startBounds.top
                        )
                )
                .with(
                    ObjectAnimator
                        .ofFloat(
                            expandedImageView,
                            View.SCALE_X, startScale
                        )
                )
                .with(
                    ObjectAnimator
                        .ofFloat(
                            expandedImageView,
                            View.SCALE_Y, startScale
                        )
                )
            set.duration = mShortAnimationDuration
            set.interpolator = DecelerateInterpolator()
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }
            })
            set.start()
            mCurrentAnimator = set
        }
    }
}