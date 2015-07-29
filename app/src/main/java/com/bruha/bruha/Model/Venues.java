package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-06-09.
 */
public class Venues {

    //Venue main Database data.
    private String venueId;
    private String venueName;
    private String venueSt;
    private String venueLocation;
    private String venueDescription;

    //Venue Media data.
    private String venuePicture;
    private int venueCategoryIcon;

    //Venue Contact data.
    private String contactName;
    private String contactPhoneNumber;
    private String contactEmail;
    private String contactWebsite;
    private String contactAddress;

    //Venue Location
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
    //All the Getters and Setters:

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public String getVenuePicture() {
        return venuePicture;
    }

    public void setVenuePicture(String venuePicture) {
        this.venuePicture = venuePicture;
    }

    public int getVenueCategoryIcon() {
        return venueCategoryIcon;
    }

    public void setVenueCategoryIcon(int venueCategoryIcon) {
        this.venueCategoryIcon = venueCategoryIcon;
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

    public String getVenueSt() {
        return venueSt;
    }

    public void setVenueSt(String venueSt) {
        this.venueSt = venueSt;
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




}
