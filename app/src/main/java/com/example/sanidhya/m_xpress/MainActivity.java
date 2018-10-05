package com.example.sanidhya.m_xpress;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.sanidhya.m_xpress.Fragments.FeedFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView sideNavigation;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());


        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Custom action bar to adhere to material design
        ActionBar actionbar = getSupportActionBar();
        if(actionbar!=null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //Navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        sideNavigation = findViewById(R.id.nav_view);

        sideNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(!menuItem.isChecked()){
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    switchFragments(menuItem.getItemId());
                }
                else{
                    mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });
        sideNavigation.getMenu().getItem(0).setChecked(true);
        switchFragments(R.id.menu_posts);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddIssueActivity.class);
                startActivity(intent);
            }
        });

//        skillsSearchView = findViewById(R.id.skillsSearchView);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        skillsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        handleIntent(getIntent());
    }

    private void switchFragments(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(id){
            case R.id.menu_posts: transaction.replace(R.id.fragment_container, new FeedFragment());
                                    break;
            case R.id.drawer_setting : Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
        }
        Log.d("TestLog","Transaction created");
        transaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG, "handleIntent: action_search: " + query);
//            doMySearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String skill = intent.getDataString();
            Log.e(TAG, "handleIntent: skill = " + skill);
            Uri data = intent.getData();
            Log.e(TAG, "handleIntent: data = " + data);

            // TODO
        }
    }


}
