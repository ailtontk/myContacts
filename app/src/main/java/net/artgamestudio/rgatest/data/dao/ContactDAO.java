package net.artgamestudio.rgatest.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.artgamestudio.rgatest.data.dao.base.DAOBase;
import net.artgamestudio.rgatest.data.dao.base.DBConnection;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.util.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ailton on 25/09/2017 for artGS.<br>
 *
 * DAOClass for Contact pojo
 */
public class ContactDAO extends DAOBase<Contact> {

    public ContactDAO(Context context) {
        super(context);
    }

    @Override
    public List<Contact> getAll(String... name) throws Exception {
        mDb = DBConnection.getConnection(mHelper);
        Cursor cursor = null;
        List<Contact> contacts = new ArrayList<>();

        try {
            String where = null;
            //If has name, put on where
            if (name != null && name.length > 0) {
                where = "name like ?";
                name[0] = "%" + name[0] + "%";
            }

            //Search
            cursor = mDb.query(Param.Tables.CONTACTS, null, where, name, null, null, null);
            Contact contact;
            while (cursor.moveToNext()) {
                contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                contact.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                contact.setName(cursor.getString(cursor.getColumnIndex("name")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contact.setBorn(cursor.getString(cursor.getColumnIndex("born")));
                contact.setBio(cursor.getString(cursor.getColumnIndex("bio")));

                contacts.add(contact);
            }
        } finally {
            DBConnection.closeConnection();

            if (cursor != null)
                cursor.close();
        }

        return contacts;
    }

    @Override
    public Contact get(int id) throws Exception {
        mDb = DBConnection.getConnection(mHelper);
        Cursor cursor = null;
        Contact contact = null;

        try {
            //Search
            cursor = mDb.query(Param.Tables.CONTACTS, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToNext()) {
                contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                contact.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                contact.setName(cursor.getString(cursor.getColumnIndex("name")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contact.setBorn(cursor.getString(cursor.getColumnIndex("born")));
                contact.setBio(cursor.getString(cursor.getColumnIndex("bio")));
            }
        } finally {
            DBConnection.closeConnection();

            if (cursor != null)
                cursor.close();
        }

        return contact;
    }

    @Override
    public void update(Contact obj) throws Exception {
        mDb = DBConnection.getConnection(mHelper);

        try {
            ContentValues contentValues = getContentValues(obj);
            mDb.update(Param.Tables.CONTACTS, contentValues, "id=?", new String[]{String.valueOf(obj.getId())});
        } finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public void insert(Contact obj) throws Exception {
        mDb = DBConnection.getConnection(mHelper);

        try {
            ContentValues contentValues = getContentValues(obj);
            mDb.insert(Param.Tables.CONTACTS, null, contentValues);
        } finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public void remove(int id) throws Exception {
        mDb = DBConnection.getConnection(mHelper);

        try {
            mDb.delete(Param.Tables.CONTACTS, "id=?", new String[]{String.valueOf(id)});
        } finally {
            DBConnection.closeConnection();
        }
    }

    private ContentValues getContentValues(Contact obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("email", obj.getEmail());
        contentValues.put("photo", obj.getPhoto());
        contentValues.put("born", obj.getBorn());
        contentValues.put("bio", obj.getBio());
        return contentValues;
    }
}
