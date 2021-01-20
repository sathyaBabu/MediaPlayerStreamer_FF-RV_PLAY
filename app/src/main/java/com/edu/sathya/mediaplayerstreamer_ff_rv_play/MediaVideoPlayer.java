package com.edu.sathya.mediaplayerstreamer_ff_rv_play;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

// pointers to pass action to service to start the player
// https://stackoverflow.com/questions/3293243/pass-data-from-activity-to-service-using-an-intent
public class MediaVideoPlayer extends AppCompatActivity {

    Uri video1 ;
    VideoView video ;
  //  MediaController mediaController;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_video_player);

        video = (VideoView) findViewById(R.id.video1);

//        Uri videoo = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.    //pelearrahman);//yeloveshreya); //pelearrahman); //do not add any extension
//
//
//        video.setVideoURI(videoo);
//       // setContentView(videoHolder);
//        video.requestFocus();
//        video.start();





//        VideoView video=(VideoView) findViewById(R.id.video1);
//
//        video.setVideoPath("android.resource://uk.co.SplashActivity/vid/big_buck_bunny.mp4");
//        video.requestFocus();
//        video.start();


        // https://www.youtube.com/watch?v=JGwWNGJdvx8


        VideoView videoView = (VideoView) findViewById(R.id.video1);
    //  MediaController mediaController = new MediaController(this,android.media.session.MediaSession.999);
    //  mediaController.setAnchorView(videoView);
  //    videoView.setMediaController(mediaController);

      videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
      R.raw.twoguys)); //theevandijeevamshamayivideosong));

        videoView.start();


    }
}
/*
Error:Execution failed for task ':app:packageInstantRunResourcesDebug'.
> Java heap space
ties file.
For example, the following line, in the gradle.properties file, sets the maximum Java heap size to 1,024 MB:
<em>org.gradle.jvmargs=-Xmx1024m</em>
<a href="http://www.gradle.org/docs/current/userguide/build_environment.html">Read Gradle's configuration guide
</a><br><a href="http://docs.oracle.com/javase/7/docs/technotes/guides/vm/gc-ergonomics.html">
Read about Java's heap size</a>
 */