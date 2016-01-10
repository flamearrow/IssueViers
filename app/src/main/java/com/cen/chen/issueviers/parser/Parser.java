package com.cen.chen.issueviers.parser;

import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public interface Parser<T> {
    /**
     * return a list of objects given the json stirng
     * @param jsonStrig
     * @return
     */
    List<T> parse(String jsonStrig);
}
