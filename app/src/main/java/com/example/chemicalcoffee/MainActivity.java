package com.example.chemicalcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private int positionTab;
    public  TabLayout tabLayout;
    public  ViewPager pager;
    private CoffeeFragment coffeeFragment;
    private HotFragment hotFragment;
    private StoreFragment storeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) toolbar.findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        barTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        loadFragment();

        if (savedInstanceState != null){
            tabLayout.getTabAt(savedInstanceState.getInt("positionTab")).select();
        }
    }

    private void loadFragment(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                coffeeFragment = new CoffeeFragment();
                hotFragment = new HotFragment();
                storeFragment = new StoreFragment();
            }
        }).start();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("positionTab", positionTab);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager frManager){
            super(frManager);
        }

        public int getCount(){
            return 4;
        }

        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return coffeeFragment;
                case 2:
                    return hotFragment;
                case 3:
                    return storeFragment;
            }
            return null;
        }

        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return getResources().getText(R.string.home_tab);
                case 1:
                    return getResources().getText(R.string.coffee_tab);
                case 2:
                    return getResources().getText(R.string.hot_tab);
                case 3:
                    return getResources().getText(R.string.store_tab);
            }

            return null;
        }
    }
}