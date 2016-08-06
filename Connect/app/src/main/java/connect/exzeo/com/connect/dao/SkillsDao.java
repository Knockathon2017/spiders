package connect.exzeo.com.connect.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import connect.exzeo.com.connect.modal.Skills;
import connect.exzeo.com.connect.modal.User;

/**
 * Created by subodh on 5/8/16.
 */
public class SkillsDao {
    public static final String SKILLS_TABLE_NAME = "skills";

    public static final String SKILLS_TABLE_KEY_ID = "_id";
    public static final String SKILLS_TABLE_KEY_SKILL_ID = "skill_id";
    public static final String SKILLS_TABLE_KEY_SKILL_DESCRIPTION = "skill_description";


    public static final int SKILLS_TABLE_KEY_ID_COLUMN_INDEX = 0;
    public static final int SKILLS_TABLE_KEY_SKILL_ID_COLUMN_INDEX = 1;
    public static final int SKILLS_TABLE_KEY_SKILL_DESCRIPTION_COLUMN_INDEX = 2;


    public static final String SKILL_TABLE_CREATE_COMMAND =
        "CREATE TABLE " + SKILLS_TABLE_NAME + "(" + SKILLS_TABLE_KEY_ID + " integer primary key autoincrement, "
        + SKILLS_TABLE_KEY_SKILL_ID + " text not null, "  + SKILLS_TABLE_KEY_SKILL_DESCRIPTION + " text not null);";

    private DatabaseHelper db = null;

    public SkillsDao(DatabaseHelper db) {
        this.db = db;
    }


    public boolean insertSkillData(List<Skills> skillsList)
    {
        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            //Prepare ContentValues object in order to insert the records to SQLite database
            ContentValues initialValues = new ContentValues();
            //Insert data in to database table

            for(int i = 0 ; i < skillsList.size() ; i++) {
                Skills skills = skillsList.get(i);
                initialValues.put(SKILLS_TABLE_KEY_SKILL_ID, skills.getId());
                initialValues.put(SKILLS_TABLE_KEY_SKILL_DESCRIPTION, skills.getSkillDescription());

                sqLite.insert(SKILLS_TABLE_NAME, null, initialValues);        //Set the table name & values to be inserted
            }

        } catch (Exception ex) {
            return false;
        }finally{
            db.close();
        }
        return true;
    }


    public List<Skills> getSkillsData() throws SQLException {
        List<Skills> skillsList = new ArrayList<Skills>();
        Skills skills = null;

        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            Cursor cursor = null;
            //Create the cursor object to get the records
            cursor = sqLite.query(SKILLS_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();		//Move the cursor to first record
            //Iterate through each record of offers table
            while (cursor.isAfterLast() == false) {
                skills = new Skills();

                skills.setId(cursor.getInt(SKILLS_TABLE_KEY_SKILL_ID_COLUMN_INDEX));
                skills.setSkillDescription(cursor.getString(SKILLS_TABLE_KEY_SKILL_DESCRIPTION_COLUMN_INDEX));

                skillsList.add(skills);
                cursor.moveToNext();
            }

            cursor.close();		//Close the cursor object

        } catch(Exception ex) {
            throw new SQLException();
        }finally{
            db.close();
        }
        return skillsList;
    }
}
