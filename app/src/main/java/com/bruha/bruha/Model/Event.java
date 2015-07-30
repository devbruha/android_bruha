package com.bruha.bruha.Model;

import java.util.ArrayList;

/**
 * Created by Work on 2015-05-13.
 */
public class Event {
    //All the variables the Event Summary holds.
    private int eventIcon;
    private String eventName;
    private String eventDate;
    private double eventPrice;
    private double eventDistance;
    private String eventPicture;

    //ALL the variables for the lat/lng of event
    private double eventLatitude;
    private double eventLongitude;

    //All the variables the Event Detailed Description holds.
    private String eventLocName;
    private String eventLocSt;
    private String eventLocAdd;
    private String eventStartTime;
    private String eventEndDate;
    private String eventEndTime;

    // Variables for event categories

    private String eventPrimaryCategory;
    private ArrayList<String> eventSubCategories = new ArrayList<>();

    //SQL variables and eventpage
    private String eventDescription;
    private String eventid;
    private String venueid;
    private String locationID;

    //Default Constructor for Event.
    public Event()
    {}

    //All the setters and getters of the variables declared above.


    //Getters and Setters for the Detailed Description of event.
    public String getEventLocName() {
        return eventLocName;
    }

    public void setEventLocName(String eventLocName) {
        this.eventLocName = eventLocName;
    }

    public String getEventLocSt() {
        return eventLocSt;
    }

    public void setEventLocSt(String eventLocSt) {
        this.eventLocSt = eventLocSt;
    }

    public String getEventLocAdd() {
        return eventLocAdd;
    }

    public void setEventLocAdd(String eventLocAdd) {
        this.eventLocAdd = eventLocAdd;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    //Getters and Setters for lat/lng

    public double getEventLatitude() {
        return eventLatitude;
    }

    public void setEventLatitude(double eventLatitude) {
        this.eventLatitude = eventLatitude;
    }

    public double getEventLongitude() {
        return eventLongitude;
    }

    public void setEventLongitude(double eventLongitude) {
        this.eventLongitude = eventLongitude;
    }

    //Getters and Setters for the Summary Description of event.

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public int getEventIcon() {
        return eventIcon;
    }

    public void setEventIcon(int eventIcon) {
        this.eventIcon = eventIcon;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public double getEventDistance() {
        return eventDistance;
    }

    public void setEventDistance(double eventDistance) {
        this.eventDistance = eventDistance;
    }

    //Event Page getter and setters.
    public String getEventDescription() { return eventDescription; }

    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public String getVenueid() { return venueid; }

    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventPrimaryCategory() {
        return eventPrimaryCategory;
    }

    public void setEventPrimaryCategory(String eventPrimaryCategory) {
        this.eventPrimaryCategory = eventPrimaryCategory;
    }

    public ArrayList<String> getEventSubCategories() {
        return eventSubCategories;
    }

    public void setEventSubCategories(ArrayList<String> eventSubCategories) {
        this.eventSubCategories = eventSubCategories;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
}