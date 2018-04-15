package com.example.youpiman.mynews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youpiman.mynews.Models.TopStories.TopStoriesResult;
import com.example.youpiman.mynews.R;
import com.example.youpiman.mynews.Views.TopStoriesViewHolder;

import java.util.List;



public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

// FOR DATA
private List<TopStoriesResult> mTopStoriesResults;

// CONSTRUCTOR
public TopStoriesAdapter(List<TopStoriesResult> topStoriesResults) {
        this.mTopStoriesResults = topStoriesResults;
        }

@Override
public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item, parent, false);

        return new TopStoriesViewHolder(view);
        }

// UPDATE VIEW HOLDER WITH A TOP STORY
@Override
public void onBindViewHolder(TopStoriesViewHolder viewHolder, int position) {
        viewHolder.updateWithTopStories(this.mTopStoriesResults.get(position));
        }

// RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
@Override
public int getItemCount() {
        return this.mTopStoriesResults.size();
        }
        }