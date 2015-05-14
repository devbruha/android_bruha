package com.bruha.bruha.Processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thomas on 5/14/2015.
 */
public class ExpandableListDataProvider {

    public static HashMap<String, List<String>> getQuickieInfo(){

        HashMap<String, List<String>> QuickieFields = new HashMap<String, List<String>>();

        List<String> Quickie = new ArrayList<String>();

        Quickie.add("Featured");
        Quickie.add("Recommended");
        Quickie.add("Today");
        Quickie.add("This Weekend");

        QuickieFields.put("Quickie", Quickie);

        return QuickieFields;
    }
}
