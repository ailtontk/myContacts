package net.artgamestudio.rgatest.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.util.Param;
import net.artgamestudio.rgatest.util.UtilView;

import butterknife.BindView;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Called when user starts the app, will be responsable to ask for permissions,
 * check data etc
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.ivLogo) ImageView ivLogo;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_splash;
    }

    @Override
    public void setupData() throws Exception {
        //showing up animation
        UtilView.animateView(this, ivLogo, 0);

        ContactRN contactRN = new ContactRN(this, this);
        contactRN.getAndImportContacts();
    }

    @Override
    protected void onReceiveData(Class fromClass, int id, boolean result, Object... objects) throws Exception {
        //If its coming from contactRN
        if (fromClass == ContactRN.class) {
            //If its the get contact service response -- The contact list is now imported
            if (id == Param.Contact.CONTACTS_IMPORTED) {
                //This operation its supposed to be fast, so after the response
                //we going to wait 1.5 seconds before animations run and more 0.5 to change to main activity
                //Animates the logo to disapear and starts main activity after 0,5s
                UtilView.animateView(this, ivLogo, 1500, R.animator.anim_disapearing);
                startMainActivity(2000);
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
            }
        }, after);
    }
}
