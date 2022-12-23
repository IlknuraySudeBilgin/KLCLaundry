package com.example.klclaundry.MainPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.klclaundry.Adaptors.FirebaseAdaptor;
import com.example.klclaundry.Adaptors.UserAdaptor;
import com.example.klclaundry.MainPages.SideMenu.changePage;
import com.example.klclaundry.R;
import com.example.klclaundry.Services.PreferenceService;
import com.example.klclaundry.Services.pushNotService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Singleton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private int openCounter=0;
    private PreferenceService pService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definetions();
        events();
    }

    @SuppressLint("ResourceAsColor")
    protected void definetions() {
        pService = new PreferenceService(getApplicationContext());
        navigationView = findViewById(R.id.navView);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        NavHostFragment Fragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.FragmentNav);
        NavigationUI.setupWithNavController(navigationView,Fragment.getNavController());
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(R.drawable.mngpng);

        toggle.syncState();

        bottomNavigationView = findViewById(R.id.bottomNav); //bttom nav bul
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.FragmentNav); // göstericiyi bul

        NavigationUI.setupWithNavController(bottomNavigationView, //bagla
                navHostFragment.getNavController());

    }

    protected void events() {

        //loadDataforUser();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int req = item.getItemId();
                switch (req) {
                    case R.id.ChangeUsername:
                        startActivity(new Intent(getApplicationContext(),changePage.class));
                        break;

                    case R.id.shutTheProg:
                        System.exit(1);
                        break;

                    case R.id.about:
                        break;

                    case R.id.openOrClose:

                        if (pService.get("id","").equals("admin")) {
                            //pushNotService pService = new pushNotService(getApplicationContext());
                            FirebaseAdaptor firebaseAdaptor = new FirebaseAdaptor();
                            openCounter++;
                            if (openCounter%2 == 0) {
                                pService.pushInt("openorclose",0);
                                firebaseAdaptor.LaundryOpenOrClose(false);
                                item.setTitle("ÇAMAŞIRHANE: KAPALI");
                                item.setIcon(R.drawable.ic_baseline_lock_24);
                                //pService.connect();
                                openCounter=0;
                            } else {
                                pService.pushInt("openorclose",1);
                                firebaseAdaptor.LaundryOpenOrClose(true);
                                item.setTitle("ÇAMAŞIRHANE: AÇIK");
                                item.setIcon(R.drawable.ic_baseline_lock_open_24);
                            }
                        }


                }


                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }


}