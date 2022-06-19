package com.example.gallery.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.MainActivity
import com.example.gallery.R


class FaceAdapter(private val mContext: Context) :
    RecyclerView.Adapter<FaceAdapter.FaceViewHolder?>(), View.OnClickListener,
    OnLongClickListener {
    private val mInflater: LayoutInflater
    private var mMovieInfoList: List<MovieInfo>? = null
    private var mListener: MainActivity.recyclerViewOnClickListener? = null
    fun setListener(listener: MainActivity.recyclerViewOnClickListener?) {
        mListener = listener
    }

    fun setMovieInfoList(movieInfoList: List<MovieInfo>) {
        mMovieInfoList = movieInfoList
        notifyItemRangeInserted(0, movieInfoList.size)
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FaceViewHolder {
        return FaceViewHolder(mInflater.inflate(R.layout.item_face, parent, false))
    }

    fun onBindViewHolder(holder: FaceViewHolder, position: Int) {
        holder.itemView.setTag(position)
        holder.itemView.setOnClickListener(this)
        Glide.with(mContext)
            .load(mMovieInfoList!![position].url.medium)
            .centerCrop()
            .animate(R.anim.anim_zoom)
            .thumbnail(0.1f)
            .into(holder.mImageView)
    }

    val itemCount: Int
        get() = if (mMovieInfoList == null) 0 else mMovieInfoList!!.size

    override fun onClick(v: View) {
        if (mListener != null) mListener.onClick(v.tag as Int)
    }

    override fun onLongClick(v: View): Boolean {
        if (mListener != null) mListener.onLongClick(v.tag as Int)
        return false
    }

    inner class FaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView

        init {
            mImageView = itemView.findViewById<View>(R.id.iv_face) as ImageView
        }
    }

    init {
        mInflater = LayoutInflater.from(mContext)
    }
}