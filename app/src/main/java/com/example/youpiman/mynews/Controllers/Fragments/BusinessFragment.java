package com.example.youpiman.mynews.Controllers.Fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.youpiman.mynews.Models.TopStories.TopStories;
import com.example.youpiman.mynews.R;
import com.example.youpiman.mynews.Utils.NYTStreams;


import butterknife.ButterKnife;

import io.reactivex.observers.DisposableObserver;


public class BusinessFragment extends TopStoriesFragment {


    public static BusinessFragment newInstance() {
        return (new BusinessFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();
        configureSwipeRefreshLayout();
        configureOnClickRecyclerView();
        this.executeHttpTopStoriesRequest();
        return view;

    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", "onDestroy BusinessFragment");
        disposeWhenDestroy();
        super.onDestroy();
    }


    // -----------------
    // HTTP (RxJAVA)
    // -----------------

    // Execute our Stream
    private void executeHttpTopStoriesRequest() {
        // Execute the stream subscribing to Observable defined inside GithubStream

        mDisposable = NYTStreams.streamFetchBusinessTopStories().subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories topStories) {
                Log.e("BusinessF - onNext", "On Next BusinessFragment");
                // Update RecyclerView after getting results from NYT API
                updateUI(topStories.getTopStoriesResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("BusinessF - onError", Log.getStackTraceString(e));
                Toast.makeText(getContext(),"Une erreur est survenue ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onComplete() {
                Log.e("BusinessF - onComplete", "On Complete BusinessFragment");

            }
        });
    }
}

