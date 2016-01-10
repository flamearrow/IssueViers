package com.cen.chen.issueviers.model;

/**
 * Created by flamearrow on 1/7/16.
 */
public class Comment {
    private Comment(String name, String body) {
        this.name = name;
        this.body = body;
    }

    String name;
    String body;

    public static class Builder {
        private String name;
        private String body;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Comment build() {
            return new Comment(name, body);
        }

    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

}
