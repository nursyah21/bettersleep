package com.example.mobile.julfani.tubes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.mobile.julfani.tubes.R;
import com.example.mobile.julfani.tubes.utils.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        /* hide navbars, and statusbar */
        Utils.fullScreen(this);
        Utils.hideNavbar(this);

        findViewById(R.id.thankyou).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.thankyou)
        );

        findViewById(R.id.back_home).setOnClickListener(v->finish());

        /* send data */
        new NetworkApi().execute();
    }

    private static  class NetworkApi extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            String ip_str = "null";

            /* ip addr, android os,  */
            try {
                URL ip = new URL("https://api4.my-ip.io/ip");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ip.openStream()));
                ip_str = bufferedReader.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String params = "ip_addr="+ ip_str +
                    "&sdk=" + Build.VERSION.SDK_INT +
                    "&device=" + Build.ID +
                    "&hardware=" + Build.HARDWARE;

            /* input data */
            try {
                URL url = new URL("https://bettersleep.herokuapp.com/api?"+params);
                Log.d("url", "https://bettersleep.herokuapp.com/api?"+params);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String str = bufferedReader.readLine().trim();
                Log.d("input data", str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}