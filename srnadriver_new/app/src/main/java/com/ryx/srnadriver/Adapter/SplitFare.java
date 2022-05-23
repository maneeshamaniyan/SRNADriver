package com.ryx.srnadriver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryx.srnadriver.Model.RideResponse;
import com.ryx.srnadriver.Model.SplitFareModel;
import com.ryx.srnadriver.R;

import java.util.List;

public class SplitFare extends RecyclerView.Adapter<SplitFare.ViewHolder> {

    private Context context;
    List<SplitFareModel> rideInfo;
    SharedPreferences prefs;


    public SplitFare(Context context, List<SplitFareModel> rideInfo) {
        this.context = context;
        this.rideInfo = rideInfo;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.split_fare_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



        try {
            if(rideInfo !=null)
            {

                holder.split_user_name.setText(rideInfo.get(position).getName());
                holder.split_amount.setText(""+rideInfo.get(position).getAmount());


            }
        }
        catch (Exception e)
        {
            Log.d("Exception", e.getMessage());
        }








    }




    @Override
    public int getItemCount() {
       return rideInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        TextView split_user_name,split_amount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            split_user_name = itemView.findViewById(R.id.tv_address_name);

            split_amount = itemView.findViewById(R.id.tv_address_sub);



        }
    }
}
