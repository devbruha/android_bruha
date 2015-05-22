package com.bruha.bruha.Processing;

/**
 * Created by Thomas on 5/22/2015.
 */
import com.bruha.bruha.Model.Items;
import com.bruha.bruha.Model.Items.SubCategory;
import com.bruha.bruha.Model.Items.SubCategory.ItemList;
import java.util.ArrayList;


/**
 * Created by Thomas on 5/21/2015.
 */
public class FilterGen {

    private ArrayList<Items>mainList = new ArrayList<Items>();
    private ArrayList<SubCategory>ParentArrayList = new ArrayList<SubCategory>();

    //Each arraylist corresponds to a different primary category
    ArrayList<Items.SubCategory.ItemList> primaryCategory1=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory2=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory3=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory4=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory5=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory6=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory7=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory8=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory9=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory10=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory11=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory12=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory13=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory14=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory15=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory16=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory17=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> primaryCategory18=new ArrayList<ItemList>();

    //Each arraylist corresponds to a different recommended category
    ArrayList<Items.SubCategory.ItemList> recommendedCategory1=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> recommendedCategory2=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> recommendedCategory3=new ArrayList<ItemList>();
    ArrayList<Items.SubCategory.ItemList> recommendedCategory4=new ArrayList<ItemList>();

    public ArrayList<Items> initCategory(){

        addParentLevel();
        addPrimaryCategories();
        addSubCategories();

        return mainList;
    }

    public ArrayList<Items> initRecommended(){

        mainList.add(new Items("Quickie", ParentArrayList));

        //Add arraylist in category
        ParentArrayList.add(new Items.SubCategory("Featured", recommendedCategory1));
        ParentArrayList.add(new Items.SubCategory("Recommended", recommendedCategory2));
        ParentArrayList.add(new Items.SubCategory("Today", recommendedCategory3));
        ParentArrayList.add(new Items.SubCategory("This Weekend", recommendedCategory4));

        return mainList;
    }

    public void addParentLevel(){

        //Add main categories in Mainlists along with their items it
        mainList.add(new Items("Categories", ParentArrayList));
    }

    public void addPrimaryCategories(){

        //Add arraylist in category
        ParentArrayList.add(new Items.SubCategory("Academic", primaryCategory1));
        ParentArrayList.add(new Items.SubCategory("Business", primaryCategory2));
        ParentArrayList.add(new Items.SubCategory("Charity", primaryCategory3));
        ParentArrayList.add(new Items.SubCategory("Competitions", primaryCategory4));
        ParentArrayList.add(new Items.SubCategory("Culture & Religion", primaryCategory5));
        ParentArrayList.add(new Items.SubCategory("Fashion & Beauty", primaryCategory6));
        ParentArrayList.add(new Items.SubCategory("Festivals", primaryCategory7));
        ParentArrayList.add(new Items.SubCategory("Film", primaryCategory8));
        ParentArrayList.add(new Items.SubCategory("Food & Drink", primaryCategory9));
        ParentArrayList.add(new Items.SubCategory("Live Performance", primaryCategory10));
        ParentArrayList.add(new Items.SubCategory("Night Life", primaryCategory11));
        ParentArrayList.add(new Items.SubCategory("Political", primaryCategory12));
        ParentArrayList.add(new Items.SubCategory("Seasonal", primaryCategory13));
        ParentArrayList.add(new Items.SubCategory("Sports", primaryCategory14));
        ParentArrayList.add(new Items.SubCategory("Travel", primaryCategory15));
        ParentArrayList.add(new Items.SubCategory("Visual Arts", primaryCategory16));
        ParentArrayList.add(new Items.SubCategory("Well Being", primaryCategory17));
        ParentArrayList.add(new Items.SubCategory("Other", primaryCategory18));
    }

    public void addSubCategories(){

        primaryCategory1.add(new ItemList("SubCat1"));
        primaryCategory1.add(new ItemList("SubCat2"));
        primaryCategory1.add(new ItemList("SubCat3"));

        primaryCategory2.add(new ItemList("SubCat4"));
        primaryCategory2.add(new ItemList("SubCat5"));
        primaryCategory2.add(new ItemList("SubCat6"));

        primaryCategory3.add(new ItemList("SubCat7"));
        primaryCategory3.add(new ItemList("SubCat8"));
        primaryCategory3.add(new ItemList("SubCat9"));

        primaryCategory4.add(new ItemList("SubCat10"));
        primaryCategory4.add(new ItemList("SubCat11"));
        primaryCategory4.add(new ItemList("SubCat12"));

        primaryCategory5.add(new ItemList("SubCat13"));
        primaryCategory5.add(new ItemList("SubCat14"));
        primaryCategory5.add(new ItemList("SubCat15"));

        primaryCategory6.add(new ItemList("SubCat16"));
        primaryCategory6.add(new ItemList("SubCat17"));
        primaryCategory6.add(new ItemList("SubCat18"));

        primaryCategory7.add(new ItemList("SubCat19"));
        primaryCategory7.add(new ItemList("SubCat20"));
        primaryCategory7.add(new ItemList("SubCat21"));

        primaryCategory8.add(new ItemList("SubCat22"));
        primaryCategory8.add(new ItemList("SubCat23"));
        primaryCategory8.add(new ItemList("SubCat24"));

        primaryCategory9.add(new ItemList("SubCat25"));
        primaryCategory9.add(new ItemList("SubCat26"));
        primaryCategory9.add(new ItemList("SubCat27"));

        primaryCategory10.add(new ItemList("SubCat28"));
        primaryCategory10.add(new ItemList("SubCat29"));
        primaryCategory10.add(new ItemList("SubCat30"));

        primaryCategory11.add(new ItemList("SubCat31"));
        primaryCategory11.add(new ItemList("SubCat32"));
        primaryCategory11.add(new ItemList("SubCat33"));

        primaryCategory12.add(new ItemList("SubCat34"));
        primaryCategory12.add(new ItemList("SubCat35"));
        primaryCategory12.add(new ItemList("SubCat36"));

        primaryCategory13.add(new ItemList("SubCat37"));
        primaryCategory13.add(new ItemList("SubCat38"));
        primaryCategory13.add(new ItemList("SubCat39"));

        primaryCategory14.add(new ItemList("SubCat40"));
        primaryCategory14.add(new ItemList("SubCat41"));
        primaryCategory14.add(new ItemList("SubCat42"));

        primaryCategory15.add(new ItemList("SubCat43"));
        primaryCategory15.add(new ItemList("SubCat44"));
        primaryCategory15.add(new ItemList("SubCat45"));

        primaryCategory16.add(new ItemList("SubCat46"));
        primaryCategory16.add(new ItemList("SubCat47"));
        primaryCategory16.add(new ItemList("SubCat48"));

        primaryCategory17.add(new ItemList("SubCat49"));
        primaryCategory17.add(new ItemList("SubCat50"));
        primaryCategory17.add(new ItemList("SubCat51"));

        primaryCategory18.add(new ItemList("SubCat52"));
        primaryCategory18.add(new ItemList("SubCat53"));
        primaryCategory18.add(new ItemList("SubCat54"));

    }

}
