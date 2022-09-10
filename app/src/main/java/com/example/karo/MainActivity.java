package com.example.karo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.karo.utils.Helpers;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.topbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        toolbar.setNavigationOnClickListener(view -> {
            drawer.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawer(GravityCompat.START);

            switch (item.getItemId()) {
                case R.id.home:
                    Helpers.showToast(this, "Home is Clicked");
                    break;
                case R.id.trash:
                    Helpers.showToast(this, "Trash is Clicked");
                    break;
                case R.id.settings:
                    Helpers.showToast(this, "Settings is Clicked");
                    break;
                case R.id.login:
                    Helpers.showToast(this, "Login is Clicked");
                    break;
                case R.id.share:
                    Helpers.showToast(this, "Share is Clicked");
                    break;
                case R.id.rate:
                    Helpers.showToast(this, "Rate is Clicked");
                    break;
                default:
                    return true;
            }

            return true;
        });
    }
}