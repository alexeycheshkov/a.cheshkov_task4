package main;

public enum Method {
    SEND_POST("wall.post"),
    EDIT_POST("wall.edit"),
    PHOTO_UPLOAD_SERVER("photos.getWallUploadServer"),
    SAVE_WALL_PHOTO("photos.saveWallPhoto"),
    WALL_CREATE_COMMENT("wall.createComment"),
    IS_LIKED("likes.isLiked"),
    DELETE_POST("wall.delete")
    ;

    private String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
