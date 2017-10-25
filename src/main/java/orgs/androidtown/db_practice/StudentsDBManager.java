package orgs.androidtown.db_practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jisang on 2017-09-26.
 */

public class StudentsDBManager extends SQLiteOpenHelper{
    //DB명, 테이블명, DB 버전 정보를 정의한다.
    //==================================================================
    static final String DB_STUDENTS = "Students.db";
    static final String TABLE_STUDENTS = "Students";
    static final int DB_VERSION = 2;
    Context mContext = null;

    private static StudentsDBManager mDbManager = null;
    private SQLiteDatabase mDatabase = null;

    //DB 매니저 객체는 싱글톤으로 구현한다.
    //===================================================================
    public static StudentsDBManager getInstance(Context context) {
        if (mDbManager == null) {
            mDbManager = new StudentsDBManager(context, DB_STUDENTS, null, DB_VERSION);
        }
        return mDbManager;
    }
    //====================================================================

    private StudentsDBManager(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, dbName, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "number TEXT, " +
                "name TEXT, " +
                "department TEXT, " +
                "age TEXT, " +
                "grade INTEGER ); ");
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion<newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STUDENTS);
            onCreate(db);
        }

    }


    public long insert(ContentValues addRowValue) {
        //execSql함수를 이용해서 직접 sql문으로 레코드를 추가할 수 있다.

        return getWritableDatabase().insert(TABLE_STUDENTS, null, addRowValue);

    }

    public int insertAll(ContentValues[] values){

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        for(ContentValues contentValues : values){
            db.insert(TABLE_STUDENTS, null, contentValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return values.length;
    }

    public Cursor query(String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderBy) {

        return getReadableDatabase().query(TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int update(ContentValues updateRowValue, String whereClause, String[] whereArgs) {
        return getWritableDatabase().update(TABLE_STUDENTS,
                                updateRowValue,
                                whereClause,
                                    whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs){
        // exeSQL 함수를 이용해서 직접 SQL문으로 레코드를 삭제할 수도 있다.
        /*-----------------------------------------------------------------
        mStudentsDB.exeSQL(" DELETE FROM " + TABLE_STUDENTS);
        -------------------------------------------------------------------*/

        return getWritableDatabase().delete(TABLE_STUDENTS, whereClause, whereArgs);
    }



}






















