package net.artgamestudio.rgatest.data.dao.base;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Ailton on 29/05/2017.<br>
 *
 * Helper class for open and close database
 */
public class DBConnection {

    private static SQLiteDatabase mDB;
    private static int mOpenedConnections;

    /**
     * Obtem uma conexão com o banco
     * @param dbHelper O DBHelper da dao
     * @throws Exception
     */
    public synchronized static SQLiteDatabase getConnection(DBHelper dbHelper){

        try
        {
            //Se houver banco, retorna o mesmo
            if (mDB == null) {
                mDB = dbHelper.getWritableDatabase();
            }

            //Informa que existe uma nova conexão aberta
            mOpenedConnections++;
        }
        catch (Exception error)
        {
            Log.e("Error", "Erro ao abrir conexão com o banco. " + error.getMessage());
        }

        return mDB;
    }

    /**
     * Fecha a conexão com o banco
     */
    public synchronized static void closeConnection() {

        try
        {
            //se houver mais instancias, mantem aberto
            if (mOpenedConnections > 0) {
                mOpenedConnections--;
            }

            //se não houver mais conexões
            if (mOpenedConnections == 0) {
                mDB.close();
                mDB = null;
            }
        }
        catch (Exception error)
        {
            Log.e("Error", "Erro ao fechar conexão com o banco. " + error.getMessage());
        }
    }
}
