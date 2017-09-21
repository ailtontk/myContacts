package net.artgamestudio.rgatest.data.rn;

import android.content.Context;
import android.util.Log;

import net.artgamestudio.rgatest.base.BaseRN;
import net.artgamestudio.rgatest.base.interfaces.IComponentContact;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.net.RestApi;
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
                    List<Contact> contacts = RestApi.callApi(RestApi.getServicesInstance().getContacts());
                } catch (Exception error) {
                    Log.e("error", "Error at getAndImportContacts in " + ContactRN.this.getClass() + ". Error: " + error.getMessage());
                } finally {
                    mContact.contactComponent(ContactRN.class, Param.Contact.CONTACTS_IMPORTED, true);
                }
            }
        }).start();
    }
}
