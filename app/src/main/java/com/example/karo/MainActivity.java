package com.example.karo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.karo.pages.auth.LoginActivity;
import com.example.karo.utils.Helpers;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.topbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        toolbar.setNavigationOnClickListener(view -> drawer.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawer.closeDrawer(GravityCompat.START);

            switch (item.getItemId()) {
                case R.id.home:
                    Helpers.showToast(this, "Home is Clicked");
                    changeFragment(new HomeFragment());
                    break;
                case R.id.manage_fees:
                    Helpers.showToast(this, "Manage Fees is Clicked");
                    changeFragment(new ManageFeesFragment());
                    break;
                case R.id.settings:
                    Helpers.showToast(this, "Settings is Clicked");
                    changeFragment(new SettingsFragment());
                    break;
                case R.id.profile:
                    Helpers.showToast(this, "Profile is Clicked");
                    changeFragment(new ProfileFragment());
                    break;
                case R.id.share:
                    Helpers.showToast(this, "Share is Clicked");
                    break;
                case R.id.rate:
                    Helpers.showToast(this, "Rate is Clicked");
                    break;
                case R.id.logout:
                    logout();
                    break;
                default:
                    return true;
            }

            return true;
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}