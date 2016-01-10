package com.cen.chen.issueviers.dialogs;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cen.chen.issueviers.R;
import com.cen.chen.issueviers.model.Comment;
import com.cen.chen.issueviers.network.ApiRequest;
import com.cen.chen.issueviers.network.OnFailureListener;
import com.cen.chen.issueviers.network.OnSuccessListener;
import com.cen.chen.issueviers.parser.CommentParser;

import java.io.IOException;
import java.util.List;

/**
 * Created by flamearrow on 1/9/16.
 */
public class CommentDialog extends DialogFragment implements OnSuccessListener<Comment>,
        OnFailureListener {
    private static final String SEPERATOR = "---------------";
    private static final String TAG = "CommentDialog";
    private static final String RETAINED_FRAGMENT = "commentRetainedFragment";
    private static final String URL = "url";
    private static final String TITLE = "titile";
    private TextView mTextView;
    private ProgressBar mProgresssBar;
    private String mTitle;
    private String mUrl;
    private List<Comment> mComments;

    private CommentRetainedFragment mRetainedFragment;

    public static CommentDialog createCommentDialog(String dialogUrl, String titleStr) {
        Bundle args = new Bundle();
        args.putString(URL, dialogUrl);
        args.putString(TITLE, titleStr);
        CommentDialog ret = new CommentDialog();
        ret.setArguments(args);
        return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        mTitle = args.getString(TITLE);
        mUrl = args.getString(URL);
        mRetainedFragment = (CommentRetainedFragment) getFragmentManager().findFragmentByTag
                (RETAINED_FRAGMENT + mUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetainedFragment.setTitle(mTitle);
        mRetainedFragment.setComments(mComments);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.comment_view, container, true);
        getDialog().setTitle(mTitle);
        mTextView = (TextView) v.findViewById(R.id.commentContent);
        mProgresssBar = (ProgressBar) v.findViewById(R.id.commentProgressbar);
        v.findViewById(R.id.dismissButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
//        mRetainedFragment.getComments() would be null if previous dialog is created without
// network
        if (mRetainedFragment == null || mRetainedFragment.getComments() == null) {
            Log.d("mlgb", "requesting network for comment");
            mRetainedFragment = new CommentRetainedFragment();
            getFragmentManager().beginTransaction().add(mRetainedFragment, RETAINED_FRAGMENT + mUrl)
                    .commit();
            String[] params = {mUrl};
            new ApiRequest(new CommentParser(), this, this).executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR, (Object[]) params);
        } else {
            Log.d("mlgb", "use existing comments");
            mComments = mRetainedFragment.getComments();
            updateDialogText();
        }
        return v;
    }

    @Override
    public void onFailure(IOException e) {
        Log.d(TAG, "fail in looking for comment: " + e.toString());
        updateFailureText();
    }

    @Override
    public void onSuccess(List<Comment> result) {
        mComments = result;
        updateDialogText();
    }

    private void updateDialogText() {
        mTextView.setText(buildCommentText(mComments));
        mProgresssBar.setVisibility(View.GONE);
    }

    private void updateFailureText() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mTextView.setText(getString(R.string.no_data));
                mProgresssBar.setVisibility(View.GONE);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private String buildCommentText(List<Comment> result) {
        StringBuilder sb = new StringBuilder();
        for (Comment c : result) {
            sb.append("" + c.getName() + " says.. \n");
            sb.append(c.getBody() + "\n");
            sb.append(SEPERATOR + "\n");
        }
        return sb.toString();
    }
}
