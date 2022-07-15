package com.example.mobile.julfani.tubes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.Utils;

public class MoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /* set statusbar transparent */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);

        /* set moon */
        ImageView imageView = findViewById(R.id.moon_activity);
        imageView.setImageResource(R.drawable.mooniconselect);


        /* see home */
        Utils.exitApp(this);

        /* see stats */
        findViewById(R.id.stats_activity).setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, StatsActivity .class));
        });

        /* open link github */
        findViewById(R.id.imageView_github).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nursyah21/bettersleep"));
            startActivity(browserIntent);
        });
    }
}