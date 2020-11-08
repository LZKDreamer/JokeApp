package com.lzk.jokeapp.ui.publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lzk.jokeapp.R
import com.lzk.libnavannotation.ActivityDestination

@ActivityDestination(pageUrl = "main/tabs/publish")
class PublishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }
}