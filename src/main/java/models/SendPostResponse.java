package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendPostResponse {
    @JsonProperty("post_id")
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
