package main;

import kong.unirest.jackson.JacksonObjectMapper;
import models.*;
import utils.ApiUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ApiAppRequest {
    private static final String OWNER_ID = Config.get("owner_id");
    private static final String ACCESS_TOKEN = Config.get("access_token");
    private static final String API_VERSION = Config.get("api_version");

    public static String sendPostOnTheWall(String message){
        String request = String.format("%s?owner_id=%s&message=%s&access_token=%s&v=%s",Method.SEND_POST.getMethod(), OWNER_ID, message, ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse = ApiUtils.post(request);
        SendPostResponse response = new JacksonObjectMapper().readValue(jsonResponse.getBody().getObject().get("response").toString(), SendPostResponse.class);
        return response.getPostId();
    }

    public static String editPostWithAttachment(String message, String postId, String filePath){
        String sentPhotoId = getSavedPhotoResponse(OWNER_ID,filePath).getId();
        String request = String.format("%s?owner_id=%s&post_id=%s&message=%s&attachments=photo%s_%s&access_token=%s&v=%s",Method.EDIT_POST.getMethod(),OWNER_ID,postId,message,OWNER_ID,sentPhotoId,ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse = ApiUtils.post(request);
        return jsonResponse.getBody().getObject().get("response").toString();
    }

    public static SavePhotoResponse getSavedPhotoResponse(String ownerId, String filePath){
        String request = String.format("%s?user_id=%s&access_token=%s&v=%s",Method.PHOTO_UPLOAD_SERVER.getMethod(),ownerId,ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse = ApiUtils.get(request);
        WallUploadServerResponse wallUploadServerResponse = new JacksonObjectMapper().readValue(jsonResponse.getBody().getObject().get("response").toString(), WallUploadServerResponse.class);
        JsonResponse jsonResponse1 = ApiUtils.upLoad(wallUploadServerResponse.getUploadUrl(), filePath,"photo");
        UploadPhotoResponse uploadPhotoResponse = new JacksonObjectMapper().readValue(jsonResponse1.getBody().toString(), UploadPhotoResponse.class);
        String encodedURL = null;
        try {
            encodedURL = URLEncoder.encode(uploadPhotoResponse.getPhoto(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String request1 = String.format("%s?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=%s",Method.SAVE_WALL_PHOTO.getMethod(),
                ownerId, encodedURL,uploadPhotoResponse.getServer(),uploadPhotoResponse.getHash(),ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse2 = ApiUtils.post(request1);
        return new JacksonObjectMapper().readValue(jsonResponse2.getBody().getObject().getJSONArray("response").get(0).toString(), SavePhotoResponse.class);
    }

    public static String sendCommentToPost(String message, String postId){
        String request = String.format("%s?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=%s",Method.WALL_CREATE_COMMENT.getMethod(),OWNER_ID,postId,message,ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse = ApiUtils.post(request);
        SendCommentResponse response = new JacksonObjectMapper().readValue(jsonResponse.getBody().getObject().get("response").toString(), SendCommentResponse.class);
        return response.getCommentId();
    }

    public static boolean isLikedPost(String postId,String userId){
        String request = String.format("%s?owner_id=%s&user_id=%s&type=post&item_id=%s&access_token=%s&v=%s",Method.IS_LIKED.getMethod(),OWNER_ID,userId,postId,ACCESS_TOKEN,API_VERSION);
        JsonResponse jsonResponse = ApiUtils.get(request);
        IsLikedPostResponse response = new JacksonObjectMapper().readValue(jsonResponse.getBody().getObject().get("response").toString(), IsLikedPostResponse.class);
        return response.isLiked();
    }

    public static void deletePost(String postId){
        String request = String.format("%s?owner_id=%s&post_id=%s&access_token=%s&v=%s",Method.DELETE_POST.getMethod(), OWNER_ID,postId, ACCESS_TOKEN, API_VERSION);
        ApiUtils.post(request);
    }
}
