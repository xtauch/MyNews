package com.example.youpiman.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.youpiman.mynews.Adapters.MostPopularAdapter;
import com.example.youpiman.mynews.Controllers.Activities.WebActivity;
import com.example.youpiman.mynews.Models.MostPopular.MostPopular;
import com.example.youpiman.mynews.Models.MostPopular.MostPopularResult;
import com.example.youpiman.mynews.R;
import com.example.youpiman.mynews.Utils.ItemClickSupport;
import com.example.youpiman.mynews.Utils.NYTStreams;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_most_popular_recycler_view)
    RecyclerView mRecyclerView;


    // FOR DATA
    private Disposable mDisposable;
    private List<MostPopularResult> mMostPopularResults = new ArrayList<>();
    private MostPopularAdapter mMostPopularAdapter;

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, result);
        this.configureMostPopularRecyclerView();
        this.executeHttpMostPopularRequest();
        this.configureOnClickRecyclerView();
        // Inflate the layout for this fragment
        return result;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy MostPopularFragment");
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("openUrl", mMostPopularResults.get(position).getUrl());
                        startActivity(intent);
                    }
                });
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureMostPopularRecyclerView(){
        this.mMostPopularResults = new ArrayList<>();
        this.mMostPopularAdapter = new MostPopularAdapter(this.mMostPopularResults, Glide.with(this));
        this.mRecyclerView.setAdapter(this.mMostPopularAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    // -----------------
    // HTTP (RxJAVA)
    // -----------------

    // Execute our Stream
    private void executeHttpMostPopularRequest(){
        // Execute the stream subscribing to Observable defined inside GithubStream
        this.mDisposable = NYTStreams.streamFetchMostPopular("all-sections").subscribeWith(new DisposableObserver<MostPopular>(){
            @Override
            public void onNext(MostPopular mostPopular) {
                // Update RecyclerView after getting results from NYT API
                updateMostPopularUI(mostPopular.getMostPopularResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MostPopular onError", Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("MostPopular onComplete", "On Complete !!");

            }
        });
    }

    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    // -----------------
    // UPDATE UI
    // -----------------

    private void updateMostPopularUI(List<MostPopularResult> mostPopularResults){
        mMostPopularResults.clear();
        mMostPopularResults.addAll(mostPopularResults);
        mMostPopularAdapter.notifyDataSetChanged();
    }
}