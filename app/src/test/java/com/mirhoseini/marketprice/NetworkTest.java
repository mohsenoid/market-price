package com.mirhoseini.marketprice;

import com.mirhoseini.marketprice.network.NetworkHelper;
import com.mirhoseini.marketprice.network.OnNetworkFinishedListener;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Mohsen on 24/03/16.
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
//
//                return null;
//            }
//        }).when(onNetworkFinishedListener).onSuccess(eq(timeSpan), any(RestMarketPrice.class));
//
//        doAnswer(new Answer<Void>() {
//
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//
//                return null;
//            }
//        }).when(onNetworkFinishedListener).onError(eq(timeSpan), any(Throwable.class));

        networkHelper.loadMarketPriceValues(timeSpan, onNetworkFinishedListener);

        //wait for network timeout
        Thread.sleep(30 * 1000);

        // verify if onSuccess() method was fired once and no onError()
        verify(onNetworkFinishedListener, times(1)).onSuccess(eq(timeSpan), any(RestMarketPrice.class));
        verify(onNetworkFinishedListener, times(0)).onError(eq(timeSpan), any(Throwable.class));

    }
}
