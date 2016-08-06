package connect.exzeo.com.connect.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import connect.exzeo.com.connect.modal.User;
import connect.exzeo.com.connect.modal.UserSkills;

/**
 * Created by subodh on 5/8/16.
 */
public class DatabaseUtility {

    DatabaseHelper db = null;

    public DatabaseUtility(DatabaseHelper db){
        this.db = db;
    }

    public synchronized void cleanDatabase(){
        Thread thread =	new Thread (){
            public void run(){
                try{
//                    deleteAllRecords(TokenDao.ACCESS_TOKEN_TABLE_NAME);
//                    deleteAllRecords(LocationDao.CURRENT_LOCATION_TABLE_NAME);
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    db.close();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteAllRecords(String tableName){
        try {
            db.open();
            SQLiteDatabase sqLite = db.getSQLiteDatabaseObject();
            sqLite.delete(tableName, null,null);		//Delete all the records from the table
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void populateUserTable(User user){
        try {
            deleteAllRecords(UserDao.USER_TABLE_NAME);
            UserDao userDao = new UserDao(db);

            userDao.insertUserData(user);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized User getUserData(){
        try {
            UserDao userDao = new UserDao(db);

            return userDao.getUserData();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void populateUserSkillsTable(List<UserSkills> userSkillsList){
        try {
            deleteAllRecords(UserSkillsDao.USER_SKILLS_TABLE_NAME);
            UserSkillsDao userSkillsDao = new UserSkillsDao(db);

            userSkillsDao.insertUserSkillData(userSkillsList);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized List<UserSkills> getUserSkillsData(){
        try {
            UserSkillsDao userSkillsDao = new UserSkillsDao(db);

            return userSkillsDao.getUserSkillsData();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
