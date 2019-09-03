package com.example.usbdemo;

import android.os.Bundle;
import android.serialport.UsbObservable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chice.scangun.ScanGun;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private EditText qr_address;
    private EditText qr_btl;
    TextView qr_content;
    CheckBox qr_save;
    TextView qr_clear;
    Button qr_open,qr_close;
//    UsbObservable UsbObservable = new UsbObservable();
    /**
     * 补光灯开关  1开 0关
     * @param value
     */
    private CheckBox checkbox_buguangdeng;
    ScanGun scanGun;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        usbSerialTest();
        FillLightTest();
    }

    private boolean  isSave =false;
    private void initView(){


        // 串口测试UI
        qr_address = findViewById(R.id.qr_address);
        qr_save = findViewById(R.id.qr_save);
        qr_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSave = isChecked;

            }
        });
        qr_btl = findViewById(R.id.qr_btl);
        qr_clear = findViewById(R.id.qr_clear);
        qr_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsbObservable.clear();
                qr_content.setText("");
            }
        });
        qr_content = findViewById(R.id.qr_content);
        qr_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        qr_open = findViewById(R.id.qr_open);
        qr_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(qr_address.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请正确填写串口地址",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(qr_btl.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请正确填写波特率",Toast.LENGTH_SHORT).show();
                    return;
                }
                UsbObservable.open(qr_address.getText().toString(),Integer.valueOf(qr_btl.getText().toString()));
            }
        });
        qr_close = findViewById(R.id.qr_close);
        qr_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsbObservable.close();
            }
        });


    }
    String msgNew  ;
    /**
     * usb串口测试
     */
    private void usbSerialTest(){
        UsbObservable.SubscribeOn(new android.serialport.Observer() {
            @Override
            public void resultByte(byte[] buff) {
                String s = null;
                try {
                    s = new String(buff,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (isSave){
                    UsbObservable.writeFile(s);
                }
                msgNew  = s+"\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n";

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qr_content.post(new Runnable() {
                            @Override
                            public void run() {
                                qr_content.append(msgNew);
                                int scrollAmount = qr_content.getLayout().getLineTop(qr_content.getLineCount()) - qr_content.getHeight();
                                if (scrollAmount > 0)
                                    qr_content.scrollTo(0, scrollAmount);
                                else
                                    qr_content.scrollTo(0, 0);
                            }
                        });
                    }
                });
            }

            @Override
            public void usbTips(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void close() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"关闭了",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    /**
     * 补光灯测试
     */
    private void FillLightTest(){
        checkbox_buguangdeng = findViewById(R.id.checkbox_buguangdeng);
        checkbox_buguangdeng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        FillLight.setValue(FillLight.ON);
                    }else {
                        FillLight.setValue(FillLight.OFF);
                    }
            }
        });
    }


    /**
     * sub hook 通讯测试
     */
    private void USBHookTest(){
        scanGun = new ScanGun(new ScanGun.ScanGunCallBack() {
            @Override
            public void onScanFinish(String s) {
                Log.e(getClass().getName(),s);
            }
        });
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode <= 6) {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UsbObservable.unRegisteredObserver();
    }
}