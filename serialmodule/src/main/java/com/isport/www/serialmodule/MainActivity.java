package com.isport.www.serialmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.serialport.SerialPortFinder;
import android.serialport.StringToAscii;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SerialTools serialTools = new SerialTools();
	private SerialPortFinder serialPortFinder = new SerialPortFinder();
	private String[] devices = null;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0x01:
					serialPort_remind.setText((String)msg.obj);
					break;

				default:
					break;
			}
		};
	};
	TextView serialPort_remind = null,send_bt = null;
	EditText sendEdit = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView serialPort = (TextView) findViewById(R.id.serialPort);
		serialPort_remind = (TextView) findViewById(R.id.serialPort_remind);
		send_bt = (TextView) findViewById(R.id.send_bt);
		sendEdit = (EditText) findViewById(R.id.sendEdit);
		serialTools.setHandler(handler);
		serialPort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				serialPort_remind.setText("消息：");
				devices = serialPortFinder.getAllDevices();

				showSingleChoiceDialog();
			}
		});


		send_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 发送命令
				if (serialTools!= null) {
					serialTools.sendCmd(StringToAscii.parseAscii(sendEdit.getText().toString()).getBytes());
				}
			}
		});



	}
	int yourChoice;
	private void showSingleChoiceDialog(){
		yourChoice = -1;
		AlertDialog.Builder singleChoiceDialog =  new AlertDialog.Builder(MainActivity.this);
		singleChoiceDialog.setTitle("请选择串口");
		// 第二个参数是默认选项，此处设置为0
		singleChoiceDialog.setSingleChoiceItems(devices, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						yourChoice = which;
					}
				});
		singleChoiceDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (yourChoice!= -1) {
							String device = devices[yourChoice];
							device = device.substring(0, device.indexOf("("));
							serialTools.setSerialTools("/dev/ttyS1", 115200);
							new Thread(serialTools).start();
						}else {
							Toast.makeText(getApplicationContext(), "请选择端口号", Toast.LENGTH_SHORT).show();
						}

					}
				});
		singleChoiceDialog.show();
	}


}

