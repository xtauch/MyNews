package com.example.youpiman.mynews.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.youpiman.mynews.Controllers.Fragments.BusinessFragment;
import com.example.youpiman.mynews.Controllers.Fragments.MostPopularFragment;
import com.example.youpiman.mynews.Controllers.Fragments.TopStoriesFragment;
import com.example.youpiman.mynews.R;


public class PageAdapter extends FragmentPagerAdapter {

    private Context mContext;

    //Default Constructor
    public PageAdapter(FragmentManager mgr, Context ctx) {
        super(mgr);
        mContext = ctx;
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return BusinessFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String TOP_STORIES = mContext.getString(R.string.top_stories);
        String MOST_POPULAR = mContext.getString(R.string.most_popular);
        String BUSINESS = mContext.getString(R.string.business);

        switch (position){
            case 0: //Page number 1
                return TOP_STORIES;
            case 1: //Page number 2
                return MOST_POPULAR;
            case 2: //Page number 3
                return BUSINESS;
            default:
                return null;
        }
    }
}