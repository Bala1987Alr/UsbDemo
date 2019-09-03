package android.serialport;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UsbObservable{
    private static boolean isRead = true;// 线程标志
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static int length = 0;
    static  StringBuffer stringBuffer = new StringBuffer();
    static SerialPort serialPort = new SerialPort();
   static Observable usbObservableeee = new Usb();
    public static void open(final String address, final int baudrate){
        isRead = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[1024];
                while(isRead){
                    if (inputStream  == null){
                        serialPort.createSerialPort(address,baudrate);
                        inputStream = serialPort.getInputStream();
                        outputStream = serialPort.getOutputStream();
                        usbObservableeee.usbTips("开启成功");
                    }
                    try {
                        length = inputStream.available();
                        if (length>0){
                            length = inputStream.read(bytes);
                            byte[]  dd = new byte[length];
                            System.arraycopy(bytes,0,dd,0,length);
                            byteMerger(dd);

                        }else{
                            Thread.sleep(30);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        usbObservableeee.usbTips("串口异常"+e.getMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        usbObservableeee.usbTips("串口异常"+e.getMessage());
                    }
                }
                usbObservableeee.close();
                usbObservableeee.usbTips("关闭");
                try {
                    inputStream.close();
                    outputStream.close();
                    serialPort.closeDevice();
                    outputStream = null;
                    inputStream = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 发送数据
     * @param buff
     */
    public static void writeData(byte[] buff){
        if (outputStream!= null){
            try {
                outputStream.write(buff);
            } catch (IOException e) {
                e.printStackTrace();
                usbObservableeee.usbTips(e.getMessage());
            }
        }
    }


    // 10k字节
    private static byte[] buffByte = new byte[10240];
    private static int buffByteLength = 0;
    /**
     * 字节拼接
     */
    private static void byteMerger(byte[] buff){
        if (buff.length<2){
            return;
        }
        if ((buffByteLength+buff.length)>  10240){
            buffByteLength = 0;
        }
        System.arraycopy(buff,0,buffByte,buffByteLength,buff.length);
        if ((buff[buff.length-1]=='\n')&&(buff[buff.length-2]=='\r')){
            // 找到了二维码
            buffByteLength+= buff.length;
            // 去掉尾部
            byte[] mergerByte = new byte[buffByteLength-2];
            System.arraycopy(buffByte,0, mergerByte,0,buffByteLength-2);
            usbObservableeee.notify(mergerByte);

            buffByteLength = 0;
        }else{
            buffByteLength += buff.length;
        }
    }

    private static List<Observer> personList = new ArrayList<Observer>();//保存（观察者）的信息

    /**
     * 订阅
     * @param observer
     */
    public static void SubscribeOn(Observer observer){
        usbObservableeee.add(observer);
    }

    /**
     * 注销
     */
    public static void unRegisteredObserver(){
        for (Observer observerr:personList){
            usbObservableeee.remove(observerr);
        }

    }



    public static void  close(){
        isRead = false;
    }


    public static void clear(){
        stringBuffer.delete(0,stringBuffer.length());
        if (file!= null){
            file.delete();
        }
    }



    static File file ;
    public static void writeFile(String  content,String name) {
        BufferedWriter bw = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/usbSerial/"+System.currentTimeMillis()+name+".txt");
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeFileByte(byte[]  buff,String name) {
        try {
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/usbSerial/"+System.currentTimeMillis()+name+".txt");
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile(),true);
            fileOutputStream.write(buff);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String byteArrayToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
}
