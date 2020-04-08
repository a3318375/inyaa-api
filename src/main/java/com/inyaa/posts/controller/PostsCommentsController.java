package com.inyaa.posts.controller;

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.validator.annotion.NotNull;
import com.inyaa.posts.domain.validator.InsertPosts;
import com.inyaa.posts.domain.validator.InsertPostsComments;
import com.inyaa.posts.domain.validator.QueryPostsComments;
import com.inyaa.posts.domain.vo.PostsCommentsVO;
import com.inyaa.posts.service.PostsCommentsService;
import com.inyaa.system.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author inyaa
 * @since 2019-09-03
 */
@RestController
@RequestMapping("/comments")
public class PostsCommentsController {

    @Resource
    private PostsCommentsService postsCommentsService;

    @LoginRequired(role = RoleEnum.USER)
    @PostMapping("/comments/v1/add")
    public Result savePostsComments(@Validated({InsertPostsComments.class}) @RequestBody PostsCommentsVO postsCommentsVO) {
        return this.postsCommentsService.savePostsComments(postsCommentsVO);
    }

    @LoginRequired(role = RoleEnum.USER)
    @PostMapping("/admin/v1/reply")
    public Result replyComments(@RequestBody PostsCommentsVO postsCommentsVO) {
        return this.postsCommentsService.replyComments(postsCommentsVO);
    }

    @LoginRequired
    @DeleteMapping("/comments/v1/{id}")
    public Result deletePostsComments(@PathVariable(value = "id") Long id) {
        return this.postsCommentsService.deletePostsComments(id);
    }

    @LoginRequired
    @GetMapping("/comments/v1/{id}")
    public Result getPostsComment(@PathVariable(value = "id") Long id) {
        return this.postsCommentsService.getPostsComment(id);
    }

    @GetMapping("/comments-posts/v1/list")
    public Result getPostsCommentsByPostsIdList(@Validated({QueryPostsComments.class}) PostsCommentsVO postsCommentsVO) {
        return this.postsCommentsService.getPostsCommentsByPostsIdList(postsCommentsVO);
    }

    @LoginRequired
    @GetMapping("/comments/v1/get")
    public Result getPostsCommentsList(PostsCommentsVO postsCommentsVO) {
        return this.postsCommentsService.getPostsCommentsList(postsCommentsVO);
    }
}
