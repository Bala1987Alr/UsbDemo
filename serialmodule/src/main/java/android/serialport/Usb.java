package android.serialport;

import java.util.ArrayList;
import java.util.List;


public class Usb implements Observable {
    private List<Observer> personList = new ArrayList<Observer>();//保存观察者的信息
    @Override
    public void add(Observer observer) {
        personList.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        personList.remove(observer);
    }

    @Override
    public void notify(byte[] message) {
        for (Observer observer : personList) {
            observer.resultByte(message);
        }
    }

    @Override
    public void usbTips(String s) {
        for (Observer observer : personList) {
            observer.usbTips(s);
        }
    }

    @Override
    public void close() {
        for (Observer observer : personList) {
            observer.close();
        }
    }
}
