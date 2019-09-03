package android.serialport;

public interface Observable {
    void add(Observer observer);//添加观察者

    void remove(Observer observer);//删除观察者

    void notify(byte[] message);//通知观察者

    void usbTips(String s);
    void close( );
}
