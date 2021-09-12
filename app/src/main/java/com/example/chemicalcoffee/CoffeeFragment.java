package com.example.chemicalcoffee;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class CoffeeFragment extends Fragment {
    private SQLiteOpenHelper helper;
    private static final String tableName = "PRODUCT";
    private ArrayList<String> captions = new ArrayList<String>();
    private ArrayList<Integer>  imageID = new ArrayList<Integer>();
    private ArrayList<Integer>  objID = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        helper = new LocalDatabaseHelper(getContext());
        RecyclerView coffeeView = (RecyclerView) inflater.inflate(R.layout.fragment_coffee, container, false);

        if (captions.size() == 0 && imageID.size() == 0)
            new UnloadObject().execute(tableName);

        SQListImageAdapter adapter = new SQListImageAdapter(captions, imageID, objID);
        adapter.setListener(new SQListImageAdapter.Listener() {
            @Override
            public void onClick(int position, int objID) {
                Intent intent = new Intent(getActivity(), CardObjectActivity.class);
                intent.putExtra(CardObjectActivity.EXTRA_OBJ_ID, objID);
                intent.putExtra(CardObjectActivity.EXTRA_TABLE, tableName);
                startActivity(intent);
            }
        });
        coffeeView.setAdapter(adapter);

        adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        coffeeView.setLayoutManager(layout);

        return coffeeView;
    }

    private class UnloadObject extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            //Получаем наименование таблицы в SQLite
            String nameTab = strings[0];

            //Попытка вытащить значения из таблицы
            try {
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.query(nameTab, new String[]{"_id", "name", "image_id"},
                        "type_product = ?", new String[] {"DRINK"}, null, null, null);

                for (int i = 0; i < cursor.getCount(); i++){
                    if(i == 0 && cursor.moveToFirst()) {
                        captions.add(cursor.getString(cursor.getColumnIndex(dbColumn.EnterColumn.OBJECT_NAME)));
                        imageID.add(cursor.getInt(cursor.getColumnIndex(dbColumn.EnterColumn.IMAGE_ID)));
                        objID.add(cursor.getInt(cursor.getColumnIndex(dbColumn.EnterColumn.ID_VERSION)));
                    } else {
                        cursor.moveToNext();
                        captions.add(cursor.getString(cursor.getColumnIndex(dbColumn.EnterColumn.OBJECT_NAME)));
                        imageID.add(cursor.getInt(cursor.getColumnIndex(dbColumn.EnterColumn.IMAGE_ID)));
                        objID.add(cursor.getInt(cursor.getColumnIndex(dbColumn.EnterColumn.ID_VERSION)));
                    }
                }

                cursor.close();
                db.close();
                Log.i("Success load", "CoffeeFragment success loaded");
                return true;

            } catch (SQLException e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean){
                Toast toast = Toast.makeText(getContext(), R.string.error_load, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}