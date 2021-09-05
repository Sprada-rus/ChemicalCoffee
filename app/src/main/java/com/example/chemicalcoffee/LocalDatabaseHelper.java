package com.example.chemicalcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ChemicalCoffee";
    private static final int DB_VERSION = 14;

    LocalDatabaseHelper(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database){

        //Create local tables for a fragment
        database.execSQL(
                "CREATE TABLE DRINK ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name TEXT,"
                        +"descr TEXT,"
                        +"image_id INTEGER,"
                        +"count INTEGER,"
                        +"coast REAL);"
        );

        database.execSQL(
                "CREATE TABLE FOOD ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name TEXT,"
                        +"descr TEXT,"
                        +"image_id INTEGER,"
                        +"count INTEGER,"
                        +"coast REAL);"
        );

        database.execSQL(
                "CREATE TABLE STORE ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name TEXT,"
                        +"descr TEXT,"
                        +"image_id INTEGER);"
        );

        database.execSQL(
                "CREATE TABLE BASKET ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name_product TEXT,"
                        +"count_product INTEGER,"
                        +"table_product TEXT,"
                        +"coast REAL,"
                        +"image_id INTEGER);"
        );

        insertValue(database,
                "DRINK",
                "Американо",
                "Премиальный сорт кофе из самой Колумбии.\nНежная обжарка, терпкий вкус и заряд энергии на целый день, а то и недели.",
                R.drawable.americano, 100F);
        insertValue(database,
                "DRINK",
                "Капучино",
                "Нежный молочный напиток, с шапкой из пены.\nВам не стоит переживать не о чем, все мысли позади когда делаешь глоток этого напитка",
                R.drawable.cappucino, 200F);
        insertValue(database,
                "DRINK",
                "Какао",
                "Холодно на улице и ничто не радует?\nМожет стоит взять греющий какао? Он позволит вам не только согреться, но и настроится на приятные воспоминания",
                R.drawable.cacao, 150F);
        insertValue(database,
                "DRINK",
                "Раф",
                "Вам хотелось что-то сливочное?\nМы можем предложить сливочный раф. Вы можете добавить все сиропы по вкусу, чтобы ваш день стал намного лучше.",
                R.drawable.raf, 120F);
        insertValue(database,
                "FOOD",
                "Бургер",
                "Хочется поесть, но не знаешь что?\nМы уверены, что вы не откажитесь от нашего фирменного бургера из мраморной говядины и картошки фри.",
                R.drawable.burger, 200.10F);
        insertValue(database,
                "FOOD",
                "Рол с курицей",
                "Наш фирменный рол с курицей перебьет всё желание покупать шаверму.\nНежные кусочки курицы с соусом тартар, поразят даже самых искушенных гурманов",
                R.drawable.chicken_roll, 150.50F);
        insertValueStore(database,
                "FOOD",
                "Шницель",
                "Наш шницель отлично подойдет тем, кто хочет поесть и не думать о еде целый день.\nТолько самая лучшая свинина в шикарной панировке.",
                R.drawable.shnicel);
        insertValueStore(database,
                "STORE",
                "Удельная",
                "Приятное место со своим внетренним вайбом. Здесь можно подумать о высоком и не обращать внимание на низменность.\n" +
                        "ул. Гаврская д.5",
                R.drawable.tipo_udelnaya);
        insertValueStore(database,
                "STORE",
                "Озерки",
                "Современный декор, бесплатный Wi-Fi, это место подходит для создание самых амбициозных стартапов",
                R.drawable.tipo_lunacharka);
    }

    //insert value in table product
    private static void insertValue(SQLiteDatabase db, String table, String name, String description, int image_id, float coast){
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("descr", description);
        values.put("image_id", image_id);
        values.put("count", 0);
        values.put("coast", coast);
        db.insert(table, null, values);
    }

    private static void insertValueStore(SQLiteDatabase db, String table, String name, String description, int image_id){
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("descr", description);
        values.put("image_id", image_id);
        db.insert(table, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion){
            db.execSQL("DROP TABLE DRINK");
            db.execSQL("DROP TABLE FOOD");
            db.execSQL("DROP TABLE STORE");
            onCreate(db);
        }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
