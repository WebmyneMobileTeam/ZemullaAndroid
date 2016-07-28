package com.zemulla.android.app.home;

/**
 * Created by dhruvil on 28-07-2016.
 */

public class DrawerOptionBean {

    private String optionName;
    private DrawerOptionsConfiguration.OptionID id;
    private int icon;

    public DrawerOptionBean() {
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public DrawerOptionsConfiguration.OptionID getId() {
        return id;
    }

    public void setId(DrawerOptionsConfiguration.OptionID id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
