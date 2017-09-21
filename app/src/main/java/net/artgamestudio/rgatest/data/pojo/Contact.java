package net.artgamestudio.rgatest.data.pojo;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * POJO for get contact service
 */
@Entity
public class Contact {

    private String name;
    private String email;
    private String born;
    private String bio;
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
