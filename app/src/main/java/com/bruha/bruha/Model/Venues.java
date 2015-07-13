package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-06-09.
 */
public class Venues {

    //Venue main Database data.
    private int VenueId;
    private String VenueName;
    private String VenueLocation;
    private String VenueDescription;

    //Venue Media data.
    private String VenuePicture;
    private int VenueCategoryIcon;

    //Venue Contact data.
    private String ContactName;
    private String ContactPhoneNumber;
    private String ContactEmail;
    private String ContactWebsite;
    private String ContactAddress;

    //Venue Location
    private double Lat;
    private double Lng;

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
    //All the Getters and Setters:

    public int getVenueId() {
        return VenueId;
    }

    public void setVenueId(int venueId) {
        VenueId = venueId;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getVenueLocation() {
        return VenueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        VenueLocation = venueLocation;
    }

    public String getVenueDescription() {
        return VenueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        VenueDescription = venueDescription;
    }

    public String getVenuePicture() {
        return VenuePicture;
    }

    public void setVenuePicture(String venuePicture) {
        VenuePicture = venuePicture;
    }

    public int getVenueCategoryIcon() {
        return VenueCategoryIcon;
    }

    public void setVenueCategoryIcon(int venueCategoryIcon) {
        VenueCategoryIcon = venueCategoryIcon;
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
