package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-06-12.
 */
public class Organizations {


    //
    private int OrgId;
    private String OrgName;
    private String OrgLocation;
    private String OrgDescription;
    private int LocId;
    private double Lat;
    private double Lng;

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int orgId) {
        OrgId = orgId;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getOrgLocation() {
        return OrgLocation;
    }

    public void setOrgLocation(String orgLocation) {
        OrgLocation = orgLocation;
    }

    public String getOrgDescription() {
        return OrgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        OrgDescription = orgDescription;
    }

    public int getLocId() {
        return LocId;
    }

    public void setLocId(int locId) {
        LocId = locId;
    }

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
}
