package phonestatus;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.cm.cmframeutils.BaseActivity;
import com.cm.cmframeutils.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import logutils.LogUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：phonestatus
 * FileName：PhoneStatusActivity.java
 * Date：2015/12/1 45
 * Author：大鹏
 * ClassName:PhoneStatusActivity
 **/
public class PhoneStatusActivity extends BaseActivity{
    private Context mContext;
    private TextView phone_text_status;
    private GLSurfaceView mGLSurfaceView;
    private PhoneInfoImpl phoneInfo;
    private ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_status);
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(new Renderer());
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mContext = PhoneStatusActivity.this;
        phone_text_status = (TextView) findViewById(R.id.phone_text_status);
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setPhoneName(getPhoneName());
        phoneInfo.setPhoneSysCode(getPhoneSysCode());
        phoneInfo.setPhoneTotalRAM(getTotalRAM());
        phoneInfo.setPhoneAvailableRAM(getPhoneRAM());

        phoneInfo.setPhoneTotalMemory(getPhoneROM(1));
        phoneInfo.setPhoneAvailableMemory(getPhoneROM(0));

        phoneInfo.setPhoneResolution(getPhoneResolution());
        phoneInfo.setPhoneCPUModel(String.valueOf(getCpuModel()));
        phoneInfo.setPhoneIMEI(getIMEI());
        phoneInfo.setPhoneCPUNumCores(String.valueOf(getNumCores()) + "核");
        phoneInfo.setPhoneDisplayMetrics(getPhoneDisplayMetrics());
        phoneInfo.setIsphoneroot(IsPhoneRoot());
        phoneInfo.setSensorInfoList(getSensor());
        phoneInfo.setPhoneSysROM(getPhoneSysROM());
        LogUtils.e("手机CPU名字：" +Build.BRAND+" "+android.os.Build.HARDWARE);
        LogUtils.e("手机是否root：" + IsPhoneRoot() + " ");
        LogUtils.e("信息： " + getManufactory());
        getSensor();
        try {
            execCommand("cat /proc/cpuinfo");
        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         * 线程池
         */
        phone_text_status.setText(phoneInfo.toString());
        executorService = Executors.newCachedThreadPool();
        executorService= Executors.newFixedThreadPool(3);
        executorService=Executors.newSingleThreadExecutor();

        /**
         * execute无返回值
         */
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("答应....");
            }
        });
        /**
         * submit可以有返回值
         */
        Future<PhoneInfo> a=executorService.submit(new Testservice());
        try {
            PhoneInfo b=a.get();
            LogUtils.e(b.getPhoneCPUModel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    class Testservice implements Callable<PhoneInfo>{

        @Override
        public PhoneInfo call() throws Exception {
            PhoneInfo info=new PhoneInfo();
            info.setPhoneCPUModel("aaaaaa");
            return info;
        }
    }
    /**
     * 获取系统刷机ROM
     */
    private String getPhoneSysROM() {
        return  Build.DEVICE;
    }

    /**
     * 获取剩余的内存空间
     *
     * @return
     */
    public String getPhoneRAM() {
        long mem_unused;
        // 得到ActivityManager
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 创建ActivityManager.MemoryInfo对象

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        // 取得剩余的内存空间
        return formatSize(mi.availMem);
    }

    /**
     * 获取总内存
     *
     * @return MB
     */
    public String getTotalRAM() {
        long mTotal;
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);
        return formatSize(mTotal * 1024);
    }

    /**
     * 获取储存
     *
     * @return
     */
    public String getPhoneROM(int type) {
        String state = Environment.getExternalStorageState();// 获取SD卡状态
        long size = 0, total = 0, available = 0;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File sdFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(sdFile.getPath());
            size = statfs.getBlockSize();// 每个block的大小
            total = statfs.getBlockCount();// 总block的大小
            available = statfs.getAvailableBlocks();// 可用block的大小
        }
        long maxStorage = total * size;
        long availStorage = available * size;
        if (type == 1) {
            return formatSize(maxStorage);
        } else {
            return formatSize(availStorage);
        }
    }

    /**
     * 获取手机型号
     */
    public String getPhoneName() {
        return Build.BRAND+" "+android.os.Build.MODEL;
    }

    /**
     * 获取系统版本
     */
    public String getPhoneSysCode() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取传感器信息
     *
     * @param view
     */
    public List<SensorInfo> getSensor() {
        //  获得SensorManager对象
        List<SensorInfo> sensorInfoList = new ArrayList<SensorInfo>();
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //得到手机上所有的传感器
        List<Sensor> listSensor = manager.getSensorList(Sensor.TYPE_ALL);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        for (Sensor sensor : listSensor) {
            SensorInfo sensorInfo = new SensorInfo();
//            LogUtils.e("sensor " + i+" 传感器名称"+ sensor.getName());
//            LogUtils.e("sensor " + i+" 传感器type"+ sensor.getType());
            switch (sensor.getType()) {
                //1加速度
                case Sensor.TYPE_ACCELEROMETER:
                    LogUtils.e("加速度传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("加速度传感器");
                    break;
                //2磁力
                case Sensor.TYPE_MAGNETIC_FIELD:
                    LogUtils.e("磁力传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("磁力传感器");
                    break;
                //3方向
                case Sensor.TYPE_ORIENTATION:
                    LogUtils.e("方向传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("方向传感器");
                    break;
                //4陀螺仪
                case Sensor.TYPE_GYROSCOPE:
                    LogUtils.e("陀螺仪");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("陀螺仪");
                    break;
                //5光线感应
                case Sensor.TYPE_LIGHT:
                    LogUtils.e("光线传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("光线传感器");
                    break;
                //6压力
                case Sensor.TYPE_PRESSURE:
                    LogUtils.e("压力传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("压力传感器");
                    break;
                //7温度
                case Sensor.TYPE_TEMPERATURE:
                    LogUtils.e("温度传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("温度传感器");
                    break;
                //8接近
                case Sensor.TYPE_PROXIMITY:
                    LogUtils.e("接近传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("接近传感器");
                    break;
                //9重力
                case Sensor.TYPE_GRAVITY:
                    LogUtils.e("重力传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("重力传感器");
                    break;
                //10线性加速度
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    LogUtils.e("线性加速度传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("线性加速度传感器");
                    break;
                //11旋转矢量
                case Sensor.TYPE_ROTATION_VECTOR:
                    LogUtils.e("旋转矢量传感器");
                    sensorInfo.setSensortype(sensor.getType());
                    sensorInfo.setSensorname("旋转矢量传感器");
                    break;
            }
            sensorInfoList.add(sensorInfo);
        }
        return sensorInfoList;
    }

    public String getCPU() {
        return Build.HARDWARE;
    }

    /**
     * 获取CPU类型
     */
    public String getCpuModel() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            LogUtils.e("CPU 信息" + text.toString());
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
                LogUtils.e("CPU 信息" + array[i].toString());
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }

    /**
     * 获取IMEI
     *
     * @return
     */
    public String getIMEI() {
        return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取手机分辨率
     *
     * @return
     */
    public String getPhoneResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return String.valueOf(height) + "X" + String.valueOf(width) + "像素";
    }

    /**
     * 获取屏幕DPI密度
     *
     * @return
     */
    public String getPhoneDisplayMetrics() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int densityDpi = metric.densityDpi;
        return String.valueOf(densityDpi) + "DPI";
    }

    //CPU个数
    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            LogUtils.e("CPU Count: " + files.length);
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception
            LogUtils.e("CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    public String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strInfo;
    }

    @Override
    public void onClickView(View view) {
        super.onClickView(view);
        switch (view.getId()) {
            case R.id.nav_image_back:
                finish();
                break;
        }
    }

    private class Renderer implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 渲染器
            LogUtils.e("GL_RENDERER:::::" + gl.glGetString(GL10.GL_RENDERER));
            // 供应商
            LogUtils.e("GL_VENDOR::::: " + gl.glGetString(GL10.GL_VENDOR));
            // 版本
            LogUtils.e("GL_VERSION::::: " + gl.glGetString(GL10.GL_VERSION));
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }

    public String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


    public boolean IsPhoneRoot() {
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public void execCommand(String command) throws IOException {
        // start the ls command running
        //String[] args =  new String[]{"sh", "-c", command};
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);        //这句话就是shell与高级语言间的调用
        //如果有参数的话可以用另外一个被重载的exec方法
        //实际上这样执行时启动了一个子进程,它没有父进程的控制台
        //也就看不到输出,所以我们需要用输出流来得到shell执行后的输出
        InputStream inputstream = proc.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        // read the ls output
        String line = "";
        StringBuilder sb = new StringBuilder(line);
        while ((line = bufferedreader.readLine()) != null) {
            //System.out.println(line);
            sb.append(line);
            sb.append('\n');
        }
        LogUtils.e("输出："+sb.toString());
        //tv.setText(sb.toString());
        //使用exec执行不会等执行成功以后才返回,它会立即返回
        //所以在某些情况下是很要命的(比如复制文件的时候)
        //使用wairFor()可以等待命令执行完成以后才返回
        try {
            if (proc.waitFor() != 0) {
                LogUtils.e("exit value = " + proc.exitValue());
            }
        } catch (InterruptedException e) {
            LogUtils.e(e.getMessage());
        }
    }
    public static String getManufactory(){
//        LogUtils.e("主板 : "+Build.BOARD); // 主板
//        LogUtils.e("android系统定制商 : "+Build.BRAND );// android系统定制商
//        LogUtils.e("cpu指令集 : "+Build.CPU_ABI); // cpu指令集
//        LogUtils.e("设备参数 : "+Build.DEVICE); // 设备参数
//        LogUtils.e("显示屏参数 : "+Build.DISPLAY); // 显示屏参数
//        LogUtils.e("硬件名称 : "+Build.FINGERPRINT); // 硬件名称
//        LogUtils.e("HOST : "+Build.HOST);
//        LogUtils.e("修订版本列表 : "+Build.ID); // 修订版本列表
//        LogUtils.e("硬件制造商 : "+Build.MANUFACTURER );// 硬件制造商
//        LogUtils.e("版本 : "+Build.MODEL); // 版本
//        LogUtils.e("手机制造商 : "+Build.PRODUCT); // 手机制造商
//        LogUtils.e("描述build的标签 : "+Build.TAGS );// 描述build的标签
//        LogUtils.e("时间 : "+Build.TIME+" ");
//        LogUtils.e("builder类型 : "+Build.TYPE); // builder类型
//        LogUtils.e("用户 : "+Build.USER);


        LogUtils.e("BOARD:" + Build.BOARD);
        LogUtils.e("BOOTLOADER:" + Build.BOOTLOADER);
        LogUtils.e("BRAND:" + Build.BRAND);
        LogUtils.e("CPU_ABI:" + Build.CPU_ABI);
        LogUtils.e("build"+"CPU_ABI2:" + Build.CPU_ABI2);
        LogUtils.e("build"+"DEVICE:" + Build.DEVICE);
        LogUtils.e("build"+"DISPLAY:" + Build.DISPLAY);
        LogUtils.e("build"+"FINGERPRINT:" + Build.FINGERPRINT);
        LogUtils.e("build"+"HARDWARE:" + Build.HARDWARE);
        LogUtils.e("build"+"HOST:" + Build.HOST);
        LogUtils.e("build"+"ID:" + Build.ID);
        LogUtils.e("build"+"MANUFACTURER:" + Build.MANUFACTURER);
        LogUtils.e("build"+"MODEL:" + Build.MODEL);
        LogUtils.e("build"+"PRODUCT:" + Build.PRODUCT);
        LogUtils.e("build"+"RADIO:" + Build.RADIO);
        LogUtils.e("build"+"TAGS:" + Build.TAGS);
        LogUtils.e("build"+"TIME:" + Build.TIME);
        LogUtils.e("build"+"TYPE:" + Build.TYPE);
        LogUtils.e("build"+"UNKNOWN:" + Build.UNKNOWN);
        LogUtils.e("build"+"USER:" + Build.USER);
        LogUtils.e("build"+"VERSION.CODENAME:" + Build.VERSION.CODENAME);
        LogUtils.e("build"+"VERSION.INCREMENTAL:" + Build.VERSION.INCREMENTAL);
        LogUtils.e("build"+"VERSION.RELEASE:" + Build.VERSION.RELEASE);
        LogUtils.e("build"+"VERSION.SDK:" + Build.VERSION.SDK);
        LogUtils.e("build"+"VERSION.SDK_INT:" + Build.VERSION.SDK_INT);


        return android.os.Build.MANUFACTURER +"\n"+android.os.Build.BOARD+ "\n"+android.os.Build.DEVICE+ "\n"+android.os.Build.PRODUCT;
    }
}
