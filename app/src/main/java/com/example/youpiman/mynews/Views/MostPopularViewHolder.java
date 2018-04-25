package com.example.youpiman.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.youpiman.mynews.Models.MostPopular.MostPopularResult;
import com.example.youpiman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MostPopularViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_page_item_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_item_image)
    ImageView mImageView;
    @BindView(R.id.fragment_page_item_section)
    TextView mSection;
    @BindView(R.id.fragment_page_item_date)
    TextView mDate;


    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void updateWithMostPopular(MostPopularResult mostPopularResult, RequestManager glide) {
        this.mTitle.setText(mostPopularResult.getTitle());
        this.mDate.setText(mostPopularResult.getPublishedDate().substring(0, 10));
        this.mSection.setText(mostPopularResult.getSection());
        if (mostPopularResult.getMultimedia().size() != 0) {
            glide.load(mostPopularResult.getMultimedia().get(0).getMediaMetadata().get(0).getUrl())
                    .into(mImageView);
        } else {
            mImageView.setImageResource(R.drawable.ic_openclassrooms);
        }


    }
}