package com.codecoy.prescriptionapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codecoy.prescriptionapp.R;
import com.codecoy.prescriptionapp.utils.Singleton;
import com.codecoy.prescriptionapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userPassword;
    private Button btnLogin;
    private ProgressDialog pd;


    public String name ;
    public String password;

    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Singleton singleton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
//        getSupportActionBar().hide();
        initViews();

        btnLogin.setOnClickListener((v) -> {
            if(checkValidation()){
                sendNetworkRequest();
            }else{
                Toast.makeText(this, "Please fill up Empty cells!.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendNetworkRequest() {
        pd.show();
      
        singleton.apiInterface.checkUserStatus(name,password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    pd.dismiss();
                    if(response.body().getStatus()){
                        // write all the data entered by the user in SharedPreference and apply
                        edit.putString("CUID",response.body().getUser_id() );
                        edit.apply();



                       // singleton.setCurrentUserID(response.body().getUser_id());
                      //  singleton.setListPriscriber(response.body().getPrescriberList());
                        Toast.makeText(LogInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this,MainActivity.class).putExtra("CUID",response.body().getUser_id()));
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            pd.dismiss();
                Toast.makeText(LogInActivity.this, "failure "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkValidation() {
        name = userName.getText().toString();
        password = userPassword.getText().toString();
        if(name.isEmpty()){
            userName.setError("please enter your name");
        }
        if(name.isEmpty()){
            userPassword.setError("please enter your password");
        }
        if(name.isEmpty() || password.isEmpty()){
           return false;
        }

        return true;

    }

    private void initViews() {
        userName = findViewById(R.id.et_username);
        userPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.iv_signin);

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
      sp = getSharedPreferences("RCS", MODE_PRIVATE);
        edit = sp.edit();

        singleton = Singleton.getInstance();
        
        pd = new ProgressDialog(this);
        pd.setMessage("please wait...");
        
    }
}