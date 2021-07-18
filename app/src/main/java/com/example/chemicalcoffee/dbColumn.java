package com.example.chemicalcoffee;

import android.provider.BaseColumns;

public class dbColumn {

    public static final class EnterColumn implements BaseColumns{
        public static final String ID_VERSION = "_id";
        public static final String OBJECT_NAME = "name";
        public static final String OBJECT_DESCRIPTION = "descr";
        public static final String IMAGE_ID = "image_id";
        public static final String COUNT_OBJECT = "count";
    }
}
