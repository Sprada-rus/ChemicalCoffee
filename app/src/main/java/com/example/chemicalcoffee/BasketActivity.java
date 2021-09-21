package com.example.chemicalcoffee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    private final String basketTable = "BASKET";
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

        TextView devText = (TextView)  findViewById(R.id.textView);
        devText.setVisibility(View.INVISIBLE);

        RecyclerView view = (RecyclerView) findViewById(R.id.basket_list);

        //В фоне пытаемся выгрузить дайнные из таблицы - Начало
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteOpenHelper helper = new LocalDatabaseHelper(BasketActivity.this);

                try {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    Cursor cursor = db.query(basketTable, new String[] {"_id", "name_product", "count_product", "table_product", "coast", "image_id"},
                            null, null, null, null, null);

                    for(int i = 0; i < cursor.getCount(); i++){
                        if (i == 0 && cursor.moveToFirst()){
                            basketList.add(new ObjProduct(
                                    cursor.getString(1),
                                    cursor.getInt(5),
                                    cursor.getInt(2),
                                    cursor.getFloat(4) * (cursor.getInt(2) * 1.0F)
                            ));
                        } else if(cursor.moveToNext()){
                            basketList.add(new ObjProduct(
                                    cursor.getString(1),
                                    cursor.getInt(5),
                                    cursor.getInt(2),
                                    cursor.getFloat(4) * (cursor.getInt(2) * 1.0F)
                            ));
                        }
                    }
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
        //В фоне пытаемся выгрузить дайнные из таблицы - Конец


        devText.setVisibility(View.INVISIBLE);
        BasketAdapter adapter = new BasketAdapter(basketList);
        adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        view.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(BasketActivity.this);
        view.setLayoutManager(manager);

    }





}