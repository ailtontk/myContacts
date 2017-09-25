package net.artgamestudio.rgatest.ui.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.rn.ContactRN;
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
    @BindView(R.id.tvEmail) TextView tvEmail;
    @BindView(R.id.tvBorn) TextView tvBorn;
    @BindView(R.id.tvBio) TextView tvBio;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private Contact mContact;
    private Long mContactId;

    /***** CONSTANTS *****/
    private final int REQUEST_EDIT_CONTACT = 1;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_contact_info;
    }

    @Override
    public void getParam() throws Exception {
        mContactId = getIntent().getLongExtra(Param.Intent.CONTACT_ID, -1);
    }

    @Override
    public void setupData() throws Exception {
        setSupportActionBar(toolbar);

        mContactRN = new ContactRN(this, this);
        mContact = mContactRN.getContact(mContactId);

        //If has photo, put it on screen
        if (mContact.getPhoto() != null && mContact.getPhoto().length() > 0) {
            //Defines the image size
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();

            //Put on screen
            Glide.with(this)
                    .load(mContact.getPhoto())
                    .apply(requestOptions)
                    .into(ivPhoto);
        }

        addContactInfoOnFields(mContact);
    }

    /**
     * Adds the contact info on screen
     */
    private void addContactInfoOnFields(Contact contact) throws Exception {
        colToolbar.setTitle(contact.getName());
        tvEmail.setText(contact.getEmail());
        tvBorn.setText(contact.getBorn());
        tvBio.setText(contact.getBio());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_contact_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            // Checks the touched menu
            switch (item.getItemId()) {
                // If touched at back button
                case android.R.id.home:
                    finish();
                    break;

                // If touched at edit button
                case R.id.mnuEdit:
                    startEditContactActivity();
                    break;
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onOptionsItemSelected in " + getClass().getName() + ". " + error.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            //If its coming from edit contact screen, updates the contact object and updates screen
            if (requestCode == REQUEST_EDIT_CONTACT) {
                mContact = mContactRN.getContact(data.getLongExtra(Param.Intent.CONTACT_ID, -1));
                addContactInfoOnFields(mContact);
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onActivityResult in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    private void startEditContactActivity() {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra(Param.Intent.CONTACT_ID, mContactId);
        startActivityForResult(intent, REQUEST_EDIT_CONTACT);
    }
}
