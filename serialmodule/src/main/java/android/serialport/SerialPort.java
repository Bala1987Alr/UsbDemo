/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android.serialport;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class SerialPort {

	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */
	private static FileDescriptor mFd;
	private static FileInputStream mFileInputStream;
	private static FileOutputStream mFileOutputStream;
	private static String mSerialPortPath;
	private  String addressd = "dev/ttyXRUSB2";
	private  int  baudrated = 115200;


	/**
	 * Open the serial port
	 */
	public  boolean createSerialPort(String address,int baudrate){
		try {
			creatSerialPort(new File(address),baudrate);
			Log.e(getClass().getName(),"open serial success");
//			LogUtils.e("open serial success");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	/**
	 * Close the serial port
	 */
	public void CloseDevice(){
			close();

	}
	
	private  void creatSerialPort(File device, int baudrate) throws SecurityException, IOException {

		/* Check access permission */
//		if (!device.canRead() || !device.canWrite()) {
//			try {
//				/* Missing read/write permission, trying to chmod the file */
//				Process su;
//				su = Runtime.getRuntime().exec("/system/bin/su");
//				String cmd = "chmod 777 " + device.getAbsolutePath() + "\n"
//						+ "exit\n";
//				/*String cmd = "chmod 777 /dev/s3c_serial0" + "\n"
//				+ "exit\n";*/
//				su.getOutputStream().write(cmd.getBytes());
//				if ((su.waitFor() != 0) || !device.canRead()
//						|| !device.canWrite()) {
//					throw new SecurityException();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new SecurityException();
//			}
//		}
		
		mSerialPortPath = device.getAbsolutePath();
		mFd = open(mSerialPortPath, baudrate,8);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}
		
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
		
	}

	// Getters and setters
	public  InputStream getInputStream() {
		return mFileInputStream;
	}

	public  OutputStream getOutputStream() {
		return mFileOutputStream;
	}
	public  void closeDevice(){
		try {
//			close();
			mFileInputStream.close();
			mFileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// JNI
	private native static FileDescriptor open(String path, int baudrate,int flag);
	private native void close();
	static {
		System.loadLibrary("serial_port");
	}
}
