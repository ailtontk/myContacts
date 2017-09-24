package net.artgamestudio.rgatest.data.rn;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import net.artgamestudio.rgatest.base.BaseRN;
import net.artgamestudio.rgatest.base.interfaces.IComponentContact;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.pojo.ContactDao;
import net.artgamestudio.rgatest.net.RestApi;
import net.artgamestudio.rgatest.util.App;
import net.artgamestudio.rgatest.util.Param;

import java.util.List;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * All business operations for ComponentCompact class will be placed here.
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
                boolean result = false;
                try {
                    //If contacts are already imported just returns
                    if (areContactsImported()) {
                        result = true;
                        return;
                    }

                    //Otherwise get the contact list
                    List<Contact> contacts = RestApi.callApi(RestApi.getServicesInstance().getContacts());

                    //if has failed to get
                    if (contacts == null || contacts.size() == 0)
                        return;

                    //save on db
                    App.getDaoSession().getContactDao().insertInTx(contacts);

                    //if its here, it was successfully saved.
                    setContactsImported(true);
                    result = true;
                } catch (Exception error) {
                    Log.e("error", "Error at getAndImportContacts in " + ContactRN.this.getClass() + ". Error: " + error.getMessage());
                } finally {
                    mContact.contactComponent(ContactRN.class, Param.ComponentCompact.CONTACTS_IMPORTED, result);
                }
            }
        }).start();
    }

    /**
     * Get all contacts from database
     * @return All contacts saved
     */
    public List<Contact> getContacts(String...name) {
        if (name == null || name.length == 0)
            return App.getDaoSession().getContactDao().loadAll();

        //If has a name, search by name
        return App.getDaoSession().getContactDao().queryBuilder()
                .where(ContactDao.Properties.Name.like("%"+name[0]+"%"))
                .orderAsc(ContactDao.Properties.Name)
                .list();
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


    /**
     * Saves or update contact in database
     * @return True if was successfully saved, false otherwise
     */
    public boolean saveContactOnDatabase(Contact currentContact, Contact newContact) {

        try {
            if (currentContact != null) {
                currentContact = getContact(currentContact.getId());
            }

            //If its null, even after try to get from db, adds
            if (currentContact == null) {
                addContact(newContact);
                return true;
            }

            //otherwise, updates the current contact
            newContact.setPhoto(currentContact.getPhoto());
            updateContact(newContact);
        } catch (Exception error) {
            Log.e("error", "Error at getAndImportContacts in " + ContactRN.this.getClass() + ". Error: " + error.getMessage());
            return false;
        }

        return true;
    }


    /**
     * Adds a new contact on db
     * @param contact A contact to add
     */
    public void addContact(Contact contact) throws Exception {

    }

    /**
     * Updates a contact in db.
     * @param contact A contact with id filled
     */
    public void updateContact(Contact contact) throws Exception {

    }

    /**
     * Updates a contact in db.
     * @param id A contact id
     */
    public void updateContact(int id) throws Exception {

    }

    /**
     * Removes a contact from db
     * @param id The contact id
     */
    public void removecontact(int id) throws Exception {

    }

    /**
     * Get a contact from database based on a contact id
     * @param id The contact id
     * @return A filled contact object
     */
    public Contact getContact(Long id) throws Exception {
        return null;
    }
}
