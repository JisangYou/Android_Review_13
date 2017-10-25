package orgs.androidtown.db_practice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Jisang on 2017-09-27.
 */

public class StudentsProvider extends ContentProvider {

    public StudentsDBManager mDbManager = null;
    private static final String AUTHORITY = "orgs.androidtown.db_practice";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/students");
    private static final int STUDENT_ALL = 1;
    private static final int STUDENT_ONE = 2;

    private static UriMatcher STUDENTS_URI_MATCHER = null;

    static {
        STUDENTS_URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        STUDENTS_URI_MATCHER.addURI(AUTHORITY, "students", STUDENT_ALL);
        STUDENTS_URI_MATCHER.addURI(AUTHORITY, "students/#", STUDENT_ONE);
    }

    @Override
    public boolean onCreate() {
        mDbManager = StudentsDBManager.getInstance(getContext());
        return true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (STUDENTS_URI_MATCHER.match(uri)) {
            case STUDENT_ALL:
                long rowId = mDbManager.insert(values);
                if (rowId > 0) {
                    return ContentUris.withAppendedId(CONTENT_URI, rowId);
                }
                break;
        }
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return mDbManager.insertAll(values);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (STUDENTS_URI_MATCHER.match(uri)) {
            case STUDENT_ONE:
                selection = "_id=" + ContentUris.parseId(uri);
                selectionArgs = null;
                break;

            case STUDENT_ALL:
                break;

            case UriMatcher.NO_MATCH:
                return null;
        }
        return mDbManager.query(projection, selection, selectionArgs, null, null, sortOrder);
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int updateCnt = 0;
        switch (STUDENTS_URI_MATCHER.match(uri)) {
            case STUDENT_ONE:
                selection = "_id=" + ContentUris.parseId(uri);
                selectionArgs = null;
                break;

            case STUDENT_ALL:
                break;

            case UriMatcher.NO_MATCH:
                return 0;
        }
        updateCnt = mDbManager.update(values, selection, selectionArgs);

        return updateCnt;


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (STUDENTS_URI_MATCHER.match(uri)){
            case STUDENT_ONE:
                selection="_id"+ContentUris.parseId(uri);
                selectionArgs = null;
                break;
            case STUDENT_ALL:
                break;
            case UriMatcher.NO_MATCH:
                return 0;
        }

        return mDbManager.delete(selection, selectionArgs);
    }


}
