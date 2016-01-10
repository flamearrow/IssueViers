package com.cen.chen.issueviers.network;

import android.os.AsyncTask;

import com.cen.chen.issueviers.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public class ApiRequest<T> extends AsyncTask<String, Integer, List<T>> {
    private static final String TAG = "ApiRequest";
    private Parser<T> mParser;
    private OnSuccessListener mOnSuccessListener;
    private OnFailureListener mOnFailureListener;

    public ApiRequest(Parser<T> parser, OnSuccessListener successListener,
                      OnFailureListener onFailureListener) {
        mParser = parser;
        mOnSuccessListener = successListener;
        mOnFailureListener = onFailureListener;
    }

    @Override
    protected List<T> doInBackground(String[] params) {
        List<T> list = null;
        BufferedReader br = null;
        try {
            URLConnection connection = new URL(params[0]).openConnection();
            br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String newLine = br.readLine();
            while (newLine != null) {
                list = mParser.parse(newLine);
                newLine = br.readLine();
            }
        } catch (IOException e) {
            mOnFailureListener.onFailure(e);
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    @Override
    protected void onPostExecute(List<T> result) {
        mOnSuccessListener.onSuccess(result);
    }

}
