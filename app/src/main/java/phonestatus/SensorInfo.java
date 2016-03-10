package phonestatus;

import java.io.Serializable;

/**
 * ProjectName：cmframeutils
 * PackageName：phonestatus
 * FileName：SensorInfo.java
 * Date：2015/12/3 06
 * Author：大鹏
 * ClassName:SensorInfo
 **/
public class SensorInfo implements Serializable {
    /**
     * id标识
     */
    private int id;
    /**
     * 传感器类型
     */
    private int sensortype;
    /**
     * 传感器名称
     */
    private String sensorname;
    /**
     * 传感器描述
     */
    private String sensordescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensortype() {
        return sensortype;
    }

    public void setSensortype(int sensortype) {
        this.sensortype = sensortype;
    }

    public String getSensorname() {
        return sensorname;
    }

    public void setSensorname(String sensorname) {
        this.sensorname = sensorname;
    }

    public String getSensordescription() {
        return sensordescription;
    }

    public void setSensordescription(String sensordescription) {
        this.sensordescription = sensordescription;
    }
}
