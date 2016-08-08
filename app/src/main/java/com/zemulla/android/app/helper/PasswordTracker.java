package com.zemulla.android.app.helper;

/**
 * Created by sagartahelyani on 08-08-2016.
 */
public class PasswordTracker {

    private String text;
    private int color;
    private int passwordType;

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }
}
