package com.codecoy.prescriptionapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codecoy.prescriptionapp.R;
import com.codecoy.prescriptionapp.models.Prescriber;

import java.util.ArrayList;

public class PrescriberDetailAdapter extends RecyclerView.Adapter<PrescriberDetailAdapter.ViewHolder> {
   private ArrayList<Prescriber> list = new ArrayList<>();
   private Context context;
   private RecyclerView rec;
   private TextView tvNothing;


    public PrescriberDetailAdapter(ArrayList<Prescriber> list, Context context, RecyclerView recyclerView, TextView tvNothing) {
        this.list.addAll(list);
        this.context = context;
        this.tvNothing = tvNothing;
        this.rec = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescriber prescriber = list.get(position);


       holder.refill_renew.setText(prescriber.getRefill());
       holder.rxnumber.setText("Rx Number # "+prescriber.getRx());
       holder.date.setText(prescriber.getDate());
       holder.quantity.setText(prescriber.getQuantity());
       holder.transactionStatus.setText(prescriber.getTransaction());
       holder.rxstatus.setText(prescriber.getStatus());

    }


    public void updateData(ArrayList<Prescriber> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

        if (list.size() == 0 || list.isEmpty()) {
            rec.setVisibility(View.GONE);
            tvNothing.setVisibility(View.VISIBLE);
        } else {
            rec.setVisibility(View.VISIBLE);
            tvNothing.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView location;
      TextView rxnumber;
      TextView date;
      TextView rxstatus;
      TextView quantity;
      TextView transactionStatus;
      TextView refill_renew;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.tv_location);
            rxnumber = itemView.findViewById(R.id.tv_rxnumber);
            date = itemView.findViewById(R.id.tv_date);
            rxstatus = itemView.findViewById(R.id.tv_rxstatus);
            quantity = itemView.findViewById(R.id.tv_quantity);
            transactionStatus = itemView.findViewById(R.id.tv_transaction_status);
            refill_renew = itemView.findViewById(R.id.tv_refill_new);


        }
    }


}