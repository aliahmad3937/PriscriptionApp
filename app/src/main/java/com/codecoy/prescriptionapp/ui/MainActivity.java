package com.codecoy.prescriptionapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codecoy.prescriptionapp.R;
import com.codecoy.prescriptionapp.utils.Singleton;
import com.codecoy.prescriptionapp.adapters.PrescriberAdapter;
import com.codecoy.prescriptionapp.adapters.SpinnerAdapter;
import com.codecoy.prescriptionapp.models.DetailResponse;
import com.codecoy.prescriptionapp.models.LoginResponse;
import com.codecoy.prescriptionapp.models.User;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ImageView filter_;
   private ImageView logout;
   private CardView cvFilter;
   private Singleton singleton;
   private RecyclerView rvPrescriber;
   private EditText searchdata;
   private TextView tvNothing;
   private PrescriberAdapter adapter;
   private SpinnerAdapter spinnerAdapter;
   private TextView   tvTotal , tvRefill , tvNew ,filterPrescribers ;

   private TextView btnSort;
   private String spinnerName = "";


    private ProgressDialog pd;

    private Integer total =0 , refill =0 , new_ =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        singleton.setCurrentUserID(getIntent().getStringExtra("CUID"));

        setUpClickListeners();
        setUpRecycler();
        textWatcher();
        new AsyncClass().execute("");

    }

    private void setUpClickListeners() {
        filter_.setOnClickListener((v) -> {
            filterDialoge();
        });


        logout.setOnClickListener((v) -> {
            logout();
        });

        filterPrescribers.setOnClickListener((v) -> {
            filterPrescribers();
        });
    }

    public void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Confirm Logout..!!!");
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_logout_24);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Do you want to logout?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // write all the data entered by the user in SharedPreference and apply
                SharedPreferences sp = getSharedPreferences("PrescriptionApp", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("CUID", "");
                edit.apply();
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
                finishAffinity();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //  Toast.makeText(MainActivity.this,"You clicked over No",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //       Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void setUpRecycler() {
        rvPrescriber.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriberAdapter(singleton.getListPriscriber(), this, rvPrescriber, tvNothing);
        rvPrescriber.setAdapter(adapter);
    }

    private void textWatcher() {
        searchdata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //    Adapter.updateMedicineList(medicines);
                //  if(s.length()>0)
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterPrescribers() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View DialogView = factory.inflate(R.layout.filter_dialog, null);
        AlertDialog dialog1 = new AlertDialog.Builder(this).create();
        dialog1.setView(DialogView);
        dialog1.setCancelable(true);


       // spinner = DialogView.findViewById(R.id.simpleSpinner);
       LinearLayout layoutSpinner = DialogView.findViewById(R.id.layout_spinner);
       TextView startDate = DialogView.findViewById(R.id.start_date);
       TextView endDate = DialogView.findViewById(R.id.end_date);
       TextView btnSort = DialogView.findViewById(R.id.tv_get_result);
//       startDate.setText("");
//       endDate.setText("");


        layoutSpinner.setVisibility(View.GONE);

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


                if (startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Start/End Date", Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog1.dismiss();

                new AsyncFilterPrescriberByDate(startDate.getText().toString(),endDate.getText().toString()).execute("");
            }
        });

      dialog1.show();
    }




    private void filterDialoge() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View DialogView = factory.inflate(R.layout.filter_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(DialogView);
        dialog.setCancelable(true);


        Spinner spinner = DialogView.findViewById(R.id.simpleSpinner);
        TextView startDate = DialogView.findViewById(R.id.start_date);
        TextView endDate = DialogView.findViewById(R.id.end_date);
        TextView btnSort = DialogView.findViewById(R.id.tv_get_result);

        // we pass our item list and context to our Adapter.
        spinnerAdapter = new SpinnerAdapter(this, singleton.getListPriscriber());
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {

                        // It returns the clicked item.
                        User clickedItem = (User)
                                parent.getItemAtPosition(position);
                        spinnerName = clickedItem.getFullname();
                        //  Toast.makeText(MainActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

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

                if (spinnerName.isEmpty() || spinnerName.equals("")) {
                    Toast.makeText(MainActivity.this, "Select Prescriber Name", Toast.LENGTH_SHORT).show();
                }

                if (startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Start/End Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                singleton.setFiltered(true);
                dialog.dismiss();

                new AsyncClass().execute(spinnerName,startDate.getText().toString(),endDate.getText().toString());
            }
        });

        dialog.show();
    }

    private void initViews() {
        rvPrescriber = findViewById(R.id.rv_prescriber);
        filter_ = findViewById(R.id.iv_filter);
        cvFilter = findViewById(R.id.cv_filter);
        searchdata = findViewById(R.id.et_search);
        tvNothing = findViewById(R.id.tv_list);
        logout = findViewById(R.id.iv_logout);
        tvTotal = findViewById(R.id.tv_total);
        tvRefill = findViewById(R.id.tv_refilll);
        tvNew = findViewById(R.id.tv_new);
        filterPrescribers = findViewById(R.id.btn_filter_user);


        pd = new ProgressDialog(this);
        pd.setMessage("please wait...");

        singleton = Singleton.getInstance();
    }


    class AsyncClass extends AsyncTask<String, String, String> {

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
            });
            if (singleton.isFiltered()) {
                singleton.apiInterface.filterUser(params[0],params[1],params[2]).enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus()) {

                            singleton.setListPriscriberDetail(response.body().getData());
                            pd.dismiss();
                            startActivity(new Intent(MainActivity.this, PrescriberDetailActivity.class)
                                    .putExtra("name", spinnerName)
                                    .putExtra("refill", response.body().getRefill())
                                    .putExtra("new", response.body().getNew())
                            );
                            spinnerName = "";
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        pd.dismiss();
                    }
                });
            } else {

                singleton.apiInterface.getPrescriberList(singleton.getCurrentUserID()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus()) {
                            try {
                                adapter.updateData(response.body().getPrescriberList());
                                singleton.setListPriscriber(response.body().getPrescriberList());
                                pd.dismiss();

                                for (User user : response.body().getPrescriberList()) {
                                    total += user.getTotal();
                                    refill += user.getRefill();
                                    new_ += user.getNew();
                                }
                                tvTotal.setText("Total :" + total);
                                tvRefill.setText("Refill :" + refill);
                                tvNew.setText("New :" + new_);
                            }catch(Exception e){}
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                     tvNothing.setVisibility(View.VISIBLE);
//                     tvNothing.setText("Network Failure");
                        Toast.makeText(MainActivity.this, "Network failure!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

            }

            return null;
        }
    }


    class AsyncFilterPrescriberByDate extends AsyncTask<String, String, String> {
        private String sd , ed;
        public AsyncFilterPrescriberByDate(String strt, String end) {
            sd = strt;
            ed = end;
        }

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
        protected String doInBackground(String... strings) {

            runOnUiThread(() -> {
                pd.show();
            });


                singleton.apiInterface.getPrescriberList(singleton.getCurrentUserID(),sd,ed).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body().getStatus()) {
                            total = 0;
                            refill = 0;
                            new_ = 0;
                            try {
                                adapter.updateData(response.body().getPrescriberList());
                             //   singleton.setListPriscriber(response.body().getPrescriberList());
                                pd.dismiss();

                                for (User user : response.body().getPrescriberList()) {
                                    total += user.getTotal();
                                    refill += user.getRefill();
                                    new_ += user.getNew();
                                }
                                tvTotal.setText("Total :" + total);
                                tvRefill.setText("Refill :" + refill);
                                tvNew.setText("New :" + new_);
                            }catch(Exception e){}
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                     tvNothing.setVisibility(View.VISIBLE);
//                     tvNothing.setText("Network Failure");
                        Toast.makeText(MainActivity.this, "Network failure!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });


            return null;
        }
    }
}