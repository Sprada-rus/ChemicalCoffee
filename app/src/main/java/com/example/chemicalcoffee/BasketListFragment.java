package com.example.chemicalcoffee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BasketListFragment extends Fragment {
    private SQLiteOpenHelper helper;
    private static final String tableName = "BASKET";
    private Cursor cursor;
    private SQLiteDatabase db;
    private ArrayList<String> captions = new ArrayList<>();
    private ArrayList<Integer> imageID = new ArrayList<>();
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<Float> amount = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView basketView = (RecyclerView) inflater.inflate(R.layout.fragment_basket_list, container, false);

        new UnloadObject().execute(tableName);

//        BasketAdapter adapter = new BasketAdapter(captions, imageID, amount, count);

//        adapter.setIncrementListener(new BasketAdapter.Listener() {
//            @Override
//            public void onClick() {
//                int tmpCount = BasketAdapter.ViewHolder.getCount();
//                Float tmpPrice;
//
//
//            }
//        });
        return inflater.inflate(R.layout.fragment_basket_list, container, false);
    }


    class UnloadObject extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            db = helper.getWritableDatabase();
            cursor = db.query(tableName, new String[] {"_id","name_product", "count_product", "table_product", "coast", "image_id"},
                    null, null,null, null, null);

            try{
               for (int i = 0; i < cursor.getCount(); i++){
                   if (i == 0  && cursor.moveToFirst()){
                       captions.add(cursor.getString(cursor.getColumnIndex("name_product")));
                       imageID.add(cursor.getInt(cursor.getColumnIndex("image_id")));
                       count.add(cursor.getInt(cursor.getColumnIndex("count_product")));
                       amount.add(cursor.getFloat(cursor.getColumnIndex("coast")));
                   } else{
                       captions.add(cursor.getString(cursor.getColumnIndex("name_product")));
                       imageID.add(cursor.getInt(cursor.getColumnIndex("image_id")));
                       count.add(cursor.getInt(cursor.getColumnIndex("count_product")));
                       amount.add(cursor.getFloat(cursor.getColumnIndex("coast")));
                   }

               }
            } catch (Exception e){

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}