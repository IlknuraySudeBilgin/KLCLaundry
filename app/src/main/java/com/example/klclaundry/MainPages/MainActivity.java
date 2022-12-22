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

import com.example.klclaundry.MainPages.SideMenu.changePage;
import com.example.klclaundry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private SharedPreferences sp;
    private String name;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definetions();
        events();
    }

    @SuppressLint("ResourceAsColor")
    protected void definetions() {

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

        bottomNavigationView = findViewById(R.id.bottomNav);
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.FragmentNav);

        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());

    }

    protected void events() {
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
                }


                return true;
            }
        });

    }
    public void pushNot(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1","1", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext(),"1");
        b.setContentTitle(title);
        b.setContentText(body);
        b.setSmallIcon(R.drawable.mngpng);
        b.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1,b.build());
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