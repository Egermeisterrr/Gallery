package com.example.gallery

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.adapter.MyAdapter
import com.example.gallery.data.DataSource


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}