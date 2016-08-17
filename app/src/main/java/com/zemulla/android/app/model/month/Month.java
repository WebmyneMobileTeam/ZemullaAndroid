package com.zemulla.android.app.model.month;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class Month {
    public int id;
    public String name;
    public String displayText;

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Month(int id, String name, String displayText) {
        this.id = id;
        this.name = name;
        this.displayText = displayText;
    }

    public static List<Month> monthList() {

        List<Month> months = new ArrayList<>();

        months.add(new Month(0, "Month", "Month"));
        months.add(new Month(1, "January", "1 - January"));
        months.add(new Month(2, "February", "2 - February"));
        months.add(new Month(3, "March", "3 - March"));
        months.add(new Month(4, "April", "4 - April"));
        months.add(new Month(5, "May", "5 - May"));
        months.add(new Month(6, "June", "6 - June"));
        months.add(new Month(7, "July", "7 - July"));
        months.add(new Month(8, "August", "8 - August"));
        months.add(new Month(9, "September", "9 - September"));
        months.add(new Month(10, "October", "10 - October"));
        months.add(new Month(11, "November", "11 - November"));
        months.add(new Month(12, "December", "12 - December"));
        return months;
    }
}
