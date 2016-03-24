package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface OnNetworkFinishedListener<T> {

    void onSuccess(TimeSpan timeSpan, T restResponse);

    void onError(TimeSpan timeSpan, Throwable throwable);
}
