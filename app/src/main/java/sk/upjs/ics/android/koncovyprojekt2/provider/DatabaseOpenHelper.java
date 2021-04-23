package sk.upjs.ics.android.koncovyprojekt2.provider;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sk.upjs.ics.android.koncovyprojekt2.Defaults;

import static android.provider.BaseColumns._ID;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.DEFAULT_CURSOR_FACTORY;
import static sk.upjs.ics.android.koncovyprojekt2.provider.Provider.Test.TABLE_NAME;
import static sk.upjs.ics.android.koncovyprojekt2.provider.Provider.Test.TEST_DATE;
import static sk.upjs.ics.android.koncovyprojekt2.provider.Provider.Test.TEST_LENGTH;
import static sk.upjs.ics.android.koncovyprojekt2.provider.Provider.Test.TEST_TYPE;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "test";
    public static final int DATABASE_VERSION = 1;
    public DatabaseOpenHelper(Context context) { super(context, DATABASE_NAME, DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSql());
        insertSampleEntry(db, "Antigen" , "21dni" , "7");

    }

    private void insertSampleEntry(SQLiteDatabase db, String type , String length , String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_TYPE, type);
        contentValues.put(TEST_LENGTH, length);
        contentValues.put(TEST_DATE, date);
        db.insert(TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValues);
    }
    private String createTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TYPE,"
                + "%s LENGTH,"
                + "%s DATE"
                + ")";
        return String.format(sqlTemplate, TABLE_NAME, _ID, TEST_TYPE , TEST_LENGTH , TEST_DATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }
}