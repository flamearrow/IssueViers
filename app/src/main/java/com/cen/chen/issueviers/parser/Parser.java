package com.cen.chen.issueviers.parser;

import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public interface Parser<T> {
    List<T> parse(String jsonStrig);
}
