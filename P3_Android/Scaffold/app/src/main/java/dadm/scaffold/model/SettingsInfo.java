package dadm.scaffold.model;



public class SettingsInfo {

    private int drawableRes;

    private static SettingsInfo instance;


    private SettingsInfo() {

    }

    public static SettingsInfo getInstance() {
        if (instance == null) {
            instance = new SettingsInfo();
        }
        return instance;

    }


    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }
}


