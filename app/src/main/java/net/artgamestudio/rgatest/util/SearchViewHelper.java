package net.artgamestudio.rgatest.util;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.artgamestudio.rgatest.base.interfaces.IComponentContact;

public class SearchViewHelper implements MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener {

    /***** VARIABLES *****/
    private MaterialSearchView mSearchView;
    private IComponentContact mContactComponent;
    private String mCurrentText;
    private boolean mSearchPressed;

    public SearchViewHelper(MaterialSearchView searchView, IComponentContact contactComponent) {
        mSearchView = searchView;
        mContactComponent = contactComponent;

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSearchViewListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mCurrentText = query;

        //If hasn't text, do nothing
        if (query.length() == 0)
            return false;

        mSearchPressed = true;
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mCurrentText = newText;

        //If hasn't text, do nothing
        if (newText.length() == 0 || mSearchPressed) {
            if (newText.length() > 0)
                mSearchPressed = false;
            return false;
        }

        report(Param.ComponentCompact.SEARCH_TEXT, newText);
        return false;
    }

    @Override
    public void onSearchViewShown() {
        report(Param.ComponentCompact.SEARCH_SHOWN, null);
    }

    @Override
    public void onSearchViewClosed() {
        report(Param.ComponentCompact.SEARCH_CLOSED, mCurrentText);
        mSearchPressed = false;
    }

    /**
     * Reports to its creator that a new text for search
     */
    private void report(int id, Object newText) {
        mContactComponent.contactComponent(SearchViewHelper.class, id, false, newText);
    }
}
