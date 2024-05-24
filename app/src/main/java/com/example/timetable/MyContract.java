package com.example.timetable;

import android.provider.BaseColumns;

public final class MyContract {
    private MyContract() {} // Private constructor to prevent instantiation

    public static class MyEntry implements BaseColumns {
        public static final String TABLE_NAME = "saving";
        public static final String COLUMN_NAME_SEC_ID = "sec_id";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_LVL_ID = "lvl_id";
        public static final String COLUMN_NAME_spec_ID = "spec_id";
        public static final String COLUMN_NAME_NAME = "name"; // New column for name
    }
}

