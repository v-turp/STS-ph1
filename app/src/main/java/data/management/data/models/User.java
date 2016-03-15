package data.management.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Who Dat on 2/3/2016.
 * this was created using Android Parcelable generator plugin(see Code/generate)
 */
public class User implements Parcelable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String city;
    private String state;
    private String country;
    private String musicGenre;
    private String filmGenre;
    private String title;
    private String bio;

    public User(){}

    public User(String firstName, String lastName, String username, String password, String email, String city,
                String state, String country, String musicGenre, String filmGenre, String title, String bio){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.city = city;
        this.state = state;
        this.country = country;
        this.musicGenre = musicGenre;
        this.filmGenre = filmGenre;
        this.title = title;
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {  return email;}

    public void setEmail(String email) { this.email = email; }

    public String getCity() { return city;}

    public void setCity(String city) { this.city = city; }

    public String getState() { return state;}

    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getMusicGenre() {return musicGenre;}

    public void setMusicGenre(String musicGenre) { this.musicGenre = musicGenre; }

    public String getFilmGenre() { return filmGenre; }

    public void setFilmGenre(String filmGenre) { this.filmGenre = filmGenre;}

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getBio() {return bio;}

    public void setBio(String bio) { this.bio = bio; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.musicGenre);
        dest.writeString(this.filmGenre);
        dest.writeString(this.title);
        dest.writeString(this.bio);
    }

    protected User(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.musicGenre = in.readString();
        this.filmGenre = in.readString();
        this.title = in.readString();
        this.bio = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
