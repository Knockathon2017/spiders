package connect.exzeo.com.connect.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import connect.exzeo.com.connect.modal.Skills;
import connect.exzeo.com.connect.modal.UserSkills;

/**
 * Created by subodh on 5/8/16.
 */
public class UserSkillsDao {

    public static final String USER_SKILLS_TABLE_NAME = "user_skills";

    public static final String USER_SKILLS_TABLE_KEY_ID = "_id";
    public static final String USER_SKILLS_TABLE_KEY_PHONE_NUMBER = "phone_number";
    public static final String USER_SKILLS_TABLE_KEY_SKILL_ID = "skill_id";


    public static final int USER_SKILLS_TABLE_KEY_ID_COLUMN_INDEX = 0;
    public static final int USER_SKILLS_TABLE_KEY_PHONE_NUMBER_COLUMN_INDEX = 1;
    public static final int USER_SKILLS_TABLE_KEY_SKILL_ID_COLUMN_INDEX = 2;


    public static final String USER_SKILL_TABLE_CREATE_COMMAND =
            "CREATE TABLE " + USER_SKILLS_TABLE_NAME + "(" + USER_SKILLS_TABLE_KEY_ID + " integer primary key autoincrement, "
                    + USER_SKILLS_TABLE_KEY_PHONE_NUMBER + " text not null, "  + USER_SKILLS_TABLE_KEY_SKILL_ID + " text not null);";

    private DatabaseHelper db = null;

    public UserSkillsDao(DatabaseHelper db) {
        this.db = db;
    }


    public boolean insertUserSkillData(List<UserSkills> userSkillsList)
    {
        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            //Prepare ContentValues object in order to insert the records to SQLite database
            ContentValues initialValues = new ContentValues();
            //Insert data in to database table

            for(int i = 0 ; i < userSkillsList.size() ; i++) {
                UserSkills userSkills = userSkillsList.get(i);
                initialValues.put(USER_SKILLS_TABLE_KEY_PHONE_NUMBER, userSkills.getPhoneNumber());
                initialValues.put(USER_SKILLS_TABLE_KEY_SKILL_ID, userSkills.getUserSkills());

                sqLite.insert(USER_SKILLS_TABLE_NAME, null, initialValues);        //Set the table name & values to be inserted
            }

        } catch (Exception ex) {
            return false;
        }finally{
            db.close();
        }
        return true;
    }


    public List<UserSkills> getUserSkillsData() throws SQLException {
        List<UserSkills> userSkillsList = new ArrayList<UserSkills>();
        UserSkills userSkills = null;

        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            Cursor cursor = null;
            //Create the cursor object to get the records
            cursor = sqLite.query(USER_SKILLS_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();		//Move the cursor to first record
            //Iterate through each record of offers table
            while (cursor.isAfterLast() == false) {
                userSkills = new UserSkills();

                userSkills.setPhoneNumber(cursor.getString(USER_SKILLS_TABLE_KEY_PHONE_NUMBER_COLUMN_INDEX));
                userSkills.setUserSkills(cursor.getString(USER_SKILLS_TABLE_KEY_SKILL_ID_COLUMN_INDEX));

                userSkillsList.add(userSkills);
                cursor.moveToNext();
            }

            cursor.close();		//Close the cursor object

        } catch(Exception ex) {
            throw new SQLException();
        }finally{
            db.close();
        }
        return userSkillsList;
    }

}
