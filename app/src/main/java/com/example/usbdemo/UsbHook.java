package com.example.usbdemo;

import android.util.Log;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *  USB Scan code   UsbHook HID-KBW  The scanner will convert the scanned content into keyboard events
 *   USBScanning case is indistinguishable, including compound keys on the keyboard
 *
 *   How to use
 *  UsbHook usb = new UsbHook();
 *   usb.setUsbQRCListenner(this);// Create listener
 *
 *
 *  in activity
 *  public boolean onKeyDown(int keyCode, KeyEvent event){
 *        UsbHook.keyCodeToNum(keyCode,event);
 *  }
 *
 *  Read the successful callback, the return is a complete two-dimensional code data
 *   @Override
 *     public void usbSucceed(String qrc) {
 *         Log.e(getClass().getName(),"qrc  log = "+qrc);
 *     }
 */
public class UsbHook {
    StringBuffer buffer = new StringBuffer();
    Map<Integer, String> map = new HashMap<>();
    private boolean mCaps = false ;

    public UsbHook(){
        initKey();
    }

    private void initKey() {
        map.clear();
        map.put(29, "a");
        map.put(30, "b");
        map.put(31, "c");
        map.put(32, "d");
        map.put(33, "e");
        map.put(34, "f");
        map.put(35, "g");
        map.put(36, "h");
        map.put(37, "i");
        map.put(38, "g");
        map.put(39, "k");
        map.put(40, "l");
        map.put(41, "m");
        map.put(42, "n");
        map.put(43, "0");
        map.put(44, "p");
        map.put(45, "q");
        map.put(46, "r");
        map.put(47, "s");
        map.put(48, "t");
        map.put(49, "u");
        map.put(50, "v");
        map.put(51, "w");
        map.put(52, "x");
        map.put(53, "y");
        map.put(54, "z");
    }

    private boolean head = false;
    private boolean end = false;

    private LinkedList<Integer> keycodeArray = new LinkedList<>() ;
    //Determine the head and tail, take the complete keycode collection
    public  void keyCodeToNum(int keycode, KeyEvent keyEvent) {
        Log.e(getClass().getName(),"keycode:"+keycode);
        keyCodeAction(keycode);
        // Find the data
        if (!head&&keycode == 143){
            keycodeArray.clear();
            head = true;
            end = false;
            buffer.delete(0,buffer.length());
            return;
        }




        if (head){
            keycodeArray.add(keycode);
            if (keycodeArray.size()>=3){
                end  = keycodeArray.get(keycodeArray.size() - 1) == 143;
                end &= (keycodeArray.get(keycodeArray.size()-2) == 20);
                end &= (keycodeArray.get(keycodeArray.size()-3) == 66);
            }
            Log.e(getClass().getName(),"end="+end);
            if (end){
                // Find data
                // Remove the last three
                for (int i = 0;i<keycodeArray.size()-3;i++){
                    keyCodeAction(keycodeArray.get(i));
                }
//            // Processing completed Callback interface
                if (usbQRCListenner!= null){
                    usbQRCListenner.usbSucceed(buffer.toString());
                }
                keycodeArray.clear();
                end = false;
                head = false;
            }
        }

    }



    // keyCode deal with
    private void keyCodeAction(int keyCode){
        //Check shift key
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
//            if (keycode == KeyEvent.ACTION_DOWN) {
                //Press the shift key to indicate uppercase
                mCaps = true;
            } else {
                //Release the shift key, which means lowercase
                mCaps = false;
//            }
        }

        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            if (mCaps) {
                buffer.append(map.get(keyCode).toUpperCase());
            } else {
                buffer.append(map.get(keyCode));
            }

        } else if ((keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9)) {
            buffer.append(keyCode - KeyEvent.KEYCODE_0);
        } else {
            //Do not handle special symbols temporarily

//                switch (keyCode){
//                    case 7:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 8:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 9:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 10:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 11:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 12:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 13:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 14:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 15:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 16:
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//
//                    case 55://"," or<
//                        if (mCaps) {
//                            buffer.append("<");
//                        } else {
//                            buffer.append(",");
//                        }
//                        break;
//                    case 56://"." or >
//                        if (mCaps) {
//                            buffer.append(">");
//                        } else {
//                            buffer.append(".");
//                        }
//                        break;
//                    case 66:// Carriage return
//                        break;
//                    case 69://"_" or "-"
//                        if (mCaps) {
//                            buffer.append("_");
//                        } else {
//                            buffer.append("-");
//                        }
//                        break;
//                    case 70://"+" or "="
//                        if (mCaps) {
//                            buffer.append("+");
//                        } else {
//                            buffer.append("=");
//                        }
//                        break;
//                    case 71://"{" or [
//                        if (mCaps) {
//                            buffer.append("{");
//                        } else {
//                            buffer.append("[");
//                        }
//                        break;
//                    case 72://"}" or ]
//                        if (mCaps) {
//                            buffer.append("}");
//                        } else {
//                            buffer.append("]");
//                        }
//                        break;
//                    case 73://"|" or \
//                        if (mCaps) {
//                            buffer.append("|");
//                        } else {
//                            buffer.append("\\");
//                        }
//                        break;
//                    case 74:// ";" or :
//                        if (mCaps) {
//                            buffer.append(":");
//                        } else {
//                            buffer.append(";");
//                        }
//                        break;
//                    case 75:// """ or " ' "
//                        if (mCaps) {
//                            buffer.append("\"");
//                        } else {
//                            buffer.append("\'");
//                        }
//                        break;
//                    case 76:// ? or /
//                        if (mCaps) {
//                            buffer.append("?");
//                        } else {
//                            buffer.append("/");
//                        }
//                        break;
//
//                }
        }
    }

    private UsbQRCListenner usbQRCListenner = null;
    public interface UsbQRCListenner{
        void usbSucceed(String qrc);
    }

    public void setUsbQRCListenner(UsbQRCListenner usbQRCListenner){
        this.usbQRCListenner = usbQRCListenner;
    }
}
