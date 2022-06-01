package com.example.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.adapter.MyAdapter
import com.example.gallery.data.DataSource
import com.example.gallery.model.Image


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val myDataset = DataSource().loadImages()
        val recyclerView: RecyclerView = findViewById(R.id.rc_view)
        val numberOfColumns = 3
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        recyclerView.adapter = MyAdapter(this, myDataset)
    }
}