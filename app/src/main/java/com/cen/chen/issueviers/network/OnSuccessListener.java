package com.cen.chen.issueviers.network;

import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public interface OnSuccessListener<T> {
    /**
     * Call back when api request succeed
     * @param result parsed models
     */
    void onSuccess(List<T> result);
}
