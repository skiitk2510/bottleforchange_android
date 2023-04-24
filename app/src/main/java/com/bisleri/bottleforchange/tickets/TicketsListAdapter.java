package com.bisleri.bottleforchange.tickets;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.pincodesearch.AgentDetails;

import java.util.ArrayList;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.ViewHolder> {

    private ArrayList<TicketDetails> adapterList = new ArrayList<>();
    private int selectedAgent = -1;

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_list, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedAgent = position;
                    notifyDataSetChanged();
                }
            });
            holder.ivAgent.setSelected(position == selectedAgent);
            holder.tvAgentName.setText(adapterList.get(position).contact_person_name);
            holder.tvAgentAddress.setText(adapterList.get(position).address);
            holder.tvAgentPhone.setText(adapterList.get(position).mobile_number);
            holder.tvDate.setText(adapterList.get(position).date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void setAdapterList( ArrayList<TicketDetails> adapterList) {
        if (adapterList != null && adapterList.size() > 0) {
            this.adapterList = adapterList;
            notifyDataSetChanged();
        }
    }

    public TicketDetails getSelectedAgent() {
        if (selectedAgent != -1) {
            return adapterList.get(selectedAgent);
        }
        return null;
    }

    public void removeSelectedAgent() {
        if(selectedAgent!=-1){
            adapterList.remove(selectedAgent);
            selectedAgent = -1;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAgentName;
        TextView tvAgentAddress;
        TextView tvAgentPhone;
        TextView tvDate;
        ImageView ivAgent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAgentName = itemView.findViewById(R.id.tv_agent_name);
            tvAgentAddress = itemView.findViewById(R.id.tv_agent_address);
            tvAgentPhone = itemView.findViewById(R.id.tv_agent_phone);
            ivAgent = itemView.findViewById(R.id.iv_agent);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
