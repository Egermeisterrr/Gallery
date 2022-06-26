package com.example.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
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
    private var mMovieInfoList: ArrayList<MovieInfo?>? = null
    private lateinit var mRetrofit: Retrofit
    private lateinit var mMovieInfoService: MovieInfoService
    private val mRecyclerView: RecyclerView = findViewById(R.id.rv_gallery)
    private val mFaceAdapter: FaceAdapter = FaceAdapter(this, mMovieInfoList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)

        mFaceAdapter.setListener(object : RecyclerViewOnClickListener {
            override fun onClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("list", mMovieInfoList)
                bundle.putInt("position", position)

                val fragmentTransaction: FragmentTransaction =
                    supportFragmentManager.beginTransaction()

                val viewPagerFragment = ViewPagerFragment.newInstance()
                viewPagerFragment.arguments = bundle
                viewPagerFragment.show(fragmentTransaction, "SLIDE_SHOW")
            }

            override fun onLongClick(position: Int) {

            }
        })

        fetchMovieInfo(URL)
    }

    private fun fetchMovieInfo(url: String) {
        mRetrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()

        mMovieInfoService = mRetrofit.create(MovieInfoService::class.java)

        val movieInfoCall = mMovieInfoService.movieInfoList(url)
        movieInfoCall!!.enqueue(object : Callback<ArrayList<MovieInfo?>?> {
            override fun onResponse(
                call: Call<ArrayList<MovieInfo?>?>,
                response: Response<ArrayList<MovieInfo?>?>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()

                    //make the data set bigger :)
                    for (i in 0..4) {
                        mMovieInfoList?.addAll(list!!)
                    }
                    mFaceAdapter.setMovieInfoList(mMovieInfoList)
                }
            }

            override fun onFailure(call: Call<java.util.ArrayList<MovieInfo?>?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    interface RecyclerViewOnClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }
}