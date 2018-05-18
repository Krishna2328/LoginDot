package com.krishna.logindot.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.krishna.logindot.R;
import com.krishna.logindot.utils.AppConstants;
import com.krishna.logindot.utils.AppServices;
import com.krishna.logindot.utils.ServiceCallback;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButtonLogin;
    EditText mEditTextEmail, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButtonLogin = (Button) findViewById(R.id.activity_login_btn_login);
        mEditTextEmail = (EditText) findViewById(R.id.activity_login_et_email);
        mEditTextPassword = (EditText) findViewById(R.id.activity_login_et_password);

        mEditTextEmail.setText(AppConstants.email);
        mEditTextPassword.setText(AppConstants.password);

        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_login_btn_login:
                login();
                break;
            default:
                break;
        }
    }

    public void login(){
        Log.i("method", "login");
        String email = mEditTextEmail.getText().toString();
        String password = getEncryptedPassword(mEditTextPassword.getText().toString());

        if(checkIntenet()){
            Log.e("internet", "connected");
            AppServices.Login(email, password, getApplicationContext(), new ServiceCallback() {
                @Override
                public void onResponse(String response) {
                    Log.e("response ", response);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Network connection failed, please try again", Toast.LENGTH_LONG).show();
        }

    }

    public String getEncryptedPassword(String password){
        String encoded_string = null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                encoded_string = Base64.getEncoder().encodeToString(hash);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encoded_string;
    }

    public boolean checkIntenet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else
            connected = false;
        return connected;
    }
}
