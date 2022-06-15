package com.example.gallery


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.example.gallery.adapter.GalleryAdapter
import com.example.gallery.app.AppController
import com.example.gallery.model.Image
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    companion object {
        private const val endpoint = "https://api.androidhive.info/json/glide.json"
    }

    private val TAG = MainActivity::class.java.simpleName
    private var images: ArrayList<Image>? = null
    private var mAdapter: GalleryAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        images = ArrayList<Image>()
        mAdapter = GalleryAdapter(applicationContext, images!!)

        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(
            applicationContext,
            2
        )

        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = mAdapter


        fetchImages()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImages() {
        val req = JsonArrayRequest(
            endpoint,
            { response ->
                Log.d(TAG, response.toString())
                images!!.clear()
                for (i in 0 until response.length()) {
                    try {
                        val `object` = response.getJSONObject(i)
                        val image : Image? = null // из-за null не возникнет проблем?
                        image?.setName(`object`.getString("name"))
                        val url = `object`.getJSONObject("url")
                        image?.setSmall(url.getString("small"))
                        image?.setMedium(url.getString("medium"))
                        image?.setLarge(url.getString("large"))
                        image?.setTimestamp(`object`.getString("timestamp"))
                        image?.let { images!!.add(it) }
                    }
                    catch (e: JSONException) {
                        Log.e(TAG, "Json parsing error: " + e.message)
                    }
                }
                mAdapter!!.notifyDataSetChanged()
            }) { error ->
            Log.e(TAG, "Error: " + error.message)
        }

        AppController.instance?.addToRequestQueue(req)
    }
}