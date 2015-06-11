package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-06-11.
 */
public class Artists {

    //Artists main Database data.
    private int ArtistId;
    private String ArtistName;
    private String ArtistDescription;

    //Artists Event Data.
    private int ArtistEventId;

    //Artists Media data.
    private int ArtistPicture;
    private int ArtistIcon;

    //Artists Contact data.
    private String ContactName;
    private String ContactPhoneNumber;
    private String ContactEmail;
    private String ContactWebsite;
    private String ContactAddress;


    //The getters and setters for Aritists variables defined above.

    public int getArtistId() {
        return ArtistId;
    }

    public void setArtistId(int artistId) {
        ArtistId = artistId;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getArtistDescription() {
        return ArtistDescription;
    }

    public void setArtistDescription(String artistDescription) {
        ArtistDescription = artistDescription;
    }

    public int getArtistEventId() {
        return ArtistEventId;
    }

    public void setArtistEventId(int artistEventId) {
        ArtistEventId = artistEventId;
    }

    public int getArtistPicture() {
        return ArtistPicture;
    }

    public void setArtistPicture(int artistPicture) {
        ArtistPicture = artistPicture;
    }

    public int getArtistIcon() {
        return ArtistIcon;
    }

    public void setArtistIcon(int artistIcon) {
        ArtistIcon = artistIcon;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhoneNumber() {
        return ContactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        ContactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getContactWebsite() {
        return ContactWebsite;
    }

    public void setContactWebsite(String contactWebsite) {
        ContactWebsite = contactWebsite;
    }

    public String getContactAddress() {
        return ContactAddress;
    }

    public void setContactAddress(String contactAddress) {
        ContactAddress = contactAddress;
    }
}
