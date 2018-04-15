package com.example.youpiman.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youpiman.mynews.Models.TopStories.TopStoriesResult;
import com.example.youpiman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TopStoriesViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_page_item_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_item_image)
    ImageView mImageView;
    @BindView(R.id.fragment_page_item_section)
    TextView mSection;
    @BindView(R.id.fragment_page_item_date)
    TextView mDate;

    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStories(TopStoriesResult topStoriesResult){
        this.mTitle.setText(topStoriesResult.getTitle());
        this.mDate.setText(topStoriesResult.getCreatedDate());
        this.mSection.setText(topStoriesResult.getSection());
    }
}
