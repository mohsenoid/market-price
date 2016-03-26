package com.mirhoseini.marketprice;

import com.mirhoseini.marketprice.network.NetworkHelper;
import com.mirhoseini.marketprice.network.OnNetworkFinishedListener;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.network.model.RestPriceValue;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.marketprice.utils.TimeSpan;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Mohsen on 3/26/16.
 */
public class NetworkTest {
    OnNetworkFinishedListener<RestMarketPrice> onNetworkFinishedListener;
    NetworkHelper networkHelper;
    TimeSpan timeSpan;


    @Before
    public void setUp() {
        timeSpan = TimeSpan.DAY_30;
        networkHelper = NetworkHelper.getInstance();
        onNetworkFinishedListener = mock(OnNetworkFinishedListener.class);
    }

    @Test
    public void testLoadMarketPriceValues() throws InterruptedException {

        assertNotNull(networkHelper);
        assertNotNull(onNetworkFinishedListener);

//        doAnswer(new Answer<Void>() {
//
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//                // TODO Auto-generated method stub
//                invocation.getArguments()[0].equals(timeSpan);
//                return null;
//            }
//        }).when(onNetworkFinishedListener).onSuccess(eq(timeSpan), any(RestMarketPrice.class));
//
//        doAnswer(new Answer<Void>() {
//
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//                // TODO Auto-generated method stub
//                return null;
//            }
//        }).when(onNetworkFinishedListener).onError(eq(timeSpan), any(Throwable.class));

        networkHelper.loadMarketPriceValues(timeSpan, onNetworkFinishedListener);

        //wait as network timeout
        Thread.sleep(30 * 1000);

        // verifies the onSuccess() method was called
        verify(onNetworkFinishedListener, times(1)).onSuccess(eq(timeSpan), any(RestMarketPrice.class));
        verify(onNetworkFinishedListener, times(0)).onError(eq(timeSpan), any(Throwable.class));

    }
}
