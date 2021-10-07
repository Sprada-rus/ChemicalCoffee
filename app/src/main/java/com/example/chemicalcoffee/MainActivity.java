package com.example.chemicalcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeTab;
    private CoffeeFragment coffeeTab;
    private HotFragment hotTab;
    private StoreFragment storeTab;
    private long time;
    private Toast toast;
    private final int count[] = {0};

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

        getCountOnTable("PRODUCT");
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
        final MenuItem item = menu.findItem(R.id.basket_item);
        item.setActionView(R.layout.basket_icon_layout);
        View actionView = item.getActionView();

        if (item != null && actionView != null){

            TextView view = (TextView) actionView.findViewById(R.id.cart_badge);
            ImageView img = (ImageView) actionView.findViewById(R.id.img_bg);
            if (count[0] > 0){
                view.setVisibility(View.VISIBLE);
                view.setText(String.valueOf(count[0]));
//                invalidateOptionsMenu();
            } else {
                view.setVisibility(View.GONE);
//                invalidateOptionsMenu();
            }


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BasketActivity.class);
                    startActivity(intent);
                }
            });
        }


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

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
        } else {
            toast = Toast.makeText(this, "Нажмите кнопку \"Назад\" ещё раз до выхода", Toast.LENGTH_LONG);
            toast.show();
        }
        time = System.currentTimeMillis();
    }

    private void getCountOnTable(String nameTable){
        SQLiteOpenHelper helper = new LocalDatabaseHelper(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor = db.query(nameTable, new String[] {"count"}, "count is not null", new String[] {}
                    ,"", "",  "");

                    cursor.moveToFirst();
                    count[0] = cursor.getInt(0);

                    while (cursor.moveToNext()){
                        count[0] += cursor.getInt(0);
                    }

                    cursor.close();
                    db.close();
                } catch (Exception ex){
                    Log.e("main", ex.getMessage());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

        });

        thread.start();
    }


    @Override
    protected void onResume() {
        getCountOnTable("PRODUCT");

        invalidateOptionsMenu();
        super.onResume();
    }
}