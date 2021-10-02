package models;

public class IsLikedPostResponse {
    private boolean liked;
    private boolean copied;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        if (liked == 1){
            this.liked = true;
        } else {
            this.liked = false;
        }
    }

    public boolean isCopied() {
        return copied;
    }

    public void setCopied(int copied) {
        if (copied == 1){
            this.copied = true;
        } else {
            this.copied = false;
        }
    }
}
