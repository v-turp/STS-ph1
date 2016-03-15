package data.management.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Who Dat on 1/13/2016.
 * this class is a Parcelable POJO for a contact
 */
public class Contact implements Parcelable{

    private static final String TAG = Contact.class.getSimpleName();

    private String firstName;
    private String lastName;
    private String email;

    public Contact(){}

    public Contact(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private Contact(Parcel in){
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>(){

        public Contact createFromParcel(Parcel in){
            return new Contact(in);
        }

        public Contact[] newArray(int size){
            return new Contact[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
