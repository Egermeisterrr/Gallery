package com.example.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.R
import com.example.gallery.model.Image

class MyAdapter (
    private val context: Context,
    private val dataset: List<Image>
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)

        fun setImage(item: Image) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return MyViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setImage(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    /*fun addImage(view: View) {
        val uri = "https://img.labirint.ru/images/upl/descripts/pic_1477575042.jpg"

        val imagePath : ImageView = findViewById(R.id.myImage)

        Glide.with(this)
            .load(uri)
            .into(imagePath)
    }*/
}