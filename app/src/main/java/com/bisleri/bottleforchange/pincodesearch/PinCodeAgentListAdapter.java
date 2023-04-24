package com.bisleri.bottleforchange.pincodesearch;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bisleri.bottleforchange.R;

import java.util.ArrayList;

public class PinCodeAgentListAdapter extends RecyclerView.Adapter<PinCodeAgentListAdapter.ViewHolder> {

    private ArrayList<AgentDetails> adapterList = new ArrayList<>();
    private int selectedAgent = -1;

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pincode_agent_list, parent, false);
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
            if (!adapterList.get(position).alternate_mobile_number.isEmpty()) {
                holder.tvAgentAltPhone.setVisibility(View.VISIBLE);
                holder.tvAgentAltPhone.setText(adapterList.get(position).alternate_mobile_number);
            } else {
                holder.tvAgentAltPhone.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void setAdapterList( ArrayList<AgentDetails> adapterList) {
        if (adapterList != null && adapterList.size() > 0) {
            this.adapterList = adapterList;
            notifyDataSetChanged();
        }
    }

    public AgentDetails getSelectedAgent() {
        if (selectedAgent != -1) {
            return adapterList.get(selectedAgent);
        }
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAgentName;
        TextView tvAgentAddress;
        TextView tvAgentPhone;
        TextView tvAgentAltPhone;
        ImageView ivAgent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAgentName = itemView.findViewById(R.id.tv_agent_name);
            tvAgentAddress = itemView.findViewById(R.id.tv_agent_address);
            tvAgentPhone = itemView.findViewById(R.id.tv_agent_phone);
            tvAgentAltPhone = itemView.findViewById(R.id.tv_agent_alt_phone);
            ivAgent = itemView.findViewById(R.id.iv_agent);
        }
    }
}
