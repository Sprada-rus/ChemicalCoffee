package com.example.chemicalcoffee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CardObjectActivity extends AppCompatActivity {
    public static final String EXTRA_OBJ_ID = "obj_id";
    public static final String EXTRA_TABLE = "table";
    private Cursor cursor;
    private SQLiteDatabase db;
    private TextView objCount;
    private TextView objName;
    private TextView objAmount;
    private ImageView objImage;
    private int imgId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_object);

        int objID = (Integer) getIntent().getExtras().get(EXTRA_OBJ_ID);
        String nameTable = (String) getIntent().getExtras().get(EXTRA_TABLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) toolbar.findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button decrementBtn = (Button) findViewById(R.id.decrementCount);
        Button incrementBtn = (Button) findViewById(R.id.incrementCount);

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper helper = new LocalDatabaseHelper(CardObjectActivity.this);
                int count = Integer.parseInt((String) objCount.getText());
                count++;
                ContentValues values1 = new ContentValues();
                ContentValues values2 = new ContentValues();
                values1.put("count", count);
                values2.put("count_product", count);
                try {
                    db = helper.getWritableDatabase();
                    db.update(nameTable, values1, "_id = ?", new String[] {Integer.toString(objID)});
                    objCount.setText(String.valueOf(count));
                    if (count == 1){
                        values2.put("product_id", objID);
                        values2.put("name_product", String.valueOf(objName.getText()));
                        values2.put("table_product", nameTable);
                        values2.put("coast", Float.valueOf((String) objAmount.getText()));
                        values2.put("image_id", imgId);
                        db.insert("BASKET", null, values2);
                    } else if (count > 1) {
                        db.update("BASKET", values2, "product_id = ?", new String[] {Integer.toString(objID)});
                        objCount.setText(String.valueOf(count));
                    }
                } catch (SQLException e){
                    Toast toast = Toast.makeText(CardObjectActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper helper = new LocalDatabaseHelper(CardObjectActivity.this);
                int count = Integer.parseInt((String) objCount.getText());
                ContentValues values1 = new ContentValues();

                try {
                    db = helper.getWritableDatabase();
                    cursor = db.query("PRODUCT", new String[] {"name"}, "name = ?", new String[] {String.valueOf(objName.getText())},
                            null, null, null);
                    if (count > 0) {
                        count--;
                        values1.put("count", count);
                        db.update("PRODUCT", values1, "_id = ?", new String[] {Integer.toString(objID)});
                        objCount.setText(String.valueOf(count));
                    }

                } catch (SQLException e){
                    Toast toast = Toast.makeText(CardObjectActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        onLoadValue(objID, nameTable);
    }

    private void onLoadValue(int id, String table){
       SQLiteOpenHelper helper = new LocalDatabaseHelper(CardObjectActivity.this);

       try {
           db = helper.getReadableDatabase();
           cursor = db.query(table, new String[] {"_id", "name", "descr", "image_id", "count", "coast"}, "_id = ?",
                   new String[] {Integer.toString(id)}, null,null,null);

           if (cursor.moveToFirst()){
               objImage = (ImageView) findViewById(R.id.img_card_obj);
               objName = (TextView) findViewById(R.id.name_card_obj);
               TextView objDescription = (TextView) findViewById(R.id.description_card_obj);
               objCount = (TextView) findViewById(R.id.count);
               objAmount = (TextView) findViewById(R.id.amount);

               objName.setText(cursor.getString(1));
               objDescription.setText(cursor.getString(2));
               objImage.setContentDescription(cursor.getString(1));
               objImage.setImageResource(cursor.getInt(3));
               imgId = cursor.getInt(3);
               objCount.setText(String.valueOf(cursor.getInt(4)));
               objAmount.setText(String.valueOf(cursor.getFloat(5)));
           }

           cursor.close();
           db.close();
       } catch (SQLException e){
           Toast toast = Toast.makeText(CardObjectActivity.this, R.string.error_load, Toast.LENGTH_SHORT);
           toast.show();
       }

    }
}