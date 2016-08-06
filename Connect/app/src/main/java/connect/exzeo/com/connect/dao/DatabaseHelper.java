package connect.exzeo.com.connect.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import connect.exzeo.com.connect.modal.User;

/**
 * Created by subodh on 5/8/16.
 */
public class DatabaseHelper {


    private static final String DATABASE_NAME = "connect.db";			//Database name
    private static final int DATABASE_VERSION = 1;						//Database version

    private final Context context;    		//Context object to deal with SQLiteOpenHelper
    private DBHelper dbHelper;				//To open & close the connection to the database
    private SQLiteDatabase db = null;		//SQLiteDatabase object

    //Constructor
    public  DatabaseHelper(Context ctx) {
        this.context = ctx;
        dbHelper = new DBHelper(context);
    }

    /*
     * Inner static class "DatabaseHelper" which implements SQLiteOpenHelper  used to create & upgrade the database when version is changed
     */
    private static class DBHelper extends SQLiteOpenHelper {
        //DBHelper Constructor
        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        /*
         * Will be called when their is no database exist in the device
         */
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SkillsDao.SKILL_TABLE_CREATE_COMMAND);
            db.execSQL(UserSkillsDao.USER_SKILL_TABLE_CREATE_COMMAND);
            db.execSQL(UserDao.USER_TABLE_CREATE_COMMAND);
        }

        /*
         * Whenever database version is updated this method will be called which inturn calls onCreate method to upgrade the database
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + SkillsDao.SKILL_TABLE_CREATE_COMMAND);
            db.execSQL("DROP TABLE IF EXISTS " + UserSkillsDao.USER_SKILL_TABLE_CREATE_COMMAND);
            db.execSQL("DROP TABLE IF EXISTS " + UserDao.USER_TABLE_CREATE_COMMAND);
            onCreate(db);
        }
    }

    public SQLiteDatabase getSQLiteDatabaseObject()
    {
        return db;
    }

    /*
     * Method "open" to open the connection to the existing database & returns writableDatabase (i.e.,the mode in which it allows to
     * insert / update / delete table contents)
     */
    public DatabaseHelper open() throws SQLException
    {
        try {
            db = dbHelper.getWritableDatabase();		//Get the writable database
        } catch (Exception ex) {
            System.err.println( "Exception in open method of DatabaseHelper :" + ex.toString() );
        }
        return this;
    }

    /*
     * Method "close" to close the database connection
     */
    public void close()
    {
        try {
            dbHelper.close();		//Close database connection
        } catch (Exception ex) {
            System.err.println( "Exception in close method of DatabaseHelper :" + ex.toString() );
        }
    }

}
