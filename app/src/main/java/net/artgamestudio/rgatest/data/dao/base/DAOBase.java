package net.artgamestudio.rgatest.data.dao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ailton on 27/04/2017.<br>
 * DaoBase
 */
public abstract class DAOBase<E> {

    protected DBHelper mHelper;
    protected SQLiteDatabase mDb;

    public DAOBase(Context context) {
        //Starts helper
        mHelper = new DBHelper(context);
    }

    /**
     * Returns a object from db
     * @param id the object id
     * @return The object or null if doesn't exist
     * @throws Exception
     */
    public abstract E get(String id) throws Exception;

    /**
     *  Updates a object in db
     * @throws Exception
     */
    public abstract void update(E obj) throws Exception;

    /**
     * AdInsert a object in db
     * @throws Exception
     */
    public abstract void insert(E obj) throws Exception;

    /**
     * Remove an object from db
     * @throws Exception
     */
    public abstract void remove(String id) throws Exception;
}
