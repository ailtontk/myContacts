package net.artgamestudio.rgatest.ui.activities;

import android.widget.TextView;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.rn.ContactRN;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    /***** VARIABLES *****/
    private ContactRN mContactRN;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_main;
    }

    @Override
    public void setupData() throws Exception {
    }
}
