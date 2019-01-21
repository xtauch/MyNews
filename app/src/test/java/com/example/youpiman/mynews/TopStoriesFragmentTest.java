package com.example.youpiman.mynews;

import android.support.v4.app.Fragment;

import com.example.youpiman.mynews.Controllers.Fragments.TopStoriesFragment;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TopStoriesFragmentTest {

    @Test
    public void shouldNotBeNull() throws Exception {
        Fragment topStoriesFragment = TopStoriesFragment.newInstance();
        assertNotNull(topStoriesFragment);
    }
}