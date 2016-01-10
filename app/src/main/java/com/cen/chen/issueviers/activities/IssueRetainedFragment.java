package com.cen.chen.issueviers.activities;

import android.app.Fragment;
import android.os.Bundle;

import com.cen.chen.issueviers.model.Issue;

import java.util.List;

/**
 * Created by flamearrow on 1/9/16.
 */
public class IssueRetainedFragment extends Fragment {
    private List<Issue> mIssues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setIssues(List<Issue> issues) {
        mIssues = issues;
    }

    public List<Issue> getIssues() {
        return mIssues;
    }
}
