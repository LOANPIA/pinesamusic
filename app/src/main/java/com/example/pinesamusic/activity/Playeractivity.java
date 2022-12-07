package com.example.pinesamusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinesamusic.R;

import java.util.jar.Attributes;

public class Playeractivity extends AppCompatActivity {
    String musicTitle,musicPath,musicDuration,musicAlbum,musicArtist;
    ImageView back,play,next,pre;
    TextView title;
    MediaPlayer mPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);

        Bundle bundle = getIntent().getExtras();
        musicTitle = bundle.getString("title");
        musicPath = bundle.getString("path");
        musicDuration = bundle.getString("dur");
        musicAlbum = bundle.getString("album");
        musicArtist = bundle.getString("artist");

        back = findViewById(R.id.tool_back);
        play = findViewById(R.id.btn_play);
        next = findViewById(R.id.btn_next);
        pre = findViewById(R.id.btn_preview);
        title = findViewById(R.id.music_title);

        initMusic();

        title.setText(musicTitle);

        play.setOnClickListener(v->{
            if (mPlayer.isPlaying()) {
                play.setImageResource(R.drawable.ic_play);
                mPlayer.pause();


            }else{
                play.setImageResource(R.drawable.ic_pause);
                mPlayer.start();

            }



        })
        ;
        pre.setOnClickListener(v->{


        });
        next.setOnClickListener(v->{


        });
        back.setOnClickListener(v->{
            finish();


        });


    }

    private void initMusic() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

        try {
            mPlayer.setDataSource(musicPath);
            mPlayer.prepareAsync();
        }
        catch (Exception e){
            Toast.makeText(this,"Error Music can't play",Toast.LENGTH_SHORT).show();
        }
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                play.setImageResource(R.drawable.ic_pause);
                mediaPlayer.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer !=null){
            mPlayer.release();
            mPlayer = null;

        }

    }
}