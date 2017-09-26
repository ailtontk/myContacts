package net.artgamestudio.rgatest.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.base.interfaces.IComponentContact;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.util.Param;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ailton on 22/09/2017 for artGS.<br>
 *
 * Custom adapter for Contacts main list
 */
public class ContactsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private IComponentContact mContact;
    private List<Contact> mContacts;

    public ContactsAdapter(Context context, IComponentContact contact, List<Contact> contacts) {
        mContext = context;
        mContact = contact;
        mContacts = contacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactHolder holder = null;

        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
            holder = new ContactHolder(view);
        } catch (Exception error) {
            Log.e("error", "Error at onCreateViewHolder in " + getClass().getName() + ". Error: " + error.getMessage());
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        try {
            //Gets the current holder and contact
            ContactHolder holder = (ContactHolder) viewHolder;
            Contact contact = mContacts.get(position);

            //Put name on screen
            holder.tvName.setText(contact.getName());

            //put image on screen
            Glide.with(mContext)
                    .load(contact.getPhoto())
                    .into(holder.ivPhoto);

            //Saves the current position
            holder.container.setTag(position);
        } catch (Exception error) {
            Log.e("error", "Error at onBindViewHolder in " + getClass().getName() + ". Error: " + error.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        mContacts = contacts;
    }

    public List<Contact> getContacts() {
        return mContacts;
    }

    public Contact getContact(int index) {
        if (index <0 || index >= mContacts.size())
            return null;
        return mContacts.get(index);
    }

    /**
     * Inner class for view holder pattern
     */
    class ContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container) View container;
        @BindView(R.id.ivPhoto) ImageView ivPhoto;
        @BindView(R.id.tvName) TextView tvName;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.container)
        public void onClick(View view) {
            //Informs who created the adapter that container were pressed
            mContact.contactComponent(ContactsAdapter.class, Param.ComponentCompact.CONTACTS_LIST_ITEM_CLICKED, true, view.getTag());
        }
    }
}
