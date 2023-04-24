package com.bisleri.bottleforchange.submittedplastics;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;

import java.util.ArrayList;

public class SubmitListAdapter extends RecyclerView.Adapter<SubmitListAdapter.ViewHolder> {

    private ArrayList<SubmittedContent> adapterList = new ArrayList<>();
    private boolean isCategoryDashboard;

    public SubmitListAdapter(Context context) {
        isCategoryDashboard = Session.getInstance(context).isCategoryDashboard();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submitted_list, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        try {
            if (isCategoryDashboard) {
                holder.tvName.setText(adapterList.get(position).ticket_to_user_id_name);
            }else {
                holder.tvName.setText(adapterList.get(position).name);
            }
            holder.tvDate.setText(adapterList.get(position).date);
            holder.tvQuantity.setText(adapterList.get(position).total_per_day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void setAdapterList( ArrayList<SubmittedContent> adapterList) {
        if (adapterList != null && adapterList.size() > 0) {
            this.adapterList = adapterList;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvName;
        TextView tvQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvName = itemView.findViewById(R.id.tv_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
