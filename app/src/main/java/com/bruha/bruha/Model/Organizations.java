package com.bruha.bruha.Model;

/**
 * Created by Work on 2015-06-12.
 */
public class Organizations {

    private String orgId;
    private String orgName;
    private String orgLocation;
    private String orgDescription;
    private String orgSt;
    private int locId;
    private double lat;
    private double lng;
    private String orgPicture;
    private String orgPrimaryCategory;

    public String getOrgSt() {
        return orgSt;
    }

    public void setOrgSt(String orgSt) {
        this.orgSt = orgSt;
    }

    public String getOrgPicture() {
        return orgPicture;
    }

    public void setOrgPicture(String orgPicture) {
        this.orgPicture = orgPicture;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLocation() {
        return orgLocation;
    }

    public void setOrgLocation(String orgLocation) {
        this.orgLocation = orgLocation;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

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

    public String getOrgPrimaryCategory() {
        return orgPrimaryCategory;
    }

    public void setOrgPrimaryCategory(String orgPrimaryCategory) {
        this.orgPrimaryCategory = orgPrimaryCategory;
    }
}