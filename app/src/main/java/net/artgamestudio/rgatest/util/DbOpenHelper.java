package net.artgamestudio.rgatest.util;

import android.content.Context;

import net.artgamestudio.rgatest.data.pojo.DaoMaster;

/**
 * Created by Ailton on 22/09/2017 for artGS.<br>
 * Used to
 */
public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }
}
