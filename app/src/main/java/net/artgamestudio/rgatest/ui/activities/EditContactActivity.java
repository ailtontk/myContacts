package net.artgamestudio.rgatest.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.util.CalendarHelper;
import net.artgamestudio.rgatest.util.DateFormatter;
import net.artgamestudio.rgatest.util.Param;
import net.artgamestudio.rgatest.util.RealPathUtil;
import net.artgamestudio.rgatest.util.Util;
import net.artgamestudio.rgatest.util.UtilView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ailton on 24/09/2017 for artGS.<br>
 * <p>
 * Used to edit or create contact info
 */
public class EditContactActivity extends BaseActivity {

    /***** VIEWS *****/
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ivPhoto) CircleImageView ivPhoto;
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etBorn) EditText etBorn;
    @BindView(R.id.etBio) EditText etBio;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private Contact mContact;
    private int mContactId;
    private String mSelectedPath;

    /***** CONSTANTS *****/
    private final int REQUEST_CHOOSE_PICUTURE = 1;
    private static final int PERMISSION_SDCARD = 1;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_edit_contact;
    }

    @Override
    public void getParam() throws Exception {
        mContactId = getIntent().getIntExtra(Param.Intent.CONTACT_ID, -1);
    }

    @Override
    public void setupToolbar() throws Exception {
        String toolbarText = mContactId != -1 ? getString(R.string.edit_title_edit) : getString(R.string.edit_title_create);
        toolbar.setTitle(toolbarText);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupData() throws Exception {
        mContactRN = new ContactRN(this, this);

        mContact = mContactRN.getContact(mContactId);
        if (mContact != null) {
            mSelectedPath = mContact.getPhoto();
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
                    .load(contact.getPhoto())
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
        contact.setPhoto(mSelectedPath);
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

    @OnClick({R.id.etBorn, R.id.ivCam})
    public void onClick(View view) {

        try {
            switch (view.getId()) {
                case R.id.etBorn:
                    if (etBorn.getText() == null || etBorn.getText().toString().length() == 0) {
                        new CalendarHelper(this, this, 0).show();
                        return;
                    }

                    Calendar calendar = DateFormatter.dateToCalendar(DateFormatter.DATE_DEFAULT, etBorn.getText().toString());
                    new CalendarHelper(this, this, 0).show(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    break;

                case R.id.ivCam:
                    if (requestPermissions())
                        return;

                    startChoosePictureActivity();
                    break;
            }
        }  catch (Exception error) {
            Log.e("Error", "Error at onClick in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    @Override
    protected void onReceiveData(Class fromClass, int id, boolean result, Object... objects) throws Exception {
        if (fromClass == CalendarHelper.class) {
            if (id == Param.ComponentCompact.CALENDAR_DATE_SETTED) {
                etBorn.setText(DateFormatter.format(DateFormatter.DATE_DEFAULT, (Calendar) objects[1]));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_CHOOSE_PICUTURE && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();

                //changes the status color
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mSelectedPath = RealPathUtil.getRealPathFromURI_API19(this, selectedImageUri);
                else
                    mSelectedPath = RealPathUtil.getRealPathFromURI_API11to18(this, selectedImageUri);

                addContactInfoOnFields(getEditedContact());
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onActivityResult in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_edit_contact, menu);
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
                case R.id.mnuDone:
                    if (!validateFields())
                        break;

                    boolean result = mContactRN.saveContactOnDatabase(mContact, getEditedContact());

                    //Show the result to the user
                    String msg = result ? getString(R.string.edit_save_success) : getString(R.string.edit_save_failed);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                    //if its edition, returns the new contact
                    if (mContact != null) {
                        Intent intent = new Intent();
                        intent.putExtra(Param.Intent.CONTACT_ID, mContact.getId());
                        intent.putExtra(Param.Intent.IS_WHITE, UtilView.checkWhiteBackGroundInFrontOfIcons(ivPhoto));
                        setResult(result ? RESULT_OK : RESULT_CANCELED, intent);
                        finish();
                        break;
                    }

                    setResult(result ? RESULT_OK : RESULT_CANCELED);
                    finish();
                    break;
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onOptionsItemSelected in " + getClass().getName() + ". " + error.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Request all necessaries permissions
     *
     * @return True if permission is needed, false otherwise
     */
    private boolean requestPermissions() throws Exception {
        // Check the necessaries permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show a explanation about the permission?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.permission_title))
                        .setMessage(getString(R.string.permission_desc))
                        .setPositiveButton(getString(R.string.permission_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // It doesn't need explanations
                                ActivityCompat.requestPermissions(EditContactActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        PERMISSION_SDCARD);
                            }
                        })
                        .show();

            } else {
                // It doesn't need explanations
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_SDCARD);
            }

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            //Check what permission were received
            switch (requestCode) {
                //If were write on sdcard permission
                case PERMISSION_SDCARD:
                    //If it doesn't gave permission
                    if (grantResults.length > 0
                            && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, getString(R.string.permission_desc), Toast.LENGTH_LONG).show();
                        return;
                    }
            }

            startChoosePictureActivity();
        } catch (Exception error) {
            Log.e("Error", "Error while receiving permission at " + getClass().getName() + ". " + error.getMessage());
        }
    }

    /**
     * Starts an activity for picture selection. Result will be receive on OnActivityResult method.
     */
    public void startChoosePictureActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.edit_select_picutre)), REQUEST_CHOOSE_PICUTURE);
    }
}
