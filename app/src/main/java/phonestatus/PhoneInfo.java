package phonestatus;

import java.util.List;

import logutils.LogUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：phonestatus
 * FileName：PhoneInfo.java
 * Date：2015/12/1 19
 * Author：大鹏
 * ClassName:PhoneInfo
 **/
public class PhoneInfo {
    /**
     * 手机名称
     */
    private String phoneName;

    /**
     * 手机系统版本
     */
    private String phoneSysCode;
    /**
     * 手机总内存
     */
    private String phoneTotalRAM;
    /**
     * 手机剩余内存
     */
    private String phoneAvailableRAM;
    /**
     * 手机内存百分比
     */
    private String phoneRAM;
    /**
     * 手机总储存
     */
    private String phoneTotalMemory;
    /**
     * 手机剩余储存
     */
    private String phoneAvailableMemory;
    /**
     * 手机存储百分比
     */
    private String phoneMemory;
    /**
     * 手机分辨率
     */
    private String phoneResolution;

    /**
     * 手机CPU型号
     */
    private String phoneCPUModel;

    /**
     * 手机IMEI
     */
    private String phoneIMEI;

    /**
     * 手机CPU核心数
     */
    private String phoneCPUNumCores;

    /**
     * 手机屏幕密度
     */
    private String phoneDisplayMetrics;

    /**
     * 手机是否root
     */
    private boolean isphoneroot;


    /**
     * 手机传感器列表
     */
    private List<SensorInfo> sensorInfoList;

    /**
     * 系统刷机包
     */
    private String phoneSysROM;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneSysCode() {
        return phoneSysCode;
    }

    public void setPhoneSysCode(String phoneSysCode) {
        this.phoneSysCode = phoneSysCode;
    }

    public String getPhoneTotalRAM() {
        return phoneTotalRAM;
    }

    public void setPhoneTotalRAM(String phoneTotalRAM) {
        this.phoneTotalRAM = phoneTotalRAM;
    }

    public String getPhoneAvailableRAM() {
        return phoneAvailableRAM;
    }

    public void setPhoneAvailableRAM(String phoneAvailableRAM) {
        this.phoneAvailableRAM = phoneAvailableRAM;
    }

    public String getPhoneRAM() {
        return phoneRAM;
    }

    public void setPhoneRAM(String phoneRAM) {
        this.phoneRAM = phoneRAM;
    }

    public String getPhoneTotalMemory() {
        return phoneTotalMemory;
    }

    public void setPhoneTotalMemory(String phoneTotalMemory) {
        this.phoneTotalMemory = phoneTotalMemory;
    }

    public String getPhoneAvailableMemory() {
        return phoneAvailableMemory;
    }

    public void setPhoneAvailableMemory(String phoneAvailableMemory) {
        this.phoneAvailableMemory = phoneAvailableMemory;
    }

    public String getPhoneMemory() {
        return phoneMemory;
    }

    public void setPhoneMemory(String phoneMemory) {
        this.phoneMemory = phoneMemory;
    }

    public String getPhoneResolution() {
        return phoneResolution;
    }

    public void setPhoneResolution(String phoneResolution) {
        this.phoneResolution = phoneResolution;
    }

    public String getPhoneCPUModel() {
        return phoneCPUModel;
    }

    public void setPhoneCPUModel(String phoneCPUModel) {
        this.phoneCPUModel = phoneCPUModel;
    }

    public String getPhoneIMEI() {
        return phoneIMEI;
    }

    public void setPhoneIMEI(String phoneIMEI) {
        this.phoneIMEI = phoneIMEI;
    }

    public String getPhoneCPUNumCores() {
        return phoneCPUNumCores;
    }

    public void setPhoneCPUNumCores(String phoneCPUNumCores) {
        this.phoneCPUNumCores = phoneCPUNumCores;
    }

    public String getPhoneDisplayMetrics() {
        return phoneDisplayMetrics;
    }

    public void setPhoneDisplayMetrics(String phoneDisplayMetrics) {
        this.phoneDisplayMetrics = phoneDisplayMetrics;
    }

    public boolean isphoneroot() {
        return isphoneroot;
    }

    public void setIsphoneroot(boolean isphoneroot) {
        this.isphoneroot = isphoneroot;
    }

    public List<SensorInfo> getSensorInfoList() {
        return sensorInfoList;
    }

    public void setSensorInfoList(List<SensorInfo> sensorInfoList) {
        this.sensorInfoList = sensorInfoList;
    }

    public String getPhoneSysROM() {
        return phoneSysROM;
    }

    public void setPhoneSysROM(String phoneSysROM) {
        this.phoneSysROM = phoneSysROM;
    }

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "phoneName='" + phoneName + '\'' +
                ", phoneSysCode='" + phoneSysCode + '\'' +
                ", phoneTotalRAM='" + phoneTotalRAM + '\'' +
                ", phoneAvailableRAM='" + phoneAvailableRAM + '\'' +
                ", phoneRAM='" + phoneRAM + '\'' +
                ", phoneTotalMemory='" + phoneTotalMemory + '\'' +
                ", phoneAvailableMemory='" + phoneAvailableMemory + '\'' +
                ", phoneMemory='" + phoneMemory + '\'' +
                ", phoneResolution='" + phoneResolution + '\'' +
                ", phoneCPUModel='" + phoneCPUModel + '\'' +
                ", phoneIMEI='" + phoneIMEI + '\'' +
                ", phoneCPUNumCores='" + phoneCPUNumCores + '\'' +
                ", phoneDisplayMetrics='" + phoneDisplayMetrics + '\'' +
                ", isphoneroot=" + isphoneroot +
                ", sensorInfoList=" + sen(sensorInfoList) +
                '}';
    }

    public String  sen(List<SensorInfo> sensorInfoList){
        LogUtils.e("sensorInfoList长度：  "+sensorInfoList.size());
        StringBuffer stringBuffer=new StringBuffer();
        for (SensorInfo sensorInfo: sensorInfoList){
            stringBuffer.append(sensorInfo.getSensorname()+"\n");
        }
        return stringBuffer.toString();
    }
}
