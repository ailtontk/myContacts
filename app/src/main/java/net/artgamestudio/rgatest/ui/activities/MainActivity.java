package net.artgamestudio.rgatest.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.BaseActivity;
import net.artgamestudio.rgatest.data.rn.ContactRN;
import net.artgamestudio.rgatest.util.Util;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MaterialSearchView.OnQueryTextListener {

    /***** VIEWS *****/
    @BindView(R.id.tvMainLogo) TextView tvMainLogo;
    @BindView(R.id.searchView) MaterialSearchView searchView;
    @BindView(R.id.pbLoading) ProgressBar pbLoading;
    @BindView(R.id.cvListContainer) View cvListContainer;
    @BindView(R.id.tvNoRegister) TextView tvNoRegister;
    @BindView(R.id.rvContacts) RecyclerView rvContacts;

    /***** VARIABLES *****/
    private ContactRN mContactRN;
    private boolean mSearchPressed;

    @Override
    public int setView() throws Exception {
        return R.layout.activity_main;
    }

    @Override
    public void setupData() throws Exception {
        mContactRN = new ContactRN(this, this);

        //Changes the logo to the default font (bold)
        Util.changeFont(this, tvMainLogo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            //inflates the options menu and set search view component
            getMenuInflater().inflate(R.menu.mnu_main, menu);

            MenuItem menuItem = menu.findItem(R.id.mnuSearch);
            searchView.setMenuItem(menuItem);
            searchView.setOnQueryTextListener(this);
            searchView.showSearch();
        } catch (Exception error) {
            Log.e("Error", "Error at onCreateOptionsMenu in " + getClass().getName() + ". " + error.getMessage());
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            //If hasn't text, do nothing
            if (query.length() == 0)
                return false;

            mSearchPressed = true;
        } catch (Exception error) {
            Log.e("Error", "Error at onQueryTextSubmit in " + getClass().getName() + ". " + error.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            //If hasn't text, do nothing
            if (newText.length() == 0 || mSearchPressed) {
                mSearchPressed = false;
                return false;
            }

            pbLoading.setVisibility(View.VISIBLE);
            cvListContainer.setVisibility(View.GONE);
            mContactRN.getContacts(newText);
        } catch (Exception error) {
            Log.e("Error", "Error at onQueryTextSubmit in " + getClass().getName() + ". " + error.getMessage());
        }
        return false;
    }
}
