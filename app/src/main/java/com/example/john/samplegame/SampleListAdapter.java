package com.example.john.samplegame;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Philip on 6/10/2015.
 */
public class SampleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> nameList;

    public SampleListAdapter(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_list_layout, parent, false);
        return new SampleListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleListHolder) {
            SampleHolder.bindSampleList((SampleListHolder) holder, nameList, position);
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    static class SampleListHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public SampleListHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.tvName);
        }
    }
}
