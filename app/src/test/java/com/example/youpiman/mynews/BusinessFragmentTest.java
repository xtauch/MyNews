package com.example.youpiman.mynews;

import android.support.v4.app.Fragment;

import com.example.youpiman.mynews.Controllers.Fragments.BusinessFragment;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BusinessFragmentTest {

    @Test
    public void shouldNotBeNull() throws Exception {
        Fragment businessFragment = BusinessFragment.newInstance();
        assertNotNull(businessFragment);
    }

}
