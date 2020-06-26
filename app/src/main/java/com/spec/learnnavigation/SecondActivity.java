package com.spec.learnnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class SecondActivity extends AppCompatActivity {

    String TAG = SecondActivity.class.getSimpleName();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /*-------------------hooks--------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        /*-------------------Navigation Drawer Menu--------------------*/
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        implementNavDrawerAction(navigationView);

        /*// Inflate the header view at runtime
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        // We can now look up items within the header if needed
        ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);*/

        // To get the headerview
        /*// There is usually only 1 header view.
        // Multiple header views can technically be added at runtime.
        // We can use navigationView.getHeaderCount() to determine the total number.
        if (navigationView.getHeaderCount() > 0) {
            // avoid NPE by first checking if there is at least one Header View available
            View headerLayout = navigationView.getHeaderView(0);
        }*/

        // To perform an action as you click on menu item in navigationview
        final Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_switch);
        View actionView = menuItem.getActionView();
        //((SwitchCompat)actionView).isChecked();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchCompat switchCompatView = (SwitchCompat)view;
                if(switchCompatView.isChecked()) {
                    navigationView.setCheckedItem(menu.findItem(R.id.menu_first_fragment));
                    Log.e(TAG, String.valueOf(view.getId()));
                    toolbar.setTitle("new");
                }
            }
        });
    }

    private void implementNavDrawerAction(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_first_fragment:
                        fragment = new SecondActivity.FirstFragment();
                        break;
                    case R.id.menu_second_fragment:
                        fragment = new SecondActivity.SecondFragment();
                        break;
                    case R.id.menu_third_fragment:
                        fragment = new SecondActivity.ThirdFragment();
                        break;
                    default:
                        return false;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).commit();
                navigationView.setCheckedItem(item);
                //item.setChecked(true);
                setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);
                //drawerLayout.closeDrawers(); to close all drawers
                return true;
            }
        });
    }

    /*appears to be unnecessary because, I noticed that without this method the drawer toggle works fine*/
    // synchronize the state whenever the screen is restored
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    /*also appears to be unnecessary because, I noticed that without this method the drawer toggle works fine*/
    // synchronize the state whenever the whenever there is a configuration change
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // pass any configuration change to the drawertoggle
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*according to the tutorial, this is necessary to allow tapping of the drawer icon to open the
             navigation drawer. But I noticed from my code that it isn't necessary*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }*/
        //the drawerToggle could implement the above switch statement, and this method adds a bit more
        // checks and allows mouse clicks on the icon to open and close the drawer
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    public static class FirstFragment extends Fragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.first_fragment, container, false);
        }
    }

    public static class SecondFragment extends Fragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.second_fragment, container, false);
        }
    }

    public static class ThirdFragment extends Fragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.third_fragment, container, false);
        }
    }
}
