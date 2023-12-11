package com.example.reado

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.memeshare.MySingleton
import com.example.reado.databinding.ActivityMainBinding
import com.example.reado.databinding.ItemNewsBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mAdapter : MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView : RecyclerView = binding.rvItemsList
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
        mAdapter = MyAdapter(this,this)
        recyclerView.adapter = mAdapter


    }

    private fun fetchData() {
        val url = "https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=LhqfAOH1L1lfFPePgWPoK82oUYYBqLxe"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,

            {response ->
                val newsJsonArray = response.getJSONArray("results")

                val newsArray = ArrayList<NewsApiAttributes>()

                for (obj in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(obj)
                    val mediaArray = newsJsonObject.getJSONArray("media")

                    if(mediaArray.length() != 0) {

                        val mediaObject = mediaArray.getJSONObject(0)
                        val metaMediaArray = mediaObject.getJSONArray("media-metadata")
                        val newsImageObject = metaMediaArray.getJSONObject(2)

                        val newsContent = NewsApiAttributes(
                            newsJsonObject.getString("abstract"),
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("url"),
                            newsImageObject.getString("url"),
                            newsJsonObject.getString("byline")


                        )
                        newsArray.add(newsContent)
                    }

                }
                mAdapter.updateNewsItem(newsArray)
            },
            {error ->
//                Toast.makeText(this, "Api Calling failed!",Toast.LENGTH_LONG).show()
                Log.e("API_CALL", "API Calling failed: ${error.message}")
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: NewsApiAttributes) {
        //Open article in browser.
//        val uri = Uri.parse(item.url)
//        val intent = Intent(Intent.ACTION_VIEW, uri)
//        startActivity(intent)

        //Open article inside the App.
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}