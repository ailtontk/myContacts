package net.artgamestudio.rgatest.ui.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
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
import net.artgamestudio.rgatest.util.UtilView;

import butterknife.BindView;

/**
 * Created by Ailton on 22/09/2017 for artGS.<br>
 *
 * Activity for see contacts details
 */
public class ContactInfoActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    /***** VIEWS *****/
    @BindView(R.id.appBar) AppBarLayout appBar;
    @BindView(R.id.colToolbar) CollapsingToolbarLayout colToolbar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ivPhoto) ImageView ivPhoto;
    @BindView(R.id.tvEmail) TextView tvEmail;
    @BindView(R.id.tvBorn) TextView tvBorn;
    @BindView(R.id.tvBio) TextView tvBio;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private Contact mContact;
    private int mContactId;
    private int mContactPosition;
    private boolean mHasChanged;
    private boolean mIsBackgroundWhite;
    private boolean mCanChangeColor;

    /***** CONSTANTS *****/
    private final int REQUEST_EDIT_CONTACT = 1;
    private final int UPDATE_ALL_LIST = -1;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_contact_info;
    }

    @Override
    public void getParam() throws Exception {
        mContactId = getIntent().getIntExtra(Param.Intent.CONTACT_ID, -1);
        mContactPosition = getIntent().getIntExtra(Param.Intent.CONTACT_POSITION, -1);
        mIsBackgroundWhite = getIntent().getBooleanExtra(Param.Intent.IS_WHITE, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void setSharedElements() throws Exception {
        ivPhoto.setTransitionName(getString(R.string.transition_photo));
    }

    @Override
    public void setupToolbar() throws Exception {
        setSupportActionBar(toolbar);
        appBar.addOnOffsetChangedListener(this);

        //changes the status color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGrey9));
        }

        //If image has white background, change the icons to black while expanded
        if (mIsBackgroundWhite) {
            colToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorGrey9));
        }
    }

    @Override
    public void setupData() throws Exception {
        mContactRN = new ContactRN(this, this);
        mContact = mContactRN.getContact(mContactId);

        addContactInfoOnFields(mContact);
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

        colToolbar.setTitle(contact.getName());
        tvEmail.setText(contact.getEmail());
        tvBorn.setText(contact.getBorn());
        tvBio.setText(contact.getBio());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_contact_info, menu);

        if (mIsBackgroundWhite) {
            menu.findItem(R.id.mnuRemove).setIcon(R.drawable.ic_delete_dark);
            menu.findItem(R.id.mnuEdit).setIcon(R.drawable.ic_edit_dark);
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
                    finishPassingPosition(mContactPosition);
                    break;

                // If touched at edit button
                case R.id.mnuEdit:
                    startEditContactActivity();
                    break;

                // If touched at remove button
                case R.id.mnuRemove:
                    UtilView.createAlertDialog(
                            this,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        mHasChanged = true;
                                        mContactRN.removeContact(mContact);
                                        finishPassingPosition(UPDATE_ALL_LIST);
                                    } catch (Exception error) {
                                        Log.e("Error", "Error at onClick in " + getClass().getName() + ". " + error.getMessage());
                                    }
                                }
                            },
                            null,
                            getString(R.string.attention),
                            getString(R.string.remove_desc).replace("{name}", mContact.getName()),
                            getString(R.string.yes),
                            getString(R.string.no));
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
            if (requestCode == REQUEST_EDIT_CONTACT && resultCode == RESULT_OK) {
                mHasChanged = true;
                mContact = mContactRN.getContact(data.getIntExtra(Param.Intent.CONTACT_ID, -1));
                mIsBackgroundWhite = data.getBooleanExtra(Param.Intent.IS_WHITE, false);

                //If image has white background, change the icons to black while expanded
                if (mIsBackgroundWhite) {
                    colToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorGrey9));
                }
                addContactInfoOnFields(mContact);
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onActivityResult in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset < -10 && verticalOffset > -450)
            return;

        MenuItem mnuRemove = toolbar.getMenu().findItem(R.id.mnuRemove);
        MenuItem mnuEdit = toolbar.getMenu().findItem(R.id.mnuEdit);

        if (mnuEdit == null || mnuRemove == null)
            return;

        //Its Expanded
        if (verticalOffset > -450 && !mCanChangeColor) {
            mCanChangeColor = true;

            //changes the status color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGrey9));
            }

            if (!mIsBackgroundWhite)
                return;

            toolbar.setNavigationIcon(R.drawable.ic_back_arrow_dark);
            mnuRemove.setIcon(R.drawable.ic_delete_dark);
            mnuEdit.setIcon(R.drawable.ic_edit_dark);
        }
        //Its collapsed
        else if (verticalOffset < -10 && mCanChangeColor){
            mCanChangeColor = false;

            //changes the status color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }

            if (!mIsBackgroundWhite)
                return;

            //Is expanded
            toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
            mnuRemove.setIcon(R.drawable.ic_delete);
            mnuEdit.setIcon(R.drawable.ic_edit);
        }
    }

    private void finishPassingPosition(int position) {
        Intent intent = new Intent();
        intent.putExtra(Param.Intent.CONTACT_POSITION, position);

        setResult(mHasChanged ? RESULT_OK : RESULT_CANCELED, intent);
        supportFinishAfterTransition();
    }

    private void startEditContactActivity() {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra(Param.Intent.CONTACT_ID, mContactId);

        //Adds transition effect if its lollipop or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivPhoto, getString(R.string.transition_photo));
            startActivityForResult(intent, REQUEST_EDIT_CONTACT, options.toBundle());
            return;
        }

        startActivityForResult(intent, REQUEST_EDIT_CONTACT);
    }
}
