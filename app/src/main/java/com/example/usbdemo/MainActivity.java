package com.example.usbdemo;

import android.os.Bundle;
import android.serialport.Observer;
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
import com.ibmwebapi.GetDataService;
import com.ibmwebapi.RetrofitClientInstance;
import com.ibmwebapi.data.PostTemperatureResponse;
import com.ibmwebapi.data.UserData;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.QRJoint;

public class MainActivity extends AppCompatActivity {

    private EditText qr_address;
    private EditText qr_btl;
    private EditText qr_send_content;
    private EditText nric,temp;
    String declaration_ID,entry;
    TextView qr_content;
    CheckBox qr_save;
    CheckBox qr_save_hex;
    CheckBox qr_save_byte;
    CheckBox qr_auto_send;
    CheckBox qr_save_onoff;
    CheckBox qr_auto_one;
    CheckBox qr_auto_two;
    CheckBox qr_auto_three;
    TextView qr_clear;
    Button qr_open,qr_close;
    /**
     * Fill light switch 1 on 0 off
     * @param value
     */
    private CheckBox checkbox_buguangdeng;
    ScanGun scanGun;
    //for date
    String pattern = "dd-MM-yyyy";
    String pattern_mm = "MM-dd-yyyy hh:mm:ss";
    SimpleDateFormat simpleDateFormat;
    private GetDataService service;
    private static final String TAG = "MainActivity";
    Date current_date ;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_date = new Date();
        initView();

        usbSerialTest();
        FillLightTest();

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }

    private boolean  isSave =false;
    private boolean  auto =false;
    private boolean UTF8= false;
    private boolean HEX= false;
    private boolean BYTE= false;
    private void initView(){

        qr_send_content = findViewById(R.id.qr_send_content);
        //Serial test UI
        qr_address = findViewById(R.id.qr_address);
        qr_save = findViewById(R.id.qr_save);
        qr_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UTF8 = isChecked;
                UsbObservable.writeData("asdfasfdasfdasdf".getBytes());
            }
        });
        qr_save_hex = findViewById(R.id.qr_save_hex);
        qr_save_hex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HEX = isChecked;
            }
        });

        qr_save_byte = findViewById(R.id.qr_save_byte);
        qr_save_byte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BYTE = isChecked;

            }
        });

        qr_save_onoff = findViewById(R.id.qr_save_onoff);
        qr_save_onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSave = isChecked;
            }
        });

        qr_auto_send = findViewById(R.id.qr_auto_send);
        qr_auto_send.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                auto = isChecked;
//                sendData();
            }
        });

        qr_auto_one = findViewById(R.id.qr_auto_one);
        qr_auto_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) se = 500;else se = 0;
            }
        });

        qr_auto_two = findViewById(R.id.qr_auto_two);
        qr_auto_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                se = 1000;
                else
                se = 0;
            }
        });

        qr_auto_three = findViewById(R.id.qr_auto_three);
        qr_auto_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    se = 2000;
                }
                else {
                    se = 0;
                }
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
                    Toast.makeText(getApplicationContext(),"\n" + "Please fill in the serial port address correctly",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(qr_btl.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the correct baud rate",Toast.LENGTH_SHORT).show();
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

    private int se = 1000;
    private void sendData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(auto){
                    if (!TextUtils.isEmpty(qr_send_content.getText().toString())){
                        UsbObservable.writeData(qr_send_content.getText().toString().getBytes());
                    }
                    try {
                        if (se == 0){
                            se = 500;
                        }
                        Thread.sleep(se);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    String msgNew  ;
    private Observer observer = null;
    /**
     * usbSerial test
     */
    private void usbSerialTest(){
        observer = new android.serialport.Observer() {
            @Override
            public void resultByte(byte[] buff) {
                // Get the complete qrcode
                String qrcode = QRJoint.getAlipayQRCode(buff, buff.length);
                if (TextUtils.isEmpty(qrcode)) {
                    Log.e("QRJoint","YLLog:"+"qrcode = null");
                    return;
                }else
                {
                    simpleDateFormat = new SimpleDateFormat(pattern);
                    date = simpleDateFormat.format(current_date);
                    //instead of nric.getText().toString() later you can pass qrcode
                    Call<List<UserData>> call = service.getUserData("",nric.getText().toString(),"",
                            date,date,"",
                            "1","1","","false");

                    call.enqueue(new Callback<List<UserData>>() {
                        @Override
                        public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                            Log.d(TAG, "onResponse: " +response.body().get(0).getMobileNo());
                            UserData userData= response.body().get(0);
                            declaration_ID = userData.getDeclarationID();
                            entry=userData.getEntry();
                            sendTemp();
                        }
                        @Override
                        public void onFailure(Call<List<UserData>> call, Throwable t) {
                            Log.e(TAG, "onFailure: "+ t.toString());
                        }
                    });
                }



                Log.e(getClass().getName(),"二Dimension code:"+qrcode);
                msgNew  = qrcode+"\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n";

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
                        Toast.makeText(getApplicationContext(),"closed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        UsbObservable.SubscribeOn(observer);
    }


    /**
     * Fill light test
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
     * sub hook Communication test
     */
    private void USBHookTest(){
        scanGun = new ScanGun(new ScanGun.ScanGunCallBack() {
            @Override
            public void onScanFinish(String s) {
                Log.e(getClass().getName(),s);
            }
        });
    }


    public void sendTemp()
    {
        simpleDateFormat = new SimpleDateFormat(pattern_mm);
        String date = simpleDateFormat.format(current_date);
        TreeMap<String,String> postData = new TreeMap<>();
        postData.put("DeclarationID",declaration_ID);
        postData.put("Temperature","37.7"); // please pass temperature here
        postData.put("ManualTemperature","");
        postData.put("Ambient","");
        postData.put("LocationAPIKey","");
        postData.put("ScannedDateTime",date);
        if(entry.equalsIgnoreCase("N"))
        {
            postData.put("ActionResult", "Please seek staff assistance - Entry Denied.Temperature "+temp.getText()+" within acceptable range");
        }
        else if (Integer.valueOf(temp.getText().toString().trim())> 37.5) {

            postData.put("ActionResult", "Please seek staff assistance - Entry Denied. High Temperature "+temp.getText()+" detected");
        }
        else
        {
            postData.put("ActionResult", "Temperature within acceptable range.Please proceed");
        }

        Call<PostTemperatureResponse> postTemperatureResponseCall = service.postTemperature(postData);
        postTemperatureResponseCall.enqueue(new Callback<PostTemperatureResponse>() {
            @Override
            public void onResponse(Call<PostTemperatureResponse> call, Response<PostTemperatureResponse> response) {
                Log.d(TAG, "onResponse: " +response.body());
                PostTemperatureResponse postTemperatureResponse = response.body();

                //Please display this message in screen
                Log.d(TAG, "onResponse: "+ postTemperatureResponse.getActionResult());
            }
            @Override
            public void onFailure(Call<PostTemperatureResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString());
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
        UsbObservable.unRegisteredObserver(observer);
    }
}
