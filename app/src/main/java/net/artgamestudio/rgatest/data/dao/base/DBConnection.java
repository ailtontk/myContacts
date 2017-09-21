package net.artgamestudio.rgatest.data.dao.base;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Ailton on 29/05/2017 for artGS.<br>
 *
 * Used to open or close a DB connection
 */
public class DBConnection {

    private static SQLiteDatabase mDB;
    private static int mOpenedConnections;

    /**
     * Opens a DBConnection if there's no one opened.
     * @param dbHelper DAO's DBHelper
     */
    public synchronized static SQLiteDatabase getConnection(DBHelper dbHelper){

        try
        {
            //If Databsse already exists, returns it
            if (mDB == null) {
                mDB = dbHelper.getWritableDatabase();
            }

            //Just increment the number of active connections
            mOpenedConnections++;
        }
        catch (Exception error)
        {
            Log.e("Error", "Error opening DB connection. " + error.getMessage());
        }

        return mDB;
    }

    /**
     * Closes a DB connection
     */
    public synchronized static void closeConnection() {

        try
        {
            //If still have instances, keep db opened
            if (mOpenedConnections > 0) {
                mOpenedConnections--;
            }

            //If has no more connections, close.
            if (mOpenedConnections == 0) {
                mDB.close();
                mDB = null;
            }
        }
        catch (Exception error)
        {
            Log.e("Error", "Error closing DB connection. " + error.getMessage());
        }
    }
}
