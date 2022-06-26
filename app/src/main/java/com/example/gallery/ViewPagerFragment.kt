package com.example.gallery


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.gallery.Bean.MovieInfo
import com.example.gallery.databinding.FragmentFullscreenPageBinding


class ViewPagerFragment : DialogFragment() {
    private var mViewPager: ViewPager? = null
    private var mTimeStamp: TextView? = null
    private var mIndex: TextView? = null
    private var mMovieInfoList: List<MovieInfo>? = null
    private var mCurrentPos = 0

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    @Nullable
    fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_fullscreen, container, false)
        mViewPager = view.findViewById<View>(R.id.vp_fullscreen) as ViewPager
        mViewPager.setPageTransformer(true, DepthPageTransformer())
        mTimeStamp = view.findViewById<View>(R.id.tv_time) as TextView
        mIndex = view.findViewById<View>(R.id.tv_index) as TextView

        mMovieInfoList = getArguments().getSerializable("list")
        mCurrentPos = getArguments().getInt("position")

        val adapter: ViewPagerAdapter = ViewPagerAdapter()
        adapter.setMovieInfoList(mMovieInfoList)
        mViewPager.setAdapter(adapter)

        mViewPager.addOnPageChangeListener(ViewPagerListener())

        setTheCurrentItem(mCurrentPos)
        return view
    }

    private fun setTheCurrentItem(currentPos: Int) {
        mIndex.setText(currentPos + 1 + "/" + mMovieInfoList!!.size)
        mTimeStamp.setText(mMovieInfoList!![currentPos].timeStamp)
        mViewPager.setCurrentItem(currentPos)
    }

    private inner class ViewPagerAdapter : PagerAdapter() {
        private var mMovieInfoList: List<MovieInfo>? = null
        private var mInflater: LayoutInflater? = null
        private var mPageBinding: FragmentFullscreenPageBinding? = null
        fun setMovieInfoList(movieInfoList: List<MovieInfo>?) {
            mMovieInfoList = movieInfoList
        }

        fun instantiateItem(container: ViewGroup, position: Int): Any {
            mInflater = getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)

            //deprecated, use data binding instead.
//            View view = mInflater.inflate(R.layout.fragment_fullscreen_page, container, false);
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv_fullscreen);
//
//            Glide.with(getActivity())
//                    .load(mMovieInfoList.get(position).url.large)
//                    .thumbnail(0.1f)
//                    .into(imageView);

            //data binding
            mPageBinding = DataBindingUtil.inflate(
                mInflater,
                R.layout.fragment_fullscreen_page,
                container,
                false
            )
            val view: View = mPageBinding.getRoot()
            mPageBinding.setImgUrl(mMovieInfoList!![position].url.large)
            container.addView(view)
            return view
        }

        val count: Int
            get() = if (mMovieInfoList == null) 0 else mMovieInfoList!!.size

        fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
            container.removeView(`object` as View?)
        }
    }

    private inner class ViewPagerListener : ViewPager.OnPageChangeListener {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        fun onPageSelected(position: Int) {
            setTheCurrentItem(position)
        }

        fun onPageScrollStateChanged(state: Int) {}
    }

    private inner class DepthPageTransformer : ViewPager.PageTransformer {
        private val MIN_SCALE = 0.8f
        fun transformPage(page: View, position: Float) {
            val pageWidth = page.width
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                page.alpha = 1f
                page.translationX = 0f
                page.scaleX = 1f
                page.scaleY = 1f

                // Scale the page (between MIN_SCALE and 1)
                val scaleFactor = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 + position))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                //Fade the page.
                page.alpha = 1 + position
            } else if (position <= 1) { // (0,1]
                // Fade the page.
                page.alpha = 1 - position

                // Scale the page (between MIN_SCALE and 1)
                val scaleFactor = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
            }
        }
    }

    companion object {
        fun newInstance(): ViewPagerFragment {
            return ViewPagerFragment()
        }
    }
}