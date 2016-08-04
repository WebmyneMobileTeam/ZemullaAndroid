package com.zemulla.android.app.fundtransfer;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class FundTransferTileBean {

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

    public FundTransferConfiguration.FundTransfer_IDTILE getId() {
        return id;
    }

    public void setId(FundTransferConfiguration.FundTransfer_IDTILE id) {
        this.id = id;
    }

    private FundTransferConfiguration.FundTransfer_IDTILE id;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public FundTransferTileBean() {
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
