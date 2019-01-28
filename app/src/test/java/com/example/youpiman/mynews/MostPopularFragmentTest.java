package com.example.youpiman.mynews;

import android.support.v4.app.Fragment;

import com.example.youpiman.mynews.Controllers.Fragments.MostPopularFragment;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MostPopularFragmentTest {

    @Test
    public void shouldNotBeNull() throws Exception {
        Fragment mostPopularFragment = MostPopularFragment.newInstance();
        assertNotNull(mostPopularFragment);
    }
}
