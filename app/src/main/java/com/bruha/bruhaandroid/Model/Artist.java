// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruhaandroid.Model;

/**
 * Created by Work on 2015-06-11.
 */
public class Artist {
    //Artist main Database data.
    private String artistId;
    private String artistName;
    private String artistDescription;
    //Artist Event Data.
    private int artistEventId;
    //Artist Media data.
    private String artistPicture;
    private int artistIcon;
    //Category of the artist.
    private String artistPrimaryCategory;
    //Artist Contact data.
    private String contactName;
    private String contactPhoneNumber;
    private String contactEmail;
    private String contactWebsite;
    private String contactAddress;

    //The getters and setters for Aritists variables defined above.

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistDescription() {
        return artistDescription;
    }

    public void setArtistDescription(String artistDescription) {
        this.artistDescription = artistDescription;
    }

    public int getArtistEventId() {
        return artistEventId;
    }

    public void setArtistEventId(int artistEventId) {
        this.artistEventId = artistEventId;
    }

    public String getArtistPicture() {
        return artistPicture;
    }

    public void setArtistPicture(String artistPicture) {
        this.artistPicture = artistPicture;
    }

    public int getArtistIcon() {
        return artistIcon;
    }

    public void setArtistIcon(int artistIcon) {
        this.artistIcon = artistIcon;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactWebsite() {
        return contactWebsite;
    }

    public void setContactWebsite(String contactWebsite) {
        this.contactWebsite = contactWebsite;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getArtistPrimaryCategory() {
        return artistPrimaryCategory;
    }

    public void setArtistPrimaryCategory(String artistPrimaryCategory) {
        this.artistPrimaryCategory = artistPrimaryCategory;
    }
}