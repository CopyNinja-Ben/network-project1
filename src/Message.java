import java.io.Serializable;

public class Message implements Serializable {
    private String title;
    private int versionNum;
    private int updateInterval;
    private int aliveInterval;

    public Message(String title){
        this.title = title;
    }

    public Message(String title, int versionNum, int updateInterval, int aliveInterval){
        this.title = title;
        this.versionNum = versionNum;
        this.updateInterval = updateInterval;
        this.aliveInterval = aliveInterval;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public int getAliveInterval() {
        return aliveInterval;
    }

    public void setAliveInterval(int aliveInterval) {
        this.aliveInterval = aliveInterval;
    }
}
