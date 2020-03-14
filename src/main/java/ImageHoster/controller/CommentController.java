package ImageHoster.controller;
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments' and add comments in the database, it is of the GET type
    //Call the addComment() method in the business logic to add the comment
    //Direct to the same page showing the new comments with old comments
    @RequestMapping("/image/{imageId}/{imageTitle}/comments")
    public RedirectView addComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle, @RequestParam("comment") String comment, Comment newComment, HttpSession session, Model model) {
        Image image = imageService.getImage(imageId);
        User user = (User) session.getAttribute("loggeduser");
        newComment.setImage(image);
        newComment.setUser(user);
        newComment.setText(comment);
        commentService.addComment(newComment);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/images/"+ imageId);
        return redirectView;
    }
}
