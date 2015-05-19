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
    private int EventPicture;

    //All the variables the Event Detailed Description holds.
    private String EventLocName;
    private String EventLocSt;
    private String EventLocAdd;
    private String EventStartTime;
    private String EventEndDate;
    private String EventEndTime;


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



    //Getters and Setters for the Summary Description of event.
    public int getEventPicture() {
        return EventPicture;
    }

    public void setEventPicture(int eventPicture) {
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



}
