package com.example.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.Adapter.FaceAdapter
import com.example.gallery.Bean.MovieInfo
import com.example.gallery.Interface.MovieInfoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "http://api.androidhive.info/json/glide.json"

class MainActivity : AppCompatActivity() {
    private lateinit var mMovieInfoList: ArrayList<MovieInfo>
    private lateinit var mRetrofit: Retrofit
    private lateinit var mMovieInfoService: MovieInfoService
    private val mRecyclerView: RecyclerView = findViewById(R.id.rv_gallery)
    private val mFaceAdapter: FaceAdapter = FaceAdapter(this, mMovieInfoList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }



    interface RecyclerViewOnClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }
}