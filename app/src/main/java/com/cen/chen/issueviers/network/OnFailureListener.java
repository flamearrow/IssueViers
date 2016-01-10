package com.cen.chen.issueviers.network;

import java.io.IOException;

/**
 * Created by flamearrow on 1/7/16.
 */
public interface OnFailureListener {
    void onFailure(IOException e);
}
