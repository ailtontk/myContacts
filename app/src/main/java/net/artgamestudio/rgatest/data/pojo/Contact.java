package net.artgamestudio.rgatest.data.pojo;

import java.io.Serializable;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * POJO for get contact service
 */
public class Contact implements Serializable {

    private int id;
    private String name;
    private String email;
    private String born;
    private String bio;
    private String photo;

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
