package com.rubahapi.iklaniku.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubahapi.iklaniku.R;
import com.rubahapi.iklaniku.VehicleSelectActivity;
import com.rubahapi.iklaniku.data.SpotIklanContract;

/**
 * Created by prasetia on 1/11/2017
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public VehicleAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    public void updateResult(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_vehicle, parent, false);
        return new ViewHolder(view);
    }

    private int getArrayPosition(String value){
        int i = 0;
        for (String vehicleColumn : VehicleSelectActivity.VEHICLE_COLUMNS){
            if(vehicleColumn == value){
                return i;
            }
            i++;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (null != cursor){
            cursor.moveToPosition(position);
            holder.textView.setText(cursor.getString(getArrayPosition(SpotIklanContract.VehicleEntry.COLUMN_NAME)));
        }
    }

    @Override
    public int getItemCount() {
        if(null != cursor){
            return  cursor.getCount();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_text_view);
        }
    }
}
