package net.artgamestudio.rgatest.data.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * POJO for get contact service
 */
@Entity(nameInDb = "contact")
public class Contact {

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
