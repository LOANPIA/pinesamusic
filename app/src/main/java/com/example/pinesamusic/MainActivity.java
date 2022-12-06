package com.example.pinesamusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pinesamusic.adapter.MusicAdapter;
import com.example.pinesamusic.helper.Helper;
import com.example.pinesamusic.model.Music;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    List<Music> list = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView nav;
    Toolbar toobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        recyclerView = findViewById(R.id.recycler_songs);
        musicAdapter = new MusicAdapter(list);
        recyclerView.setAdapter(musicAdapter);

        loadMusic();
        
        sidebar();
    }

    private void sidebar() {

        nav = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawer_layout);
        toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toobar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_songs:
                        loadMusic();
                        break;
                    case R.id.nav_fav:
                        loadFav();
                        break;
                    case R.id.nav_share:

                        String url = "App Link: https://play.google.com/store/apps/details?id="+getPackageName();
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, url);
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Sharing App");
                        startActivity(Intent.createChooser(intent, getString(R.string.app_name)));

                        break;
                    case R.id.nav_rate:
                        String pkg = getPackageName();
                        String rateMUrl = "market://details?id="+pkg;
                        String rateDUrl = "https://play.google.com/store/apps/details?id="+pkg;

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rateMUrl)));
                        }catch (Exception e){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rateDUrl)));
                        }

                        break;
                    case R.id.nav_policy:

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy))));
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "Link Not Available", Toast.LENGTH_SHORT).show();
                        }

                        break;

                }

                return false;
            }
        });
    }

    private void loadFav() {
    }

    private void loadMusic(){
        list.clear();
        musicAdapter.notifyDataSetChanged();
        list.addAll(Helper.allMusic);
        musicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}