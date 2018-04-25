package com.example.youpiman.mynews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.youpiman.mynews.Models.MostPopular.MostPopularResult;
import com.example.youpiman.mynews.R;
import com.example.youpiman.mynews.Views.MostPopularViewHolder;

import java.util.List;

/**
 * Created by Xavier TAUCH on 19/04/2018.
 */
public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {

    // FOR DATA
    private List<MostPopularResult> mMostPopularResults;

    private RequestManager glide;

    public MostPopularAdapter(List<MostPopularResult> mostPopularResults, RequestManager glide) {
        mMostPopularResults = mostPopularResults;
        this.glide = glide;
    }

    @Override
    public MostPopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view holder and inflating its xml layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item, parent, false);

        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MostPopularViewHolder holder, int position) {
        holder.updateWithMostPopular(this.mMostPopularResults.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.mMostPopularResults.size();
    }
}