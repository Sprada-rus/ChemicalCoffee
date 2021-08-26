package com.example.chemicalcoffee;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CardStoreActivity extends AppCompatActivity {
    public static final String EXTRA_OBJ_ID = "obj_id";
    public static final String EXTRA_TABLE = "table";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_store);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) toolbar.findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int objID = (Integer) getIntent().getExtras().get(EXTRA_OBJ_ID) + 1;
        String nameTable = (String) getIntent().getExtras().get(EXTRA_TABLE);

        onLoadValue(objID, nameTable);
    }

    private void onLoadValue(int id, String table){
       SQLiteOpenHelper helper = new LocalDatabaseHelper(CardStoreActivity.this);

       try {
           SQLiteDatabase db = helper.getReadableDatabase();
           Cursor cursor = db.query(table, new String[] {"_id", "name", "descr", "image_id"}, "_id = ?",
                   new String[] {Integer.toString(id)}, null,null,null);

           if (cursor.moveToFirst()){
               ImageView objImage = (ImageView) findViewById(R.id.img_card_obj);
               TextView objName = (TextView) findViewById(R.id.name_card_obj);
               TextView objDescription = (TextView) findViewById(R.id.description_card_obj);

               objName.setText(cursor.getString(1));
               objDescription.setText(cursor.getString(2));
               objImage.setContentDescription(cursor.getString(1));
               objImage.setImageResource(cursor.getInt(3));
           }

           cursor.close();
           db.close();
       } catch (SQLException e){
           Toast toast = Toast.makeText(CardStoreActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
           toast.show();
       }

    }
}