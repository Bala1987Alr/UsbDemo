# UsbDemo

#### Introduction
usb to serial port test tool, usbhook test tools

####USB serial port package 
Call method
rely serialmodule

1. initialization (in the application or where the serial port needs to be opened)   
UsbObservable.open (serial port address, baud rate);  

2.Receive serial data  
UsbObservable.SubscribeOn(new android.serialport.Observer() {  
              @Override  
              public void resultByte(byte[] buff) {  
                // The complete code array after each scan  
              }  
              @Override  
              public void usbTips(final String s) {  
                 // The serial port switch is abnormal and other information prompts  
              }  
              @Override  
              public void close() {  
                  // Callback after the serial port is closed 
              }  
          });  
          
 3.Called after the program exits  
 UsbObservable.unRegisteredObserver();  

