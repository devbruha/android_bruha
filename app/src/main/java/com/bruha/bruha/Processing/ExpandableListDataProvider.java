package com.bruha.bruha.Processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thomas on 5/14/2015.
 */
public class ExpandableListDataProvider {

    // A function used to populate all "quickie" fields

    public static HashMap<String, List<String>> getQuickieInfo(){

        // Creating a hashmap to store the list of fields under the "Quickie" tag

        HashMap<String, List<String>> QuickieFields = new HashMap<String, List<String>>();

        // creating a string list of the fields

        List<String> Quickie = new ArrayList<String>();

        Quickie.add("Featured");
        Quickie.add("Recommended");
        Quickie.add("Today");
        Quickie.add("This Weekend");

        QuickieFields.put("Quickie", Quickie);

        return QuickieFields;
    }
}
