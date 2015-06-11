package com.example.john.samplegame;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Philip on 6/10/2015.
 */
public class SampleHolder {


    private static final String TAG = "SampleHolder";

    public static void bindSampleList(SampleListAdapter.SampleListHolder holder, ArrayList<String> nameList, int position) {

        final String bindString = nameList.get(position);
        holder.mName.setText(nameList.get(position));
        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra(DetailedActivity.TAG, bindString);
                context.startActivity(intent);
            }
        });
    }
}
