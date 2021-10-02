package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendCommentResponse {
    @JsonProperty("comment_id")
    private String commentId;
    @JsonProperty("parents_stack")
    private String[] parentsStack;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String[] getParentsStack() {
        return parentsStack;
    }

    public void setParentsStack(String[] parentsStack) {
        this.parentsStack = parentsStack;
    }
}
