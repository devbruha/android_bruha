package com.bruha.bruha.Model;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 5/26/2015.
 */
public class UserCustomFilters {

    private ArrayList<String> QuickieFilter = new ArrayList<>();
    private ArrayList<String> DateFilter = new ArrayList<>();

    private ArrayList<String> SubCategoryIDFilter = new ArrayList<>();

    private ArrayList<Date> nonFormattedDateFilter = new ArrayList<>();

    private Map<String, ArrayList<String>> CategoryFilter = new HashMap<>();
    private ArrayList<String> VenueFilter = new ArrayList<>();
    private ArrayList<String> ArtistFilter = new ArrayList<>();
    private ArrayList<String> OrganizationFilter = new ArrayList<>();

    private int AdmissionPriceFilter = -1;

    public UserCustomFilters(){

    }

    public UserCustomFilters(UserCustomFilters originalFilter){

        for(String key: originalFilter.getCategoryFilter().keySet()){

            this.CategoryFilter.put(key,originalFilter.getCategoryFilter().get(key));
        }

        for(int i = 0; i<originalFilter.getVenueFilter().size();i++){

            this.VenueFilter.add(originalFilter.getVenueFilter().get(i));
        }

        for(int i = 0; i<originalFilter.getArtistFilter().size();i++){

            this.ArtistFilter.add(originalFilter.getArtistFilter().get(i));
        }

        for(int i = 0; i<originalFilter.getOrganizationFilter().size();i++){

            this.OrganizationFilter.add(originalFilter.getOrganizationFilter().get(i));
        }
    }


    public ArrayList<String> getQuickieFilter() {
        return QuickieFilter;
    }

    public void setQuickieFilter(ArrayList<String> quickieFilter) {
        QuickieFilter = quickieFilter;
    }

    public ArrayList<String> getDateFilter() {
        return DateFilter;
    }

    public void setDateFilter(ArrayList<String> dateFilter) {
        DateFilter = dateFilter;
    }

    public Map<String, ArrayList<String>> getCategoryFilter() {
        return CategoryFilter;
    }

    public void setCategoryFilter(Map<String, ArrayList<String>> categoryFilter) {
        CategoryFilter = categoryFilter;
    }

    public int getAdmissionPriceFilter() {
        return AdmissionPriceFilter;
    }

    public void setAdmissionPriceFilter(int admissionPriceFilter) {
        AdmissionPriceFilter = admissionPriceFilter;
    }

    public ArrayList<Date> getNonFormattedDateFilter() {
        return nonFormattedDateFilter;
    }

    public void setNonFormattedDateFilter(ArrayList<Date> nonFormattedDateFilter) {
        this.nonFormattedDateFilter = nonFormattedDateFilter;
    }

    public ArrayList<String> getSubCategoryIDFilter() {
        return SubCategoryIDFilter;
    }

    public void setSubCategoryIDFilter(ArrayList<String> subCategoryIDFilter) {
        SubCategoryIDFilter = subCategoryIDFilter;
    }

    public ArrayList<String> getArtistFilter() {
        return ArtistFilter;
    }

    public void setArtistFilter(ArrayList<String> artistFilter) {
        ArtistFilter = artistFilter;
    }

    public ArrayList<String> getVenueFilter() {
        return VenueFilter;
    }

    public void setVenueFilter(ArrayList<String> venueFilter) {
        VenueFilter = venueFilter;
    }

    public ArrayList<String> getOrganizationFilter() {
        return OrganizationFilter;
    }

    public void setOrganizationFilter(ArrayList<String> organizationFilter) {
        OrganizationFilter = organizationFilter;
    }

}
