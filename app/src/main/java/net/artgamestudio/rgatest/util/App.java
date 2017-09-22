package net.artgamestudio.rgatest.util;

import android.app.Application;

import net.artgamestudio.rgatest.data.pojo.DaoMaster;
import net.artgamestudio.rgatest.data.pojo.DaoSession;

/**
 * Created by Ailton on 22/09/2017 for artGS.<br>
 *
 * Application for GreenDAO
 */
public class App extends Application {

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        //Create a instance of DaoSession when app is opening
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, Param.Database.NAME).getWritableDatabase()).newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
