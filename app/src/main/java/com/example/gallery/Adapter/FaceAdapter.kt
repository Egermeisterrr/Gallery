package com.example.gallery.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.Bean.MovieInfo
import com.example.gallery.MainActivity
import com.example.gallery.R


class FaceAdapter(
    private val context: Context,
    private var mMovieInfoList: List<MovieInfo?>?
) : RecyclerView.Adapter<FaceAdapter.FaceViewHolder>(),
    View.OnClickListener,
    OnLongClickListener {

    private var mListener: MainActivity.RecyclerViewOnClickListener? = null

    class FaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView = itemView.findViewById<View>(R.id.iv_face) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FaceViewHolder(inflater.inflate(R.layout.item_face, parent, false))
    }

    override fun onBindViewHolder(holder: FaceViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(this)
        Glide.with(context)
            .load(mMovieInfoList?.get(position)?.url?.medium)
            .centerCrop()
            //.animate(R.anim.anim_zoom)
            .thumbnail(0.1f)
            .into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return mMovieInfoList?.size!!
    }

    override fun onClick(v: View) {
        mListener?.onClick(v.tag as Int)
    }

    override fun onLongClick(v: View): Boolean {
        mListener?.onLongClick(v.tag as Int)
        return false
    }

    fun setListener(listener: MainActivity.RecyclerViewOnClickListener?) {
        mListener = listener
    }

    fun setMovieInfoList(movieInfoList: ArrayList<MovieInfo?>?) {
        mMovieInfoList = movieInfoList
        notifyItemRangeInserted(0, movieInfoList!!.size)
    }
}