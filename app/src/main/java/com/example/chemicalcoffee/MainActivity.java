package com.example.chemicalcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeTab;
    private CoffeeFragment coffeeTab;
    private HotFragment hotTab;
    private StoreFragment storeTab;

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
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        if (savedInstanceState != null){
            tabLayout.getTabAt(savedInstanceState.getInt("positionTab")).select();
        }

        homeTab = new HomeFragment();
        coffeeTab = new CoffeeFragment();
        hotTab = new HotFragment();
        storeTab = new StoreFragment();
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
                    return homeTab;
                case 1:
                    return coffeeTab;
                case 2:
                    return hotTab;
                case 3:
                    return storeTab;
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.basket_item:
                Intent intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}