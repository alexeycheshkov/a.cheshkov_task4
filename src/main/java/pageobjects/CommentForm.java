package pageobjects;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.time.Duration;

public class CommentForm extends Form {
    private String id;
    private String authorId;
    private String postId;
    private String postAuthorId;
    private IButton btnShowNextReplies;

    protected CommentForm(String name, String id, String authorId, String postId, String postAuthorId) {
        super(By.xpath(String.format("//div[@id='page_wall_posts']//div[contains(@class,'replies_list')]/div[@data-post-id='%s_%s' and contains(@class,'reply')]", authorId, id)), name);
        this.id = id;
        this.postId = postId;
        this.postAuthorId = postAuthorId;
        this.authorId = authorId;
        btnShowNextReplies = getElementFactory().getButton(By.xpath(String.format("//div[@id='page_wall_posts']/div[@data-post-id='%s_%s']//a[contains(@class,'replies_next_main')]", postAuthorId, postId)), "Next replies button");
    }

    @Override
    public boolean isDisplayed() {
        if (btnShowNextReplies.state().waitForDisplayed(Duration.ofSeconds(5))) {
            btnShowNextReplies.click();
        }
        return this.state().waitForDisplayed(Duration.ofSeconds(5));
    }

    public String getId() {
        return id;
    }
}
