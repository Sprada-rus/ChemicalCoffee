package com.example.chemicalcoffee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    private final String basketTable = "PRODUCT";
    private ArrayList<ObjProduct> basketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textEmpty = (TextView)  findViewById(R.id.textView);
        textEmpty.setVisibility(View.INVISIBLE);

        RecyclerView view = (RecyclerView) findViewById(R.id.basket_list);

        //В фоне пытаемся выгрузить дайнные из таблицы - Начало
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteOpenHelper helper = new LocalDatabaseHelper(BasketActivity.this);

                try {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    Cursor cursor = db.query(basketTable, new String[] {"_id", "name", "count", "coast", "image_id"},
                            "count > ?", new String[] {"0"}, null, null, null);

                    for(int i = 0; i < cursor.getCount(); i++){
                        if (i == 0 && cursor.moveToFirst()){
                            basketList.add(new ObjProduct(
                                    cursor.getString(cursor.getColumnIndex("name")),
                                    cursor.getInt(cursor.getColumnIndex("image_id")),
                                    cursor.getInt(cursor.getColumnIndex("count")),
                                    cursor.getFloat(cursor.getColumnIndex("coast")) * (cursor.getInt(cursor.getColumnIndex("count")) * 1.0F)
                            ));
                        } else if(cursor.moveToNext()){
                            basketList.add(new ObjProduct(
                                    cursor.getString(cursor.getColumnIndex("name")),
                                    cursor.getInt(cursor.getColumnIndex("image_id")),
                                    cursor.getInt(cursor.getColumnIndex("count")),
                                    cursor.getFloat(cursor.getColumnIndex("coast")) * (cursor.getInt(cursor.getColumnIndex("count")) * 1.0F)
                            ));
                        }
                    }

                    db.close();
                    cursor.close();
                }catch (Exception e){
                    Toast toast = Toast.makeText(BasketActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
                    toast.show();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (basketList.size() == 0)
                            textEmpty.setVisibility(View.VISIBLE);
                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        //В фоне пытаемся выгрузить дайнные из таблицы - Конец


        BasketAdapter adapter = new BasketAdapter(basketList);
        adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        view.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(BasketActivity.this);
        view.setLayoutManager(manager);

        //Adapter Listener начало
        adapter.setIncrementListener(new BasketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                basketList.get(position).incrementCount();
                String productName = basketList.get(position).getNameObject();
                int productCount = basketList.get(position).getCount();
                adapter.notifyItemChanged(position);
                updateObj(productName, position, productCount);
            }
        });


        adapter.setDecrementListener(new BasketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                basketList.get(position).decrementCount();
                String productName = basketList.get(position).getNameObject();
                int productCount = basketList.get(position).getCount();
                if (basketList.get(position).getCount() == 0){
                    basketList.remove(position);
                    adapter.notifyItemRemoved(position);
                    updateObj(productName, position, productCount);
//                    basketList.remove(position);
                } else {
                    adapter.notifyItemChanged(position);
                    updateObj(productName, position, productCount);
                }
            }
        });
        //Adapter Listener конец
    }

    private void updateObj(String name, int position, int count){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteOpenHelper helper = new LocalDatabaseHelper(BasketActivity.this);

                try {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    values.put("count", count);
                    db.update(basketTable, values, "name  = ?", new String[] {name});
                    db.close();
                }catch (Exception e){
                    Toast toast = Toast.makeText(BasketActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
                    toast.show();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }


}