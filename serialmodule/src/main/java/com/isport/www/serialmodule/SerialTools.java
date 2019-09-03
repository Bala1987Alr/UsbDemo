package com.isport.www.serialmodule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Handler;
import android.serialport.SerialPort;
import android.util.Log;

public class SerialTools extends Thread{
	protected FileInputStream fileInputStream = null;
	protected FileOutputStream fileOutputStream = null;
	private SerialPort serialPort = null;
	private boolean SERIAL_CONNECT_TAG = true;
	private boolean SERIAL_RUN_TAG = true;
	private String address;
	private int baudrate;
	private BufferedInputStream byIn = null;
	private byte[] buffer = null;
	private Handler handler = null;

	public SerialTools() {

	}

	public void setHandler(Handler handler){
		this.handler = handler;
	}

	public SerialTools(String  Address,int baudrate) {
		this.address = Address;
		this.baudrate = baudrate;
	}
	public void setSerialTools(String  Address,int baudrate) {
		this.address = Address;
		this.baudrate = baudrate;
	}

	StringBuffer stringBuffer = new StringBuffer();
	@Override
	public void run() {
		super.run();
		SERIAL_CONNECT_TAG = true;
		SERIAL_RUN_TAG = true;

		stringBuffer.delete(0, stringBuffer.length());
//		while(SERIAL_CONNECT_TAG){
//			if (createSerialPort(address, baudrate)) {
//				LogUtils.e( "串口打开成功！");
//				stringBuffer .append( "串口打开成功！");
//				handler.obtainMessage(0x01, stringBuffer.toString()).sendToTarget();
//				SERIAL_CONNECT_TAG = false;
//			}else {
//				LogUtils.e( "串口打开失败！");
//				stringBuffer .append( "串口打开失败！");
//				handler.obtainMessage(0x01, stringBuffer.toString()).sendToTarget();
//				SERIAL_CONNECT_TAG = false;
//				try {
//					if (fileInputStream != null) {
//						fileInputStream.close();
//					}
//					if (fileOutputStream != null) {
//						fileOutputStream.close();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}



		if (fileInputStream != null&&fileOutputStream!= null) {
			if (buffer == null) {
				buffer	= new byte[1024];
			}
			int length = 0;
			byIn = new BufferedInputStream(fileInputStream);
			while (SERIAL_RUN_TAG) {
				try {
					if (byIn.available()>0)
					{
						length = byIn.read(buffer);
						// 读取数据
						for (int i = 0; i <length; i++) {
							CMD += " ";
							CMD +=(buffer[i]&0xff);
							LogUtils.e( "接收到命令："+CMD);
							stringBuffer .append(  "接收到命令："+CMD);
							handler.obtainMessage(0x01,stringBuffer.toString()).sendToTarget();
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Log.e(getClass().getName(), "-");
			}
		}
		LogUtils.e( "通讯线程关闭");
		stringBuffer .append(  "通讯线程关闭");
		handler.obtainMessage(0x01, stringBuffer.toString()).sendToTarget();
	}


	String CMD = "";
	protected void sendCmd(byte[] byteArray){
		if (fileOutputStream != null ) {
			try {
				CMD = "";
				for (int i = 0; i < byteArray.length; i++) {
					CMD += " ";
					CMD +=(byteArray[i]&0xff);
					LogUtils.e( "发送命令："+CMD);
					stringBuffer .append(  "发送命令："+CMD);
					handler.obtainMessage(0x01, stringBuffer.toString()).sendToTarget();
				}
				fileOutputStream.write(byteArray);
				fileOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			LogUtils.e( "-发送命令失败 串口未打开-");
			stringBuffer .append(  "-发送命令失败 串口未打开");
			handler.obtainMessage(0x01, stringBuffer.toString()).sendToTarget();
		}
	}


	// 连接串口设备
//	private boolean createSerialPort(String address,int baudrate ){
//		try {
//			if (!address.equals("")) {
//				serialPort = new SerialPort(new File(address), baudrate);
//			}
//			if (serialPort == null) {
//				return false;
//			}
//			fileInputStream = (FileInputStream) serialPort.getInputStream();
//			fileOutputStream = (FileOutputStream) serialPort.getOutputStream();
//			return true;
//		}catch (SecurityException e) {
//			e.printStackTrace();
//			return false;
//		}catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}


//	/**
//	 * 关闭串口
//	 */
//	public void CloseDevice(){
//		if (serialPort!= null) {
//			serialPort.close();
//			LogUtils.e( "串口关闭！");
//			stringBuffer .append( "串口关闭！");
//			handler.obtainMessage(0x01,  stringBuffer.toString()).sendToTarget();
//		}
//	}




	
	
}
