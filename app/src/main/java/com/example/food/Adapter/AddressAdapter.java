package com.example.food.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Models.AddressModel;
import com.example.food.R;

import java.util.List;
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context context;
    private List<AddressModel> addressModelList;
    private SelectedAddress selectedAddress;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel addressModel = addressModelList.get(position);
        holder.address.setText(addressModel.getUserAddress());
        holder.radioButton.setChecked(addressModel.isSelected());

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressModel.setSelected(true);
                selectedAddress.setAddress(addressModel.getUserAddress());

                // Update the list to reflect changes
                for (AddressModel address : addressModelList) {
                    if (address != addressModel) {
                        address.setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
