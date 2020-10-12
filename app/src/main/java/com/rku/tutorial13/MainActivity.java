package com.rku.tutorial13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtCall,edtSms;
    Button btnCall,btnMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCall = findViewById(R.id.edtCall);
        edtSms = findViewById(R.id.edtMsg);
        btnCall = findViewById(R.id.btnCall);
        btnMsg = findViewById(R.id.btnSendSms);
    }

    public void Call(View view) {

        if(isPermissionGranted(1)){
            call_action();
        }
    }

    private void call_action() {
        String number = edtCall.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }

    private boolean isPermissionGranted(int i) {

        switch (i){
            case 1:
                if(Build.VERSION.SDK_INT>=23){
                    if(checkSelfPermission(Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED){
                        return true;
                    }
                    else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
                        return false;
                    }
                }
                else {
                    return true;
                }
            case 2:
                if(Build.VERSION.SDK_INT>=23){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED){
                        return true;
                    }
                    else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
                        return false;
                    }
                }
                else {
                    return true;
                }
            default:
                return  false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_LONG).show();
                    call_action();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
            break;

        }
    }

    private void sms_action() {
        String number = edtCall.getText().toString();
        String msg = edtSms.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number,null,msg,null,null);
        Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_LONG).show();
    }

    public void SMS(View view) {
        if(isPermissionGranted(2)){
            sms_action();
        }
    }
}