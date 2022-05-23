package com.ryx.srnadriver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ryx.srnadriver.Model.CurrentTripResponse;
import com.ryx.srnadriver.Model.PastItemModel;
import com.ryx.srnadriver.R;

public class PastTripAdapter extends RecyclerView.Adapter<PastTripAdapter.ViewHolder> {

    private Context context;
    PastItemModel pastItemModel;



    public PastTripAdapter(Context context, PastItemModel pastItemModel) {
        this.context = context;
        this.pastItemModel = pastItemModel;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.past_trip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.textView.setText(pastItemModel.getData().getTripInfo().get(position).getSourceAddress());
        holder.tripamount.setText(pastItemModel.getData().getTripInfo().get(position).getEstimatedFare()+" AED");




    }




    @Override
    public int getItemCount() {
        return pastItemModel.getData().getTripInfo().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,tripamount;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            tripamount = itemView.findViewById(R.id.trip_amount);


        }
    }
}

