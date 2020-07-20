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
    private static boolean isRead = true;// Thread flag
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static int length = 0;
    private static  StringBuffer stringBuffer = new StringBuffer();
    private static SerialPort serialPort = new SerialPort();
    private static Observable usbObservableeee = new Usb();
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
                        usbObservableeee.usbTips("Successfully opened");
                    }
                    if (inputStream == null){
                        usbObservableeee.usbTips("inputStream is empty. Check whether the serial port address is accurate.");
                        continue;
                    }
                    try {
                        length = inputStream.available();
                        if (length>0){
                            length = inputStream.read(bytes);
                            byte[]  dd = new byte[length];
                            System.arraycopy(bytes,0,dd,0,length);
                            usbObservableeee.notify(dd);
//                            byteMerger(dd);
                        }else{
                            Thread.sleep(30);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        usbObservableeee.usbTips("Serial port exception"+e.getMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        usbObservableeee.usbTips("Serial port exception"+e.getMessage());
                    }
                }
                usbObservableeee.close();
                usbObservableeee.usbTips("shut down");
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
     * send data
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


    // 10k byte
    private static byte[] buffByte = new byte[10240];
    private static int buffByteLength = 0;
    /**
     * Byte splicing
     */
    private static void byteMerger(byte[] buff){


    }


    /**
     * subscription
     * @param observer
     */
    public static void SubscribeOn(Observer observer){
        usbObservableeee.add(observer);
    }

    /**
     * Logout
     */
    public static void unRegisteredObserver(Observer observer){
        if (observer!= null){
            usbObservableeee.remove(observer);
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
