package dadm.scaffold.model;



public class SettingsInfo {

    private int drawableRes;

    private int enemiesKilled;

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

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }
}


