package net.artgamestudio.rgatest.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import net.artgamestudio.rgatest.base.interfaces.IComponentContact;

import java.util.Calendar;

public class CalendarHelper implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mDialog;
    private Context mContext;
    private IComponentContact mComponentContact;
    private int mId;

    public CalendarHelper(Context context, IComponentContact componentContact, int id) {
        mContext = context;
        mComponentContact = componentContact;
        mId = id;

        mComponentContact.contactComponent(getClass(), Param.ComponentCompact.CONTACTS_IMPORTED, true);
    }

    /**
     * Show a calendar to the user based on the current date.
     * When user selects a date, Param CALENDAR_DATE_SETTED will be called on component contact.
     */
    public void show() {
        Calendar calendar = Calendar.getInstance();
        mDialog = new DatePickerDialog(mContext, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDialog.show();
    }

    /**
     * Show a calendar to the user based on params date.
     * When user selects a date, Param CALENDAR_DATE_SETTED will be called on component contact.
     */
    public void show(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mDialog = new DatePickerDialog(mContext, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Object[] objects = new Object[2];
        objects[0] = mId;
        objects[1] = calendar;
        mComponentContact.contactComponent(getClass(), Param.ComponentCompact.CALENDAR_DATE_SETTED, true, objects);
    }
}
