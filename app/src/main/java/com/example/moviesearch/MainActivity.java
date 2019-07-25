package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle toggle;

    List<TmdbMoviePojo> tmdbTopMovie, tmdbNewMovie;
    int option = 0;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);

        //Nav Drawer
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv.setNavigationItemSelectedListener((item)->{
            switch (item.getItemId()){
                case R.id.returnHome:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.main_movie_frame, new HomeFragment(mContext));
                    ft.commit();
                    break;
                case R.id.searchByTitle:
                    option = 1;
                    break;
                case R.id.searchByYear:
                    option = 2;
                    break;
                default:
                    return true;
            }
            if(option > 0) {
                ft = fm.beginTransaction();
                ft.replace(R.id.main_movie_frame, new SearchMovieFragment(mContext, option));
                ft.commit();
                option=0;
            }
            return true;
        });

        ft = fm.beginTransaction();
        ft.replace(R.id.main_movie_frame, new HomeFragment(mContext));
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
