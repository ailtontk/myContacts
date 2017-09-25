package net.artgamestudio.rgatest.data.dao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ailton on 23/09/2017.<br>
 *
 * DAOBase
 */
public abstract class DAOBase<E> {

    protected DBHelper mHelper;
    protected SQLiteDatabase mDb;

    public DAOBase(Context context) {
        mHelper = new DBHelper(context);
    }

    /**
     * Retorna Todos os itens desta tabela banco
     * @return A Lista de itens
     */
    public abstract List<E> getAll(String...name) throws Exception;

    /**
     * Retorna o objeto da tabela
     * @param id O id do objeto
     * @return O objeto encontrado ou null se não houver
     */
    public abstract E get(Long id) throws Exception;

    public abstract void update(E obj) throws Exception;

    public abstract void insert(E obj) throws Exception;

    public abstract void remove(Long id) throws Exception;
}
