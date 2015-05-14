package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-05-13.
 */
public class Event {


    //All the variables the Event holds
    private int EventIcon;
    private String EventName;
    private String EventDate;
    private double EventPrice;
    private double EventDistance;
    private int EventPicture;


    //Default Constructor for Event
    public Event()
    {}


    //Get and Set methods for each of the global variables declared above.
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
