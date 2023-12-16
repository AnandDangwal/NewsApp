package com.example.reado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mostEmailedButton = binding.btn1
        val mostSharedButton = binding.btn2
        val mostViewedButton = binding.btn3


        val mostEmailedArticles  = "https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=LhqfAOH1L1lfFPePgWPoK82oUYYBqLxe"
        val mostSharedArticles  = "https://api.nytimes.com/svc/mostpopular/v2/shared/7/facebook.json?api-key=LhqfAOH1L1lfFPePgWPoK82oUYYBqLxe"
        val mostViewedArticles  = "https://api.nytimes.com/svc/mostpopular/v2/viewed/7.json?api-key=LhqfAOH1L1lfFPePgWPoK82oUYYBqLxe"

        val intent = Intent(this, RecyclerViewActivity::class.java)

        mostEmailedButton.setOnClickListener(){
            intent.putExtra("News Url",mostEmailedArticles)
            startActivity(intent)
        }

        mostSharedButton.setOnClickListener(){
            intent.putExtra("News Url",mostSharedArticles)
            startActivity(intent)
        }

        mostViewedButton.setOnClickListener(){
            intent.putExtra("News Url",mostViewedArticles)
            startActivity(intent)
        }

    }



}