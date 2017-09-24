package net.artgamestudio.rgatest.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * POJO for get contact service
 */
@Entity(nameInDb = "contact")
public class Contact implements Parcelable {

    private String name;
    private String email;
    private String born;
    private String bio;
    private String photo;

    @Generated(hash = 933263070)
    public Contact(String name, String email, String born, String bio,
            String photo) {
        this.name = name;
        this.email = email;
        this.born = born;
        this.bio = bio;
        this.photo = photo;
    }
    @Generated(hash = 672515148)
    public Contact() {
    }

    protected Contact(Parcel in) {
        name = in.readString();
        email = in.readString();
        born = in.readString();
        bio = in.readString();
        photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(born);
        dest.writeString(bio);
        dest.writeString(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBorn() {
        return this.born;
    }
    public void setBorn(String born) {
        this.born = born;
    }
    public String getBio() {
        return this.bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
