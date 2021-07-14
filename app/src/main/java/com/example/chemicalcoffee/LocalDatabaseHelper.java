package com.example.chemicalcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ChemicalCoffee";
    private static final int DB_VERSION = 9;

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
                +"count INTEGER);"
        );

        database.execSQL(
                "CREATE TABLE FOOD ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name TEXT,"
                        +"descr TEXT,"
                        +"image_id INTEGER,"
                        +"count INTEGER);"
        );

        database.execSQL(
                "CREATE TABLE STORE ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"name TEXT,"
                        +"descr TEXT,"
                        +"image_id INTEGER,"
                        +"count INTEGER);"
        );

        insertValue(database,
                "DRINK",
                "Американо",
                "Премиальный сорт кофе из самой Колумбии.\n Нежная обжарка, терпкий вкус и заряд энергии на целый день, а то и недели.",
                R.drawable.americano);
        insertValue(database,
                "DRINK",
                "Капучино",
                "Нежный молочный напиток, с шапкой из пены.\n Вам не стоит переживать не о чем, все мысли позади когда делаешь глоток этого напитка",
                R.drawable.cappucino);
        insertValue(database,
                "DRINK",
                "Какао",
                "Холодно на улице и ничто не радует?\n Может стоит взять греющий какао? Он позволит вам не только согреться, но и настроится на приятные воспоминания",
                R.drawable.cacao);
        insertValue(database,
                "DRINK",
                "Раф",
                "Вам хотелось что-то сливочное?\n Мы можем предложить сливочный раф. Вы можете добавить все сиропы по вкусу, чтобы ваш день стал намного лучше.",
                R.drawable.raf);
        insertValue(database,
                "FOOD",
                "Бургер",
                "Хочется поесть, но не знаешь что?\n Мы уверены, что вы не откажитесь от нашего фирменного бургера из мраморной говядины и картошки фри.",
                R.drawable.burger);
        insertValue(database,
                "FOOD",
                "Рол с курицей",
                "Наш фирменный рол с курицей перебьет всё желание покупать шаверму.\n Нежные кусочки курицы с соусом тартар, поразят даже самых искушенных гурманов",
                R.drawable.chicken_roll);
        insertValue(database,
                "FOOD",
                "Шницель",
                "Наш шницель отлично подойдет тем, кто хочет поесть и не думать о еде целый день.\n Только самая лучшая свинина в шикарной панировке.",
                R.drawable.shnicel);
        insertValue(database,
                "STORE",
                "Удельная",
                "Приятное место со своим внетренним вайбом. Здесь можно подумать о высоком и не обращать внимание на низменность.\n" +
                        "ул. Гаврская д.5",
                R.drawable.tipo_udelnaya);
        insertValue(database,
                "STORE",
                "Озерки",
                "Современный декор, бесплатный Wi-Fi, это место подходит для создание самых амбициозных стартапов",
                R.drawable.tipo_lunacharka);
    }

    //insert value in table
    private static void insertValue(SQLiteDatabase db, String table, String name, String description, int image_id){
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("descr", description);
        values.put("image_id", image_id);
        values.put("count", 0);
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
