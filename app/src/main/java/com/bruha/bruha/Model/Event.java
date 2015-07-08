package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-05-13.
 */
public class Event {

    //All the variables the Event Summary holds.
    private int EventIcon;
    private String EventName;
    private String EventDate;
    private double EventPrice;
    private double EventDistance;
    private String EventPicture;

    //ALL the variables for the lat/lng of event

    private double eventLatitude;
    private double eventLongitude;

    //All the variables the Event Detailed Description holds.
    private String EventLocName;
    private String EventLocSt;
    private String EventLocAdd;
    private String EventStartTime;
    private String EventEndDate;
    private String EventEndTime;

    //SQL variables and eventpage
    private String EventDescription;
    private String Eventid;
    private String Venueid;
    private String LocationID;

    //Default Constructor for Event.
    public Event()
    {}

    //All the setters and getters of the variables declared above.


    //Getters and Setters for the Detailed Description of event.
    public String getEventLocName() {
        return EventLocName;
    }

    public void setEventLocName(String eventLocName) {
        EventLocName = eventLocName;
    }

    public String getEventLocSt() {
        return EventLocSt;
    }

    public void setEventLocSt(String eventLocSt) {
        EventLocSt = eventLocSt;
    }

    public String getEventLocAdd() {
        return EventLocAdd;
    }

    public void setEventLocAdd(String eventLocAdd) {
        EventLocAdd = eventLocAdd;
    }

    public String getEventStartTime() {
        return EventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        EventStartTime = eventStartTime;
    }

    public String getEventEndDate() {
        return EventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        EventEndDate = eventEndDate;
    }

    public String getEventEndTime() {
        return EventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        EventEndTime = eventEndTime;
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
        return EventPicture;
    }

    public void setEventPicture(String eventPicture) {
        EventPicture = eventPicture;
    }

    public int getEventIcon() {
        return EventIcon;
    }

    public void setEventIcon(int eventIcon) {
        EventIcon = eventIcon;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public double getEventPrice() {
        return EventPrice;
    }

    public void setEventPrice(double eventPrice) {
        EventPrice = eventPrice;
    }

    public double getEventDistance() {
        return EventDistance;
    }

    public void setEventDistance(double eventDistance) {
        EventDistance = eventDistance;
    }

    //Event Page getter and setters.
    public String getEventDescription() { return EventDescription; }

    public void setEventDescription(String eventDescription) { EventDescription = eventDescription; }

    public String getVenueid() { return Venueid; }

    public void setVenueid(String venueid) {
        Venueid = venueid;
    }

    public String getEventid() {
        return Eventid;
    }

    public void setEventid(String eventid) {
        Eventid = eventid;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        LocationID = locationID;
    }
}
