package com.cen.chen.issueviers.parser;

import android.util.Log;

import com.cen.chen.issueviers.model.Issue;
import com.cen.chen.issueviers.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public class IssueParser implements Parser<Issue> {
    //    {
//        "url": "https://api.github.com/repos/crashlytics/secureudid/issues/13",
//            "labels_url": "https://api.github.com/repos/crashlytics/secureudid/issues/13/labels{/name}",
//            "comments_url": "https://api.github.com/repos/crashlytics/secureudid/issues/13/comments",
//            "events_url": "https://api.github.com/repos/crashlytics/secureudid/issues/13/events",
//            "html_url": "https://github.com/crashlytics/secureudid/issues/13",
//            "id": 3923240,
//            "number": 13,
//            "title": "Not working with ARC",
//            "user": {
//        "login": "SaschaMoellering",
//                "id": 1321549,
//                "avatar_url": "https://avatars.githubusercontent.com/u/1321549?v=3",
//                "gravatar_id": "",
//                "url": "https://api.github.com/users/SaschaMoellering",
//                "html_url": "https://github.com/SaschaMoellering",
//                "followers_url": "https://api.github.com/users/SaschaMoellering/followers",
//                "following_url": "https://api.github.com/users/SaschaMoellering/following{/other_user}",
//                "gists_url": "https://api.github.com/users/SaschaMoellering/gists{/gist_id}",
//                "starred_url": "https://api.github.com/users/SaschaMoellering/starred{/owner}{/repo}",
//                "subscriptions_url": "https://api.github.com/users/SaschaMoellering/subscriptions",
//                "organizations_url": "https://api.github.com/users/SaschaMoellering/orgs",
//                "repos_url": "https://api.github.com/users/SaschaMoellering/repos",
//                "events_url": "https://api.github.com/users/SaschaMoellering/events{/privacy}",
//                "received_events_url": "https://api.github.com/users/SaschaMoellering/received_events",
//                "type": "User",
//                "site_admin": false
//    },
//        "labels": [
//
//        ],
//        "state": "open",
//            "locked": false,
//            "assignee": null,
//            "milestone": null,
//            "comments": 10,
//            "created_at": "2012-04-02T09:18:15Z",
//            "updated_at": "2012-04-04T14:20:28Z",
//            "closed_at": null,
//            "body": "Hi,\r\n\r\nI tried to get the code running with ARC, so I changed the current code like this:\r\n\r\n\r\n CFUUIDRef uuid = CFUUIDCreate(kCFAllocatorDefault);\r\n        identifier = CFBridgingRelease(CFUUIDCreateString(kCFAllocatorDefault, uuid));\r\n        CFRelease(uuid);\r\n\r\nBut I get an EXC_BAD_ACCESS during runtime. Can you test your code with ARC?\r\n\r\nBest regards,\r\nSascha"
//    }
    @Override
    public List<Issue> parse(String jsonStr) {
        List<Issue> list = new LinkedList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            Log.d("mlgb", "found " + jsonArray.length() + " issues");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Issue ret = new Issue.Builder().setBody(obj.getString("body")).
                        setTitile(obj.getString("title")).
                        setUpdate(TimeUtils.parseFromString(obj.getString("updated_at"))).setUrl(obj.getString("url")).build();
                list.add(ret);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        return list;
    }
}
