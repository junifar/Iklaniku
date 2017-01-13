package com.rubahapi.iklaniku.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rubahapi.iklaniku.R;
import com.rubahapi.iklaniku.VehicleSelectActivity;
import com.rubahapi.iklaniku.data.SpotIklanContract;

/**
 * Created by prasetia on 1/12/2017
 */

public class VehiclePagerAdapter extends PagerAdapter {

    private Cursor cursor;
    private Context context;
    private LayoutInflater layoutInflater;

    public VehiclePagerAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    public void updateResult(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        if(null != cursor){
            return  cursor.getCount();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_vehicle, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.vehicle_image_view);
        TextView textView = (TextView) view.findViewById(R.id.name_text_view);

        if (null != cursor){
            cursor.moveToPosition(position);
            textView.setText(cursor.getString(getArrayPosition(SpotIklanContract.VehicleEntry.COLUMN_NAME)));
            Glide.with(context).load(cursor.getString(getArrayPosition(SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH))).into(imageView);
        }
        container.addView(view);
        return view;
//        return super.instantiateItem(container, position);
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
//        super.destroyItem(container, position, object);
    }

}
