package net.artgamestudio.rgatest.data.dao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.util.Param;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ailton on 23/04/2017.<br>
 *
 * DBOpen Helper class
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
            insertByFile(mContext, R.raw.database_tables, db);
        } catch (Exception error) {
            Log.e("Error", "Erro ao criar banco de dados. " + error.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(mContext, "Upgrade", Toast.LENGTH_SHORT).show();
    }

    /**
     * Le um arquivo sql da pasta raw e adiciona linha a linha
     *
     * @param context
     *
     * @param resourceId
     *  e.g. R.raw.food_db
     *
     * @return Number of SQL-Statements run
     * @throws IOException
     */
    public int insertByFile(Context context, int resourceId, SQLiteDatabase db) throws IOException {
        // reseta o contador
        int result = 0;

        // Abre o arquivo da pasta raw
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Passa pelas linhas lidas
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            db.execSQL(insertStmt);
            result++;
        }
        insertReader.close();

        // Retorna o numero de dados inseridos
        return result;
    }
}
