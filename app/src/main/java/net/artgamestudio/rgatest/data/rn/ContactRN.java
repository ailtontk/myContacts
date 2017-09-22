package net.artgamestudio.rgatest.data.rn;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import net.artgamestudio.rgatest.base.BaseRN;
import net.artgamestudio.rgatest.base.interfaces.IComponentContact;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.net.RestApi;
import net.artgamestudio.rgatest.util.App;
import net.artgamestudio.rgatest.util.Param;

import java.util.List;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * All business operations for Contact class will be placed here.
 */
public class ContactRN extends BaseRN {

    public ContactRN(Context context, IComponentContact contact) {
        super(context, contact);
    }

    /**
     * Get the contact list from server<br>
     * When its done, it will response on CONTACTS_IMPORTED
     */
    public void getAndImportContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //If contacts are already imported just returns
                    if (areContactsImported())
                        return;

                    //Otherwise get the contact list
                    List<Contact> contacts = RestApi.callApi(RestApi.getServicesInstance().getContacts());

                    //save on db
                    App.getDaoSession().getContactDao().insertInTx(contacts);

                    //if its here, it was successfully saved.
                    setContactsImported(true);
                } catch (Exception error) {
                    Log.e("error", "Error at getAndImportContacts in " + ContactRN.this.getClass() + ". Error: " + error.getMessage());
                } finally {
                    mContact.contactComponent(ContactRN.class, Param.Contact.CONTACTS_IMPORTED, true);
                }
            }
        }).start();
    }

    /**
     * Get all contacts from database
     * @return All contacts saved
     */
    public List<Contact> getContacts() {
        return App.getDaoSession().getContactDao().loadAll();
    }

    /**
     * Set on shared preferences if the contacts list were imported from server or not
     * @param imported True if yes, false otherwise
     */
    private void setContactsImported(boolean imported) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(Param.Prefs.IMPORTED, imported);
        editor.apply();
    }

    /**
     * Check on shared preferences if contacts were imported already
     * @return True if yes, false otherwise
     */
    private boolean areContactsImported() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(Param.Prefs.IMPORTED, false);
    }
}
