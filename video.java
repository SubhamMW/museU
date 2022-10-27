package com.example.subham.museu;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;



public class Video extends AppCompatActivity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL ;
//= "http://127.0.0.1:15123/"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(Video.this);
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        String str=getwifiinfo();
        String str2="http://",str4;
        StringBuilder str3 =new StringBuilder(str2);
        str3.append(str+":15123/");
        VideoURL=str3.toString();
        //System.out.println(str4);


        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    Video.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });





    }

    public String getwifiinfo() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        String ar2 = null;
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int a = wifiInfo.getIpAddress();
            // tv.setText(a+"");
            String b = String.format("%d.%d.%d.%d",
                    (a & 0xff),
                    (a >> 8 & 0xff),
                    (a >> 16 & 0xff),
                    (a >> 24 & 0xff));
            //tv.setText(b);

            int pos = b.lastIndexOf('.');
            String ar = b.substring(0, pos + 1);
            StringBuilder sb = new StringBuilder(ar);
            sb.append(1);
            ar2 = sb.toString();
            //tv2.setText(ar2);
           /* if (String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")) {
                Toast.makeText(this, wifiInfo.getSSID() + "", Toast.LENGTH_SHORT).show();
                wifiInfo.getRssi();
            } else {
                Toast.makeText(this, "pls connect to a wifi network", Toast.LENGTH_SHORT).show();
            }*/
        } else {
            wifiManager.setWifiEnabled(true);
        }
        return ar2;
    }







}

