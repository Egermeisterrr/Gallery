package com.example.gallery


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    private val tag = MainActivity::class.java.simpleName
    private var images: ArrayList<Image>? = null
    private val myAdapter = GalleryAdapter(this, ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        fetchImages()
    }

    private fun init() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = mLayoutManager
        //recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = myAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImages() {
        val req = JsonArrayRequest(
            endpoint, {
                    response ->
                Log.d(tag, response.toString())
                images?.clear()
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)

                        val name: String = jsonObject.getString("name")
                        val url = jsonObject.getJSONObject("url")
                        val small: String = url.getString("small")
                        val medium: String = url.getString("medium")
                        val large: String = url.getString("large")
                        val timestamp: String = jsonObject.getString("timestamp")
                        val image = Image(name, small, medium, large, timestamp)

                        images?.add(image)
                    }
                    catch (e: JSONException) {
                        Log.e(tag, "Json parsing error: " + e.message)
                    }
                }
                myAdapter.notifyDataSetChanged()
            }) {
                error ->
            Log.e(tag, "Error: " + error.message)
        }

        AppController.instance?.addToRequestQueue(req)
    }

    /*fun recycler() {
        recyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                recyclerView!!, object : ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        val bundle = Bundle()
                        bundle.putSerializable("images", images)
                        bundle.putInt("position", position)
                        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                        val newFragment = SlideshowDialogFragment.newInstance()
                        newFragment.arguments = bundle
                        newFragment.show(ft, "slideshow")
                    }

                    //прописать логику для длинного клика
                    override fun onLongClick(view: View?, position: Int) {}
                })
        )

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
    }*/
}