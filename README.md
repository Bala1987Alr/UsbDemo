# UsbDemo

#### 介绍
usb转串口测试工具,usbhook测试工具
####USB串口封装  
调用方式  
##1.初始化 (在application 或 需要开启串口的地方)   
UsbObservable.open(串口地址,波特率);  

##2.接收串口数据  
UsbObservable.SubscribeOn(new android.serialport.Observer() {  
              @Override  
              public void resultByte(byte[] buff) {  
                // 每次扫码后的完整code数组  
              }  
              @Override  
              public void usbTips(final String s) {  
                 // 串口开关异常等信息提示  
              }  
              @Override  
              public void close() {  
                  // 串口关闭后回调  
              }  
          });  
          
 ##3.程序退出后调用  
 UsbObservable.unRegisteredObserver();  

