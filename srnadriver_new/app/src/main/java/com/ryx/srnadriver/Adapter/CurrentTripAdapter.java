package com.ryx.srnadriver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryx.srnadriver.Model.CurrentTripResponse;
import com.ryx.srnadriver.R;

public class CurrentTripAdapter extends RecyclerView.Adapter<CurrentTripAdapter.ViewHolder> {

    private Context context;
    CurrentTripResponse currentTripResponse;



    public CurrentTripAdapter(Context context,CurrentTripResponse currentTripResponse) {
        this.context = context;
        this.currentTripResponse = currentTripResponse;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.current_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.pickupaddress.setText(currentTripResponse.getData().getTripInfo().get(position).getSourceAddress());
        holder.dropoffaddress.setText(currentTripResponse.getData().getTripInfo().get(position).getSourceAddress());
        holder.acceptBtn.setOnClickListener(v->{

        });



    }




    @Override
    public int getItemCount() {
        return currentTripResponse.getData().getTripInfo().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView pickupaddress,dropoffaddress;
        AppCompatButton acceptBtn;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pickupaddress = itemView.findViewById(R.id.tv_address_name);
            dropoffaddress = itemView.findViewById(R.id.tv_address_name_drop);
            acceptBtn = itemView.findViewById(R.id.textView);

        }
    }
}

