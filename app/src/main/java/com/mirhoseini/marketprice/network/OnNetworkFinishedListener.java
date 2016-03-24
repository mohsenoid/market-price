package com.mirhoseini.marketprice.network;

import java.util.List;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface OnNetworkFinishedListener<T> {

    void onSuccess(T restResponse);

    void onError(Throwable throwable);
}
