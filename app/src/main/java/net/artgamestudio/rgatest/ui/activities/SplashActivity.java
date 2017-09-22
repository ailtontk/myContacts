package net.artgamestudio.rgatest.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.util.Param;
import net.artgamestudio.rgatest.util.Util;
import net.artgamestudio.rgatest.util.UtilView;

import butterknife.BindView;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Called when user starts the app, will be responsable to ask for permissions,
 * check data etc
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvLogo) TextView tvLogo;

    @Override
    public int setView() throws Exception {
        setFullScreen();
        return R.layout.activity_splash;
    }

    @Override
    public void setupData() throws Exception {
        //showing up animation
        Util.changeFont(this, tvLogo);
        UtilView.animateView(this, tvLogo, 0);

        ContactRN contactRN = new ContactRN(this, this);
        contactRN.getAndImportContacts();
    }

    @Override
    protected void onReceiveData(Class fromClass, int id, boolean result, Object... objects) throws Exception {
        //If its coming from contactRN
        if (fromClass == ContactRN.class) {
            //If its the get contact service response -- The contact list is now imported
            if (id == Param.ComponentCompact.CONTACTS_IMPORTED) {
                //If has failed for some reason
                if (!result)
                    Toast.makeText(this, getString(R.string.splash_faileD_to_dowload), Toast.LENGTH_SHORT).show();

                //This operation its supposed to be fast, so after the response
                //we going to wait 2 seconds before animations run and more 1 to change to main activity
                UtilView.animateView(this, tvLogo, 2000, R.animator.anim_disapearing);
                startMainActivity(3000);
            }
        }
    }

    /**
     * Sttarts the main activity
     * @param after starts after x milliseconds. (Ex. 3000s - starts after 3 seconds)
     */
    private void startMainActivity(long after) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, after);
    }
}
