package sk.upjs.ics.android.koncovyprojekt2.provider;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import sk.upjs.ics.android.koncovyprojekt2.Defaults;

import static android.content.ContentResolver.SCHEME_CONTENT;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.ALL_COLUMNS;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.AUTOGENERATED_ID;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_CONTENT_OBSERVER;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_GROUP_BY;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_HAVING;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_NULL_COLUMN_HACK;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_SELECTION;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_SELECTION_ARGS;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_SORT_ORDER;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_TYPE;

public class NoteContentProvider extends ContentProvider {
    public static final String AUTHORITY = "sk.upjs.ics.android.koncovyprojekt2.provider.NoteContentProvider";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME_CONTENT).authority(AUTHORITY).appendPath(Provider.Test.TABLE_NAME).build();
    private static final int URI_MATCH_NOTES = 0;
    private static final int URI_MATCH_NOTE_BY_ID = 1;
    private static final String MIME_TYPE_NOTES = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + Provider.Test.TABLE_NAME;
    private static final String MIME_TYPE_SINGLE_NOTE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + Provider.Test.TABLE_NAME;
    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseOpenHelper databaseHelper;

    @Override
    public boolean onCreate() {
        uriMatcher.addURI(AUTHORITY, Provider.Test.TABLE_NAME, URI_MATCH_NOTES);
        uriMatcher.addURI(AUTHORITY, Provider.Test.TABLE_NAME + "/#", URI_MATCH_NOTE_BY_ID);
        this.databaseHelper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_NOTES:
                cursor = listNotes();
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case URI_MATCH_NOTE_BY_ID:
                long id = ContentUris.parseId(uri);
                cursor = findById(id);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return Defaults.NO_CURSOR;
        }
    }
    private Cursor findById(long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection = Provider.Test._ID + "=" + id;
        return db.query(Provider.Test.TABLE_NAME, ALL_COLUMNS, selection, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
    }
    private Cursor listNotes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return db.query(Provider.Test.TABLE_NAME, ALL_COLUMNS, NO_SELECTION, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_NOTES:
                Uri newItemUri = saveNote(values);
                getContext().getContentResolver().notifyChange(CONTENT_URI, NO_CONTENT_OBSERVER);
                return newItemUri;
            default:
                return Defaults.NO_URI;
        }
    }
    private Uri saveNote(ContentValues values) {
        ContentValues test = new ContentValues();
        test.put(Provider.Test._ID, AUTOGENERATED_ID);
        test.put(Provider.Test.TEST_TYPE, values.getAsString(Provider.Test.TEST_TYPE));
        test.put(Provider.Test.TEST_LENGTH, values.getAsString(Provider.Test.TEST_LENGTH));
        test.put(Provider.Test.TEST_DATE, values.getAsString(Provider.Test.TEST_DATE));
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long newId = db.insert(Provider.Test.TABLE_NAME, NO_NULL_COLUMN_HACK, test);
        return ContentUris.withAppendedId(CONTENT_URI, newId);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_NOTE_BY_ID:
                long id = ContentUris.parseId(uri);
                int affectedRows = databaseHelper.getWritableDatabase()
                        .delete(Provider.Test.TABLE_NAME, Provider.Test._ID + " = " + id, NO_SELECTION_ARGS);
                getContext().getContentResolver().notifyChange(CONTENT_URI,
                        NO_CONTENT_OBSERVER);
                return affectedRows;
            default:
                return 0;
        }
    }
    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_NOTE_BY_ID:
                return MIME_TYPE_SINGLE_NOTE;
            case URI_MATCH_NOTES:
                return MIME_TYPE_NOTES;
        }
        return NO_TYPE;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
