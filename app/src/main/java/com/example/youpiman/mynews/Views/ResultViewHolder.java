package com.example.youpiman.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.youpiman.mynews.Models.ArticleSearch.Doc;
import com.example.youpiman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_page_item_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_item_image)
    ImageView mImageView;
    @BindView(R.id.fragment_page_item_section)
    TextView mSection;
    @BindView(R.id.fragment_page_item_date)
    TextView mDate;

    public ResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithResult(Doc doc, RequestManager glide) {
        this.mTitle.setText(doc.getHeadline().getMain());
        this.mDate.setText(doc.getPubDate().substring(0, 10));
        this.mSection.setText(doc.getSectionName());
        if (doc.getMultimedia().size() != 0) {
            glide.load("https://www.nytimes.com/"+doc.getMultimedia().get(0).getUrl())
                    .into(mImageView);
        } else {
            mImageView.setImageResource(R.drawable.ic_openclassrooms);
        }
    }
}