package com.example.youpiman.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.youpiman.mynews.Adapters.TopStoriesAdapter;
import com.example.youpiman.mynews.Controllers.Activities.WebActivity;
import com.example.youpiman.mynews.Models.TopStories.TopStories;
import com.example.youpiman.mynews.Models.TopStories.TopStoriesResult;
import com.example.youpiman.mynews.R;


import com.example.youpiman.mynews.Utils.ItemClickSupport;
import com.example.youpiman.mynews.Utils.NYTStreams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class TopStoriesFragment extends Fragment{

    @BindView(R.id.fragment_top_stories_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loadingPanel)ProgressBar mProgressBar;

    //FOR DATA
    private List<TopStoriesResult> mTopStoriesResults;
    private TopStoriesAdapter mTopStoriesAdapter;
    public Disposable mDisposable;


    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.executeHttpTopStoriesRequest();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", "onDestroy TopStoriesFragment");
        this.disposeWhenDestroy();
        super.onDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    // Configure click on RecyclerView's item
    public void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("openUrl", mTopStoriesResults.get(position).getUrl());
                        startActivity(intent);
                    }
                });
    }




    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    public void configureRecyclerView(){
        // 3.1 - Reset list
        this.mTopStoriesResults = new ArrayList<>();
        // 3.2 - Create mTopStoriesAdapter passing the top stories
        this.mTopStoriesAdapter = new TopStoriesAdapter(this.mTopStoriesResults, Glide.with(this));
        // 3.3 - Attach the mTopStoriesAdapter to the recyclerview to populate items
        this.mRecyclerView.setAdapter(this.mTopStoriesAdapter);
        // 3.4 - Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));


    }

    // Configure the SwipeRefreshLayout
    public void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpTopStoriesRequest();
            }
        });
    }

    // ------------------------------
    //  HTTP REQUEST (RxJAVA)
    // ------------------------------


        // Execute our Stream
    private void executeHttpTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.mDisposable = NYTStreams.streamFetchTopStories("home").subscribeWith(new DisposableObserver<TopStories>(){

            @Override
            public void onNext(TopStories topStories) {
                Log.e("TopStoriesF - onNext", "On Next TopStoriesFragment");
                // Update RecyclerView after getting results from NYT API
                updateUI(topStories.getTopStoriesResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TopStoriesF - onError", Log.getStackTraceString(e));
                Toast.makeText(getContext(),"Une erreur est survenue ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                Log.e("TopSF - onComplete", "On Complete TopStoriesFragment");

            }
        });
    }

    public void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }


    // ------------------
    //  UPDATE UI
    // ------------------

    // 3 - Update UI showing only name of users
    public void updateUI(List<TopStoriesResult> topStoriesResults){
        mProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        mTopStoriesResults.clear();
        mTopStoriesResults.addAll(topStoriesResults);
        mTopStoriesAdapter.notifyDataSetChanged();
    }
}
