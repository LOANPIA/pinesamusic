package com.example.pinesamusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.AsyncTaskLoader;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;

import com.example.pinesamusic.helper.Helper;

import java.util.jar.Manifest;

public class SplashActivity extends AppCompatActivity {

    boolean isRunning = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(isPermission()){
            loadMusic();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},12);
        }


    }

    private boolean isPermission() {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    private void loadMusic(){
        AsyncTask.execute(() -> Helper.getAllMusic(SplashActivity.this));
        check();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRunning = true;
        loadMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void check() {
        if(isRunning){
            new Handler(Looper.myLooper()).postDelayed(() -> {
               if(Helper.isLoaded){
                   startMain();
               }else{
                 check();
               }
            },100);
        }
    }

    private void startMain(){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            loadMusic();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}