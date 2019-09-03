package android.serialport;

public interface Observer {
    void resultByte(byte[] buff);
    void usbTips(String s);
    void close( );
}
