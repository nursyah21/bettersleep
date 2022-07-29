package com.nursyah.bettersleep.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.utils.Utils

class MoonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moon)

        Utils.fullScreen(this)

        findViewById<ImageView>(R.id.imageView_github).setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nursyah21/bettersleep"))
            startActivity(intent)
        }
    }
}