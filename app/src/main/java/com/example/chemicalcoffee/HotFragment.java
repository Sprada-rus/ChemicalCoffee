package com.example.chemicalcoffee;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HotFragment extends Fragment {
    private SQLiteOpenHelper helper;
    private RecyclerView hotView;
    private static final String tableName = "FOOD";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        helper = new LocalDatabaseHelper(getContext());
        hotView = (RecyclerView) inflater.inflate(R.layout.fragment_hot, container, false);
        new UnloadObject().execute(tableName);


        return hotView;
    }

    private class UnloadObject extends AsyncTask<String, Void, Boolean>{
        private SQLiteDatabase db;
        private Cursor cursor;

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
                    db = helper.getReadableDatabase();
                    cursor = db.query(nameTab, new String[]{"_id", "name", "descr", "image_id"},
                    null, null, null, null, null);

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
            } else {
                try {
                    SQListImageAdapter adapter = new SQListImageAdapter(cursor, new String[]{"name", "image_id"});
                    hotView.setAdapter(adapter);

                    LinearLayoutManager layout = new LinearLayoutManager(getActivity());
                    hotView.setLayoutManager(layout);

                    adapter.setListener(new SQListImageAdapter.Listener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getContext(), CardObjectActivity.class);
                            intent.putExtra(CardObjectActivity.EXTRA_OBJ_ID, position);
                            intent.putExtra(CardObjectActivity.EXTRA_TABLE, tableName);
                        }
                    });
                    cursor.close();
                    db.close();
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
}