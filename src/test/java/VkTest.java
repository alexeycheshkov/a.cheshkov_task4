import aquality.selenium.core.logging.Logger;
import main.ApiAppRequest;
import main.Config;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.AuthForm;
import pageobjects.CommentForm;
import pageobjects.NavMenuForm;
import pageobjects.PostForm;
import utils.FileUtils;
import utils.SikuliUtils;

public class VkTest extends BaseTest{
    private AuthForm authForm = new AuthForm();
    private NavMenuForm navMenuForm = new NavMenuForm();
    private final int randomStringLength = 10;
    private String autogenMessage;
    private final String login = Config.get("user_login");
    private final String password = Config.get("user_password");
    private final String user_id = Config.get("user_id");
    private final String photoFolderPath = Config.get("upload_photo_folder");
    private final String uploadPhotoName = FileUtils.loadProperties("src/main/resources/testData.properties").getProperty("upload_photo_name");

    @Test
    public void vkGuiApiWallPostTest(){
        Logger.getInstance().info("Authorization in vk.com");
        authForm.signIn(login,password);
        Logger.getInstance().info("Opening 'My page'");
        navMenuForm.clickMyPageBtn();
        Logger.getInstance().info("Sending request to create post on the wall");
        autogenMessage = RandomStringUtils.randomAlphanumeric(randomStringLength);
        String postId = ApiAppRequest.sendPostOnTheWall(autogenMessage);
        PostForm sentPost = new PostForm("API post",postId,user_id);
        Assert.assertEquals(sentPost.getPostText(),autogenMessage, "Posted text in GUI and sent text through API are not equal");
        Assert.assertTrue(sentPost.isExist(),"Post "+sentPost.getId()+" from user "+user_id+" doesn't exist");
        Logger.getInstance().info("Sending request to edit post on the wall");
        autogenMessage = RandomStringUtils.randomAlphanumeric(randomStringLength);
        ApiAppRequest.editPostWithAttachment(autogenMessage,sentPost.getId(),photoFolderPath+uploadPhotoName);
        Assert.assertEquals(sentPost.getPostText(),autogenMessage,"Posted text in GUI and sent edited text through API are equal");
        browser.scrollWindowBy(0,500);
        Assert.assertTrue(SikuliUtils.isExistPhoto(uploadPhotoName),"Post doesn't contain photo from previous step");
        Logger.getInstance().info("Sending request to leave a comment to post on the wall");
        autogenMessage = RandomStringUtils.randomAlphanumeric(randomStringLength);
        String commentId = ApiAppRequest.sendCommentToPost(autogenMessage,sentPost.getId());
        CommentForm sentComment = sentPost.newComment("API comment", commentId, user_id);
        Assert.assertTrue(sentComment.isDisplayed(), "Post "+sentComment.getId()+" from user "+user_id+" doesn't exist");
        Logger.getInstance().info("Clicking 'like' on the post");
        sentPost.clickLikeBtn();
        Assert.assertTrue(ApiAppRequest.isLikedPost(sentPost.getId(),user_id), "Post "+sentPost.getId()+" doesn't have 'like reaction' from user "+user_id);
        Logger.getInstance().info("Sending request to delete post on the wall");
        ApiAppRequest.deletePost(sentPost.getId());
        Assert.assertTrue(sentPost.isNotDisplayed(),"Post "+sentPost.getId()+" from user "+user_id+" still exist");
    }
}
