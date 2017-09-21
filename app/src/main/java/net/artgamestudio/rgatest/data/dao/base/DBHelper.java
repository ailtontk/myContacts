package net.artgamestudio.rgatest.data.dao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.artgamestudio.rgatest.util.Param;

/**
 * Created by Ailton on 23/04/2017.<br>
 * Used to open and create database operations.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    /**
     * Construtor padrão do criador do banco
     * @param context Contexto da aplicação
     */
    public DBHelper(Context context) {
        super(context, Param.Database.NAME, null, Param.Database.VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            StringBuilder sbSQL = new StringBuilder();
            sbSQL.append("CREATE TABLE contacts (name TEXT, ");
            sbSQL.append("email TEXT, ");
            sbSQL.append("born TEXT, ");
            sbSQL.append("bio TEXT, ");
            sbSQL.append("photo);");

            db.execSQL(sbSQL.toString());
        } catch (Exception error) {
            Log.e("Error", "Error while creating database " + error.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
