package com.zemulla.android.app.topup;

import com.zemulla.android.app.home.HomeTileConfiguration;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TopupTileBean {

    private String tileName;
    private int tileIcon;
    private String backgroundColor;

    public String getBackgroundColorDark() {
        return backgroundColorDark;
    }

    public void setBackgroundColorDark(String backgroundColorDark) {
        this.backgroundColorDark = backgroundColorDark;
    }

    private String backgroundColorDark;

    public TopupTileConfiguration.TOPUP_IDTILE getId() {
        return id;
    }

    public void setId(TopupTileConfiguration.TOPUP_IDTILE id) {
        this.id = id;
    }

    private TopupTileConfiguration.TOPUP_IDTILE id;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public TopupTileBean() {
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public int getTileIcon() {
        return tileIcon;
    }

    public void setTileIcon(int tileIcon) {
        this.tileIcon = tileIcon;
    }
}
