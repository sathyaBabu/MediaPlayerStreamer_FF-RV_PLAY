package com.edu.sathya.mediaplayerstreamer_ff_rv_play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import static edureka.sathya.com.mediaplayer_ff_rv_play.R.raw.theevandijeevamshamayivideosong;

//  Skipped 38 frames!  The application may be doing too much work on its main thread.
public class MainActivity extends AppCompatActivity {

    private Button FF,Pause,Play,Rev;

    private ImageView iv;
    private Handler myHandler = new Handler();


//        mediaPlayer = MediaPlayer.create(this, R.raw.pelearrahman);  // DI ----

    MediaPlayer mediaPlayer ; //= MediaPlayer.create(this, R.raw.theevandijeevamshamayivideosong);

    private double startTime = 0;
    private double finalTime = 0;


    private int forwardTime = 5000;
    private int backwardTime = 5000;

    private SeekBar seekbar;

    private TextView tx1,tx2,tx3;

    public static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.hayati);

        FF = (Button) findViewById(R.id.button1);
        Pause = (Button) findViewById(R.id.button2);
        Play=(Button)findViewById(R.id.button3);
        Rev=(Button)findViewById(R.id.button4);

        iv=(ImageView)findViewById(R.id.imageView);

        tx1=(TextView)findViewById(R.id.textView2);
        tx2=(TextView)findViewById(R.id.textView3);
        tx3=(TextView)findViewById(R.id.textView4);
        tx3.setText("Song: My Pick");



        seekbar=(SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        // b2 pause
        Pause.setEnabled(false);



        // b3 play
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();

                // Picked for here
                // https://developer.android.com/guide/topics/media/mediaplayer

              //  streamAsong(); // Dummy Player Just Streams and playes the song
                PlayMediaPlayerMainCode();


            }// onClick
        });
// b2 pause
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                Pause.setEnabled(false);
                Play.setEnabled(true);
            }
        });

        // b1 ff
        FF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;

                    mediaPlayer.seekTo((int) startTime);

                    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // b4 rev
        Rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;



                startTime = startTime - backwardTime;
                mediaPlayer.seekTo((int) startTime);

                //  Uncomment the following to take the errr out of log...

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void streamAsong() {

        // https://developer.android.com/guide/topics/media/mediaplayer
        // pointers to pass action to service to start the player
        // https://stackoverflow.com/questions/3293243/pass-data-from-activity-to-service-using-an-intent


        String url = "http://hck.re/Rh8KTk"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();


        ////
    }

    private void PlayMediaPlayerMainCode() {
        //                try {
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

        mediaPlayer.start();   // STATE PATTERN..


// A BIG ERROR STATE has to deal with it...

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        // if (oneTimeOnly == 0)
        {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        //tx2
        Pause.setText(String.format("Pause.. %d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        tx1.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        seekbar.setProgress((int)startTime);

        myHandler.postDelayed(UpdateSongTime,100);

        Pause.setEnabled(true);
        // b3 play
        Play.setEnabled(false);
    }


    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();

            // b3  play
            Play.setText(String.format("Play Progress...  %d min, %d sec",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);  // wrong.. err due to garbage collection u r stopping the GC from collecting garbage
        }
    };


    public void videoPlay(View view) {
        Intent intent =  new Intent(MainActivity.this,MediaVideoPlayer.class);
        startActivity(intent);
    }
}

/*
Error:Execution failed for task ':app:packageInstantRunResourcesDebug'.
        > Java heap space
        ties file.
        For example, the following line, in the gradle.properties file, sets the maximum Java heap size to 1,024 MB:
<em>org.gradle.jvmargs=-Xmx1024m</em>
<a href="http://www.gradle.org/docs/current/userguide/build_environment.html">Read Gradle's configuration guide</a><br><a href="http://docs.oracle.com/javase/7/docs/technotes/guides/vm/gc-ergonomics.html">Read about Java's heap size</a>

*/