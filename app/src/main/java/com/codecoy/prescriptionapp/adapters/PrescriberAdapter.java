package com.codecoy.prescriptionapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codecoy.prescriptionapp.ui.PrescriberDetailActivity;
import com.codecoy.prescriptionapp.R;
import com.codecoy.prescriptionapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class PrescriberAdapter extends RecyclerView.Adapter<PrescriberAdapter.ViewHolder> implements Filterable {
    private ArrayList<User> list = new ArrayList<>();
    private Context context;
    private RecyclerView rec;
    private TextView tvNothing;

    private List<User> filteredData = new ArrayList<>();
    private List<User> backup;


    public PrescriberAdapter(ArrayList<User> list, Context context, RecyclerView recyclerView, TextView tvNothing) {
        this.list.addAll(list);
        this.backup = new ArrayList<>(list);
        this.context = context;
        this.tvNothing = tvNothing;
        this.rec = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prescriber, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getFullname());

        holder.itemView.setOnClickListener((v) -> {
            context.startActivity(new Intent(context, PrescriberDetailActivity.class).putExtra("user", list.get(position)));
        });

    }

    public void updateData(ArrayList<User> list) {
        this.list.clear();
        this.list.addAll(list);
        this.backup = new ArrayList<>(list);

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
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);


        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    //Anonymous inner class
    Filter filter = new Filter() {
        @Override  //background thread
        protected FilterResults performFiltering(CharSequence keyword) {
            filteredData.clear();

            if (keyword.toString().isEmpty()) {
                // backup all data if list is empty
                filteredData.addAll(backup);

            } else {

                for (User obj : backup) {
                    if (obj.getFullname().toLowerCase().contains(keyword.toString().toLowerCase())) {
                        filteredData.add(obj);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredData;
            return results;
        }

        @Override  //main UI thread
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<User> filteredResult = new ArrayList<>((ArrayList<User>) results.values);
            if (filteredResult.size() > 0) {
                //   Toast.makeText(context, "filter size :"+filteredResult.size(), Toast.LENGTH_SHORT).show();
                rec.setVisibility(View.VISIBLE);
                tvNothing.setVisibility(View.GONE);
                list.clear();
                list.addAll(filteredResult);
                notifyDataSetChanged();
            } else {
                //   Toast.makeText(context, "filter else size :"+filteredResult.size(), Toast.LENGTH_SHORT).show();
                rec.setVisibility(View.GONE);
                tvNothing.setVisibility(View.VISIBLE);
            }
        }
    };

}