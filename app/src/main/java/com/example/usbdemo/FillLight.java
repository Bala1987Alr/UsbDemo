package com.example.usbdemo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FillLight {
    public static final int ON = 1;
    public static final int OFF = 0;

    public static void setValue(int value) {

        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("/sys/class/gpio/gpio6/value", false); //t
            out = new BufferedWriter(fstream);
            out.write(Integer.toString(value));
            out.close();
        } catch (IOException e) {

        }
    }
}
