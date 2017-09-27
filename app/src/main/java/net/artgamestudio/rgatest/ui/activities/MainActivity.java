package net.artgamestudio.rgatest.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.ui.adapters.ContactsAdapter;
import net.artgamestudio.rgatest.util.Param;
import net.artgamestudio.rgatest.util.Util;
import net.artgamestudio.rgatest.util.UtilView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {

    /***** VIEWS *****/
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvMainLogo) TextView tvMainLogo;
    @BindView(R.id.searchView) MaterialSearchView searchView;
    @BindView(R.id.pbLoading) ProgressBar pbLoading;
    @BindView(R.id.cvListContainer) View cvListContainer;
    @BindView(R.id.tvNoRegister) TextView tvNoRegister;
    @BindView(R.id.rvContacts) RecyclerView rvContacts;
    @BindView(R.id.fbAdd) FloatingActionButton fbAdd;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private ContactsAdapter mAdapter;
    private boolean mSearchPressed;
    private int mSelectedPosition;
    private View mSelectedView;

    /***** CONSTANTS *****/
    private final int REQUEST_CONTACT_INFO = 1;
    private final int REQUEST_CONTACT_INSERTION = 2;
    private final int UPDATE_ALL_LIST = -1;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_main;
    }

    @Override
    public void setupToolbar() throws Exception {
        //Sets toolbar
        toolbar.setNavigationIcon(R.mipmap.ic_transparent);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupData() throws Exception {
        mContactRN = new ContactRN(this, this);

        //Changes the logo to the default font (bold) and Animates floating button
        Util.changeFont(this, tvMainLogo);
        UtilView.animateView(this, fbAdd, 1000);

        //fill the list
        fillContactList();
    }

    /**
     * Show loading instead contact list
     */
    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        cvListContainer.setVisibility(View.GONE);
        tvNoRegister.setVisibility(View.GONE);
    }

    /**
     * Show card list instead loading
     * @param hasRegisters If the contact list has registers
     */
    private void showContactList(boolean hasRegisters) {
        //Show cardlist again
        pbLoading.setVisibility(View.GONE);
        cvListContainer.setVisibility(View.VISIBLE);

        //if there is no results, show the string
        tvNoRegister.setVisibility(hasRegisters ? View.GONE : View.VISIBLE);
    }

    /**
     * Fills the contact list on screen
     */
    private void fillContactList(String...name) {
        try {
            //Get all contacts
            showLoading();
            List<Contact> contacts = mContactRN.getContacts(name);

            //create the adapter
            mAdapter = new ContactsAdapter(this, this, contacts);
            rvContacts.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
            rvContacts.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //Show the contact list again
            showContactList(contacts != null && contacts.size() > 0);
        } catch (Exception error) {
            Log.e("Error", "Error at fillContactList in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    /**
     * Get the new contact list and updates UI
     * @param position The item  position to update or -1 (UPDATE_ALL_LIST) to update all
     */
    private void updateList(int position) throws Exception {
        if (mAdapter == null)
            return;

        mAdapter.setContacts(mContactRN.getContacts());
        if (position == UPDATE_ALL_LIST) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter.notifyItemChanged(position);
    }

    /**
     * Get the new contact list and updates UI
     */
    private void updateListNewList() throws Exception {
        if (mAdapter == null)
            return;

        int currentSize = mAdapter.getItemCount();
        mAdapter.setContacts(mContactRN.getContacts());
        mAdapter.notifyItemRangeChanged(currentSize, mAdapter.getItemCount());
    }

    /**
     * Change the toolbar icon for remove or search contacts
     * @param remove True if its to show remove icon, false otherwise
     */
    private void changeToolbarMenu(boolean remove) {
        toolbar.getMenu().findItem(R.id.mnuRemove).setVisible(remove);
        toolbar.getMenu().findItem(R.id.mnuSearch).setVisible(!remove);

        toolbar.setNavigationIcon(remove ? R.drawable.ic_back_arrow : R.mipmap.ic_transparent);
    }

    /**
     * Used to remove selection from view and toolbar
     * @throws Exception
     */
    private void unSelectView() throws Exception {
        //Otherwise, go back to the normal
        changeToolbarMenu(false);
        UtilView.setSelected(this, mSelectedView, false);
        mSelectedView = null;
        mSelectedPosition = -1;
    }

    @OnClick(R.id.fbAdd)
    public void onClick(View view) {
        startEditContactActivity();
    }

    @Override
    protected void onReceiveData(Class fromClass, int id, boolean result, Object... objects) throws Exception {
        //If comes from adapter
        if (fromClass == ContactsAdapter.class) {
            //If user clicked on container
            if (id == Param.ComponentCompact.CONTACTS_LIST_ITEM_CLICKED) {
                //The contact position its on object vararg.
                startContactInfoActivity((int) objects[0], (View) objects[1]);
                return;
            }

            //If user clicked on container
            if (id == Param.ComponentCompact.CONTACTS_LIST_ITEM_LONG_CLICKED) {
                changeToolbarMenu(true);
                mSelectedPosition = (int) objects[0];
                mSelectedView = (View) objects[2];
                UtilView.setSelected(this, mSelectedView, true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflates the options menu and set search view component
        getMenuInflater().inflate(R.menu.mnu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.mnuSearch);
        searchView.setMenuItem(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            // Checks the touched menu
            switch (item.getItemId()) {
                // If touched at back button
                case android.R.id.home:
                    //If it doesn't has a view, so we don't have to do anything
                    if (mSelectedView == null)
                        break;

                    //Otherwise, go back to the normal
                    unSelectView();
                    break;

                case R.id.mnuRemove:
                    UtilView.createAlertDialog(
                            this,
                            //Positive button
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        mContactRN.removeContact(mAdapter.getContact(mSelectedPosition));
                                        updateList(UPDATE_ALL_LIST);
                                        unSelectView();
                                    } catch (Exception error) {
                                        Log.e("Error", "Error at onClick in " + getClass().getName() + ". " + error.getMessage());
                                    }
                                }
                            },
                            //Negative
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        unSelectView();
                                    } catch (Exception error) {
                                        Log.e("Error", "Error at onClick in " + getClass().getName() + ". " + error.getMessage());
                                    }
                                }
                            },
                            getString(R.string.attention),
                            getString(R.string.remove_desc).replace("{name}", mAdapter.getContact(mSelectedPosition).getName()),
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
    public boolean onQueryTextSubmit(String query) {
        //If hasn't text, do nothing
        if (query.length() == 0)
            return false;

        mSearchPressed = true;
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //If hasn't text, do nothing
        if (newText.length() == 0 || mSearchPressed) {
            if (newText.length() > 0)
                mSearchPressed = false;
            return false;
        }

        fillContactList(newText);
        return false;
    }

    @Override
    public void onSearchViewShown() {
        fillContactList();
    }

    @Override
    public void onSearchViewClosed() {
        if (!mSearchPressed || mAdapter.getItemCount() == 0)
            fillContactList();

        mSearchPressed = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            //Updates the changed position
            if (requestCode == REQUEST_CONTACT_INFO) {
                updateList(data.getIntExtra(Param.Intent.CONTACT_POSITION, UPDATE_ALL_LIST));
                return;
            }

            //Updates just the end
            if (requestCode == REQUEST_CONTACT_INSERTION) {
                updateListNewList();
            }
        } catch (Exception error) {
            Log.e("Error", "Error at onActivityResult in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    /**
     * Starts the contact info activity
     */
    private void startContactInfoActivity(int position, View view) {
        Intent intent = new Intent(this, ContactInfoActivity.class);
        intent.putExtra(Param.Intent.CONTACT_ID, mAdapter.getContact(position).getId());
        intent.putExtra(Param.Intent.CONTACT_POSITION, position);

        //Adds transition effect if its lollipop or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getString(R.string.transition_photo));
            startActivityForResult(intent, REQUEST_CONTACT_INFO, options.toBundle());
            return;
        }

        startActivityForResult(intent, REQUEST_CONTACT_INFO);
    }

    /**
     * Starts the edit contact activity
     */
    private void startEditContactActivity() {
        Intent intent = new Intent(this, EditContactActivity.class);
        startActivityForResult(intent, REQUEST_CONTACT_INSERTION);
    }
}
