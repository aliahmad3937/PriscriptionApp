package com.codecoy.prescriptionapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codecoy.prescriptionapp.R;
import com.codecoy.prescriptionapp.utils.Singleton;
import com.codecoy.prescriptionapp.adapters.PrescriberDetailAdapter;
import com.codecoy.prescriptionapp.models.DetailResponse;
import com.codecoy.prescriptionapp.models.Prescriber;
import com.codecoy.prescriptionapp.models.User;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriberDetailActivity extends AppCompatActivity {
    private User user;
    private ImageView back;
    private TextView name , tvTotal , tvRefill , tvNew , tvNothing;;
    private ProgressBar pb;
   private TextView filter;
    private RecyclerView recyclerView;
    private ArrayList<Prescriber> list = new ArrayList<>();
    private PrescriberDetailAdapter adapter;
    private  Singleton singleton;
    private Integer total =0 , refill =0 , new_ =0;
    private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber_detail);
//        changeStatusBarColor("#FFFFFFFF");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(PrescriberDetailActivity.this, R.color.white));// set status background white

        initViews();
        back.setOnClickListener((v) -> {
            finish();
        });

        filter.setOnClickListener((v) -> {
            filterDialogeUser();
        });

        if (singleton.isFiltered()) {
            singleton.setFiltered(false);
            name.setText(getIntent().getStringExtra("name"));
            refill = getIntent().getIntExtra("refill",0);
            new_ = getIntent().getIntExtra("new",0);
            total = refill + new_ ;

            tvTotal.setText("Total :" + total);
            tvRefill.setText("Refill :" + refill);
            tvNew.setText("New :" + new_);


            if(singleton.getListPriscriberDetail().isEmpty()){

                tvNothing.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                setRecycler(singleton.getListPriscriberDetail());
            }else{
                setRecycler(singleton.getListPriscriberDetail());
                tvNothing.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }



        } else {
            user = (User) getIntent().getSerializableExtra("user");
            name.setText(user.getFullname());
            setRecycler(this.list);
            new AsyncClass().execute();

        }


    }

    private void setRecycler(ArrayList<Prescriber> listPriscriberDetail) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriberDetailAdapter(listPriscriberDetail, this, recyclerView, tvNothing);
        recyclerView.setAdapter(adapter);
    }


    private void initViews() {
        back = findViewById(R.id.back);
        name = findViewById(R.id.rv_prescriber_name);
        recyclerView = findViewById(R.id.rv_detail);
        pb = findViewById(R.id.progressBar);
        tvTotal = findViewById(R.id.tv_total);
        tvRefill = findViewById(R.id.tv_refilll);
        tvNew = findViewById(R.id.tv_new);
        filter = findViewById(R.id.btn_filter_user);
        tvNothing = findViewById(R.id.tv_nothing);


        pd = new ProgressDialog(this);
        pd.setMessage("please wait...");

        singleton = Singleton.getInstance();
    }


    class AsyncClass extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //   pb.setVisibility(View.GONE);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            runOnUiThread(() -> {
                pb.setVisibility(View.VISIBLE);
            });
            singleton.apiInterface.getPrescriberDetail(user.getFullname()).enqueue(new Callback<DetailResponse>() {
                @Override
                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        refill += response.body().getRefill();
                        new_ += response.body().getNew();
                        total = refill + new_ ;

                        tvTotal.setText("Total :" + total);
                        tvRefill.setText("Refill :" + refill);
                        tvNew.setText("New :" + new_);
                        adapter.updateData(response.body().getData());
                        pb.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<DetailResponse> call, Throwable t) {

                }
            });


            return null;
        }
    }


    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }




    private void filterDialogeUser() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View DialogView = factory.inflate(R.layout.filter_dialog_user, null);
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(DialogView);
        dialog.setCancelable(true);


       TextView uName = DialogView.findViewById(R.id.name);
       TextView startDate = DialogView.findViewById(R.id.start_date);
       TextView endDate = DialogView.findViewById(R.id.end_date);
       TextView btnSort = DialogView.findViewById(R.id.tv_get_result);

       uName.setText(name.getText().toString());

        startDate.setOnClickListener(v -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                @Override
                public void onDateChoose(int year, int month, int day) {
                    String day1 = String.valueOf(day);
                    String month1 = String.valueOf(month);

                    day1 = day1.length() == 1 ? "0" + day1 : day1;
                    month1 = month1.length() == 1 ? "0" + month1 : month1;
                    startDate.setText(day1 + "-" + month1 + "-" + year);
                }
            });
            datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
        });

        endDate.setOnClickListener(v -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                @Override
                public void onDateChoose(int year, int month, int day) {
                    String day1 = String.valueOf(day);
                    String month1 = String.valueOf(month);

                    day1 = day1.length() == 1 ? "0" + day1 : day1;
                    month1 = month1.length() == 1 ? "0" + month1 : month1;
                    endDate.setText(day1 + "-" + month1 + "-" + year);
                }
            });
            datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
        });


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic

                if (uName.getText().toString().isEmpty()) {
                    Toast.makeText(PrescriberDetailActivity.this, "Select Prescriber Name", Toast.LENGTH_SHORT).show();
                }

                if (startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
                    Toast.makeText(PrescriberDetailActivity.this, "Enter Start/End Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();

                new AsyncClassFilter().execute(uName.getText().toString(),startDate.getText().toString(),endDate.getText().toString());
            }
        });

       dialog.show();
    }

    class AsyncClassFilter extends AsyncTask<String, String, String> {




        @Override
        protected void onPreExecute() {
            // show dialog


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //   pb.setVisibility(View.GONE);

            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            runOnUiThread(() -> {
                pd.show();
                recyclerView.setVisibility(View.GONE);
            });

                singleton.apiInterface.filterUser(params[0], params[1], params[2]).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus()) {
                              refill =0;
                              new_ = 0;
                              total = 0;

                               refill += response.body().getRefill();
                               new_ += response.body().getNew();
                               total = refill + new_;

                               tvTotal.setText("Total :" + total);
                               tvRefill.setText("Refill :" + refill);
                               tvNew.setText("New :" + new_);
                               adapter.updateData(response.body().getData());

                            recyclerView.setVisibility(View.VISIBLE);

                            pd.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                      pd.dismiss();
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });



            return null;
        }
    }




}