package com.cen.chen.issueviers.parser;

import com.cen.chen.issueviers.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by flamearrow on 1/7/16.
 */
public class CommentParser implements Parser<Comment> {
    //    {
//        "url": "https://api.github.com/repos/crashlytics/secureudid/issues/comments/4887892",
//            "html_url": "https://github.com/crashlytics/secureudid/issues/13#issuecomment-4887892",
//            "issue_url": "https://api.github.com/repos/crashlytics/secureudid/issues/13",
//            "id": 4887892,
//            "user": {
//        "login": "mattmassicotte",
//                "id": 85322,
//                "avatar_url": "https://avatars.githubusercontent.com/u/85322?v=3",
//                "gravatar_id": "",
//                "url": "https://api.github.com/users/mattmassicotte",
//                "html_url": "https://github.com/mattmassicotte",
//                "followers_url": "https://api.github.com/users/mattmassicotte/followers",
//                "following_url": "https://api.github.com/users/mattmassicotte/following{/other_user}",
//                "gists_url": "https://api.github.com/users/mattmassicotte/gists{/gist_id}",
//                "starred_url": "https://api.github.com/users/mattmassicotte/starred{/owner}{/repo}",
//                "subscriptions_url": "https://api.github.com/users/mattmassicotte/subscriptions",
//                "organizations_url": "https://api.github.com/users/mattmassicotte/orgs",
//                "repos_url": "https://api.github.com/users/mattmassicotte/repos",
//                "events_url": "https://api.github.com/users/mattmassicotte/events{/privacy}",
//                "received_events_url": "https://api.github.com/users/mattmassicotte/received_events",
//                "type": "User",
//                "site_admin": false
//    },
//        "created_at": "2012-04-02T20:37:51Z",
//            "updated_at": "2012-04-02T20:37:51Z",
//            "body": "I'll try to have a look at this.  Thanks for posting that bit of code."
//    }
    @Override
    public List<Comment> parse(String jsonStr) {
        List<Comment> list = new LinkedList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Comment comment = new Comment.Builder().setBody(obj.getString("body"))
                        .setName(obj.getJSONObject("user").getString("login")).build();
                list.add(comment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
