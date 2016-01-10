package com.cen.chen.issueviers.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cen.chen.issueviers.R;
import com.cen.chen.issueviers.RecyclerItemClickListener;
import com.cen.chen.issueviers.dialogs.CommentDialog;
import com.cen.chen.issueviers.model.Issue;
import com.cen.chen.issueviers.network.ApiRequest;
import com.cen.chen.issueviers.network.OnFailureListener;
import com.cen.chen.issueviers.network.OnSuccessListener;
import com.cen.chen.issueviers.parser.IssueParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public class IssuesActivity extends Activity implements OnSuccessListener<Issue>,
        OnFailureListener {
    private static final String[] ISSUES_QUERY = {"https://api.github" +
            ".com/repos/crashlytics/secureudid/issues"};
    private static final String TAG = "IssuesActivity";
    private static final String RETAINED_FRAGMENT = "issuesRetainedFragment";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private IssueAdapter mIssueAdapter;
    private TextView mNoDataView;
    private List<Issue> mIssues;

    private IssueRetainedFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issues_layout);
        mRetainedFragment = (IssueRetainedFragment) getFragmentManager().findFragmentByTag
                (RETAINED_FRAGMENT);
        mRecyclerView = (RecyclerView) findViewById(R.id.issueList);
        mProgressBar = (ProgressBar) findViewById(R.id.issueProgressBar);
        mNoDataView = (TextView) findViewById(R.id.emtpyView);
        mIssueAdapter = new IssueAdapter();
        mRecyclerView.setAdapter(mIssueAdapter);
        initializeRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRetainedFragment.setIssues(mIssues);
    }

    private void initializeRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        if (mRetainedFragment == null) {
            mRetainedFragment = new IssueRetainedFragment();
            getFragmentManager().beginTransaction().add(mRetainedFragment, RETAINED_FRAGMENT)
                    .commit();
            Log.d("mlgb", "submitting request for issues");
            new ApiRequest(new IssueParser(), this, this).execute((Object[]) ISSUES_QUERY);
        } else {
            mIssues = mRetainedFragment.getIssues();
            Log.d("mlgb", "using existing issues");
            updateRecyclerView();
        }
    }

    @Override
    public void onFailure(IOException e) {
        Log.d(TAG, "exception: " + e.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
                mNoDataView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void updateRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new
                RecyclerItemClickListener
                        .OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);
                        // Create and show the dialog.
                        Issue iissue = mIssues.get(position);
                        CommentDialog.createCommentDialog(iissue.getCommentsUrl(), iissue
                                .getTitle())
                                .show(ft, "dialog");
                    }
                }));
        mIssueAdapter.setIssues(mIssues);
        mIssueAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(final List<Issue> results) {
        mIssues = results;
        updateRecyclerView();
    }

    private class IssueViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mBody;

        public IssueViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.issueTitle);
            mBody = (TextView) v.findViewById(R.id.issueBody);
        }
    }

    private class IssueAdapter extends RecyclerView.Adapter<IssueViewHolder> {
        private List<Issue> mIssues;


        public IssueAdapter() {
            mIssues = new LinkedList<>();
        }

        public void setIssues(List<Issue> issues) {
            mIssues = issues;
        }

        @Override
        public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View ret = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.issue_view, parent, false);
            return new IssueViewHolder(ret);
        }

        @Override
        public void onBindViewHolder(IssueViewHolder holder, int position) {
            holder.mTitle.setText(mIssues.get(position).getTitle());
            holder.mBody.setText(mIssues.get(position).getBody());
        }

        @Override
        public int getItemCount() {
            return mIssues.size();
        }
    }
}
