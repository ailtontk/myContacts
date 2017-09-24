package net.artgamestudio.rgatest.ui.activities;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.util.Param;
import net.artgamestudio.rgatest.util.Util;

import butterknife.BindView;

/**
 * Created by Ailton on 24/09/2017 for artGS.<br>
 * <p>
 * Used to edit or create contact info
 */
public class EditContactActivity extends BaseActivity {

    /***** VIEWS *****/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etBorn)
    EditText etBorn;
    @BindView(R.id.etBio)
    EditText etBio;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private Contact mContact;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_edit_contact;
    }

    @Override
    public void getParam() throws Exception {
        mContact = getIntent().getParcelableExtra(Param.Intent.CONTACT);
    }

    @Override
    public void setupData() throws Exception {
        setSupportActionBar(toolbar);
        mContactRN = new ContactRN(this, this);

        if (mContact != null) {
            addContactInfoOnFields(mContact);
        }
    }

    /**
     * Adds the contact info on screen
     */
    private void addContactInfoOnFields(Contact contact) throws Exception {
        //If has photo, put it on screen
        if (contact.getPhoto() != null && contact.getPhoto().length() > 0) {
            //Defines the image size
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();

            //Put on screen
            Glide.with(this)
                    .load(mContact.getPhoto())
                    .apply(requestOptions)
                    .into(ivPhoto);
        }

        etName.setText(contact.getName());
        etEmail.setText(contact.getEmail());
        etBorn.setText(contact.getBorn());
        etBio.setText(contact.getBio());
    }

    /**
     * Get data on all fields to return a contact object with new user information
     *
     * @return Edited contact
     */
    private Contact getEditedContact() throws Exception {
        Contact contact = new Contact();
        contact.setName(etName.getText().toString());
        contact.setEmail(etEmail.getText().toString());
        contact.setBorn(etBorn.getText().toString());
        contact.setBio(etBio.getText().toString());
        return contact;
    }

    /**
     * Validate if user has wrote on all fields correctly
     *
     * @return true if its correctly wrote, false otherwise.
     */
    private boolean validateFields() throws Exception {

        //Validate name
        if (etName.getText().toString().length() == 0) {
            etName.setError(getString(R.string.edit_name_empty));
            return false;
        }

        //Validate e-mail
        if (etEmail.getText().toString().length() == 0) {
            etEmail.setError(getString(R.string.edit_mail_empty));
            return false;
        }
        //if isn't empty, check if is valid
        else if (!Util.isEmailValid(etEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.edit_mail_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Validate date
        if (etBorn.getText().toString().length() == 0) {
            etBorn.setError(getString(R.string.edit_born_hint));
            return false;
        }

        //Validate name
        if (etBio.getText().toString().length() == 0) {
            etBio.setError(getString(R.string.edit_bio_empty));
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            // Checks the touched menu
            switch (item.getItemId()) {
                // If touched at back button
                case android.R.id.home:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;

                // If touched at edit button
                case R.id.mnuEdit:
                    if (!validateFields())
                        break;

                    boolean result = mContactRN.saveContactOnDatabase(mContact, getEditedContact());
                    setResult(result ? RESULT_OK : RESULT_CANCELED);
                    finish();
                    break;
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onOptionsItemSelected in " + getClass().getName() + ". " + error.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }
}
