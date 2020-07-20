package android.serialport;

public interface Observable {
    void add(Observer observer);//Add observer

    void remove(Observer observer);//Delete observer

    void notify(byte[] message);//Notify observer

    void usbTips(String s);
    void close( );
}
