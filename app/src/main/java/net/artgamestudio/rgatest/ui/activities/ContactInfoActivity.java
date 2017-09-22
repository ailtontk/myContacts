package net.artgamestudio.rgatest.ui.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.util.Param;

import butterknife.BindView;

/**
 * Created by Ailton on 22/09/2017 for artGS.<br>
 *
 * Activity for see contacts details
 */
public class ContactInfoActivity extends BaseActivity {

    /***** VIEWS *****/
    @BindView(R.id.colToolbar) CollapsingToolbarLayout colToolbar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ivPhoto) ImageView ivPhoto;

    /***** VARIABLES *****/
    private Contact mContact;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_contact_info;
    }

    @Override
    public void getParam() throws Exception {
        mContact = getIntent().getParcelableExtra(Param.Intent.CONTACT);
    }

    @Override
    public void setupData() throws Exception {
        setSupportActionBar(toolbar);

        Glide.with(this)
                .load(mContact.getPhoto())
                .into(ivPhoto);

        colToolbar.setTitle(mContact.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            /** Checks the touched menu **/
            switch (item.getItemId()) {
                /** If touched at back button **/
                case android.R.id.home:
                    finish();
                    break;
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onOptionsItemSelected in " + getClass().getName() + ". " + error.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }
}
