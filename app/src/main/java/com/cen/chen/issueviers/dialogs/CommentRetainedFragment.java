package com.cen.chen.issueviers.dialogs;

import android.app.Fragment;
import android.os.Bundle;

import com.cen.chen.issueviers.model.Comment;

import java.util.List;

/**
 * Created by flamearrow on 1/9/16.
 */
public class CommentRetainedFragment extends Fragment {
    private List<Comment> mComments;
    private String mTitle;

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        this.mComments = comments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}
