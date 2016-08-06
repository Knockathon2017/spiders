package connect.exzeo.com.connect.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import connect.exzeo.com.connect.modal.User;

/**
 * Created by subodh on 5/8/16.
 */
public class UserDao {
    public static final String USER_TABLE_NAME = "user";

    public static final String USER_TABLE_KEY_ID = "_id";
    public static final String USER_TABLE_KEY_PHONE_NUMBER = "phone_number";
    public static final String USER_TABLE_KEY_FIRST_NAME = "first_name";
    public static final String USER_TABLE_KEY_LAST_NAME = "last_name";
    public static final String USER_TABLE_KEY_EMAIL = "email";
    public static final String USER_TABLE_KEY_PASSWORD = "password";
    public static final String USER_TABLE_KEY_GENDER = "gender";
    public static final String USER_TABLE_KEY_ROLE = "role_id";
    public static final String USER_TABLE_KEY_LAT = "lat";
    public static final String USER_TABLE_KEY_LONG = "long";
    public static final String USER_TABLE_KEY_RATINGS = "ratings";
    public static final String USER_TABLE_KEY_SERVICES = "services_id";


    public static final int USER_TABLE_KEY_ID_COLUMN_INDEX = 0;
    public static final int USER_TABLE_KEY_PHONE_NUMBER_COLUMN_INDEX = 1;
    public static final int USER_TABLE_KEY_FIRST_NAME_COLUMN_INDEX = 2;
    public static final int USER_TABLE_KEY_LAST_NAME_COLUMN_INDEX = 3;
    public static final int USER_TABLE_KEY_EMAIL_COLUMN_INDEX = 4;
    public static final int USER_TABLE_KEY_PASSWORD_COLUMN_INDEX = 5;
    public static final int USER_TABLE_KEY_GENDER_COLUMN_INDEX = 6;
    public static final int USER_TABLE_KEY_ROLE_COLUMN_INDEX = 7;
    public static final int USER_TABLE_KEY_LAT_COLUMN_INDEX = 8;
    public static final int USER_TABLE_KEY_LONG_COLUMN_INDEX = 9;
    public static final int USER_TABLE_KEY_RATINGS_COLUMN_INDEX = 10;
    public static final int USER_TABLE_KEY_SERVICES_COLUMN_INDEX = 11;


    public static final String USER_TABLE_CREATE_COMMAND =
        "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_TABLE_KEY_ID + " integer primary key autoincrement, "
        + USER_TABLE_KEY_PHONE_NUMBER + " text, " + USER_TABLE_KEY_FIRST_NAME + " text, "
        + USER_TABLE_KEY_LAST_NAME + " text, " + USER_TABLE_KEY_EMAIL + " text, "
        + USER_TABLE_KEY_PASSWORD + " text, " + USER_TABLE_KEY_GENDER + " text, "
        + USER_TABLE_KEY_ROLE + " text, " + USER_TABLE_KEY_LAT + " text, "
        + USER_TABLE_KEY_LONG + " text,"
        + USER_TABLE_KEY_RATINGS + " text, " + USER_TABLE_KEY_SERVICES + " text);";

    private DatabaseHelper db = null;

    public UserDao(DatabaseHelper db) {
        this.db = db;
    }


    public boolean insertUserData(User user)
    {
        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            //Prepare ContentValues object in order to insert the records to SQLite database
            ContentValues initialValues = new ContentValues();
            //Insert data in to database table
            initialValues.put(USER_TABLE_KEY_PHONE_NUMBER, user.getMobile());
            initialValues.put(USER_TABLE_KEY_FIRST_NAME, user.getFirstName());
            initialValues.put(USER_TABLE_KEY_LAST_NAME, user.getLastName());
            initialValues.put(USER_TABLE_KEY_EMAIL, user.getEmail());
            initialValues.put(USER_TABLE_KEY_PASSWORD, user.getPassword());
            initialValues.put(USER_TABLE_KEY_GENDER, user.getGender());
            initialValues.put(USER_TABLE_KEY_ROLE, user.getRole());
            initialValues.put(USER_TABLE_KEY_LAT, user.getLatitude());
            initialValues.put(USER_TABLE_KEY_LONG, user.getLongitude());
            initialValues.put(USER_TABLE_KEY_RATINGS, user.getRatings());
            initialValues.put(USER_TABLE_KEY_SERVICES, user.getServices());

            sqLite.insert(USER_TABLE_NAME, null, initialValues);		//Set the table name & values to be inserted

        } catch (Exception ex) {
            return false;
        }finally{
            db.close();
        }
        return true;
    }


    public User getUserData() throws SQLException {
        User user = null;

        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            Cursor cursor = null;
            //Create the cursor object to get the records
            cursor = sqLite.query(USER_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();		//Move the cursor to first record
            //Iterate through each record of offers table
            user = new User();
            user.setMobile(cursor.getString(USER_TABLE_KEY_PHONE_NUMBER_COLUMN_INDEX));
            user.setFirstName(cursor.getString(USER_TABLE_KEY_FIRST_NAME_COLUMN_INDEX));
            user.setLastName(cursor.getString(USER_TABLE_KEY_LAST_NAME_COLUMN_INDEX));
            user.setEmail(cursor.getString(USER_TABLE_KEY_EMAIL_COLUMN_INDEX));
            user.setPassword(cursor.getString(USER_TABLE_KEY_PASSWORD_COLUMN_INDEX));
            user.setGender(cursor.getString(USER_TABLE_KEY_GENDER_COLUMN_INDEX));
            user.setRole(cursor.getString(USER_TABLE_KEY_ROLE_COLUMN_INDEX));
            user.setLatitude(cursor.getString(USER_TABLE_KEY_LAT_COLUMN_INDEX));
            user.setLongitude(cursor.getString(USER_TABLE_KEY_LONG_COLUMN_INDEX));
            user.setRatings(cursor.getString(USER_TABLE_KEY_RATINGS_COLUMN_INDEX));
            user.setServices(cursor.getString(USER_TABLE_KEY_SERVICES_COLUMN_INDEX));


            cursor.close();		//Close the cursor object

        } catch(Exception ex) {
            ex.printStackTrace();
            throw new SQLException();
        }finally{
            db.close();
        }
        return user;
    }
}
