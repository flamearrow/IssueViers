package com.cen.chen.issueviers.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by flamearrow on 1/7/16.
 */
public class Issue implements Comparable<Issue>, Parcelable {
    String title;
    String body;
    String url;
    Calendar update;

    public Issue() {

    }

    private Issue(String title, String body, Calendar update, String url) {
        this.title = title;
        this.body = body;
        this.update = update;
        this.url = url;
    }

    @Override
    public int compareTo(Issue another) {
        return update.compareTo(another.update);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class Builder {
        String mTitile;
        String mBody;
        Calendar mUpdate;
        String mUrl;

        public Issue build() {
            return new Issue(mTitile, mBody, mUpdate, mUrl);
        }


        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder setTitile(String mTitile) {
            this.mTitile = mTitile;
            return this;
        }

        public Builder setBody(String mBody) {
            this.mBody = mBody;
            return this;
        }

        public Builder setUpdate(Calendar mUpdate) {
            this.mUpdate = mUpdate;
            return this;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getCommentsUrl() {
        return url + "/comments";
    }

    public Calendar getUpdate() {
        return update;
    }

}
