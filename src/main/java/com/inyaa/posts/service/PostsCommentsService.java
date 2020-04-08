package com.inyaa.posts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.base.domain.vo.UserSessionVO;
import com.inyaa.common.constant.Constants;
import com.inyaa.common.enums.ErrorEnum;
import com.inyaa.common.exception.BusinessException;
import com.inyaa.common.util.PageUtil;
import com.inyaa.auth.dao.AuthUserDao;
import com.inyaa.auth.domain.po.AuthUser;
import com.inyaa.posts.dao.PostsCommentsDao;
import com.inyaa.posts.dao.PostsDao;
import com.inyaa.posts.domain.po.PostsComments;
import com.inyaa.posts.domain.vo.PostsCommentsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author inyaa
 * @since 2019-09-03
 */
@Service
public class PostsCommentsService {

    @Resource
    private PostsCommentsDao postsCommentsDao;

    @Resource
    private PostsDao postsDao;

    @Resource
    private AuthUserDao authUserDao;

    public Result savePostsComments(PostsCommentsVO postsCommentsVO) {
        long id = 1;
        PostsComments postsComments = new PostsComments();
        postsComments.setAuthorId(id);
        postsComments.setContent(postsCommentsVO.getContent());
        postsComments.setPostsId(postsCommentsVO.getPostsId());
        postsComments.setCreateTime(LocalDateTime.now());

        String treePath;
        if (postsCommentsVO.getParentId() == null) {
            this.postsCommentsDao.insert(postsComments);
            treePath = postsComments.getId() + Constants.TREE_PATH;
        } else {
            PostsComments parentPostsComments = this.postsCommentsDao.selectById(postsCommentsVO.getParentId());
            if (parentPostsComments == null) {
                throw new BusinessException(ErrorEnum.DATA_NO_EXIST);

            }

            postsComments.setParentId(postsCommentsVO.getParentId());
            this.postsCommentsDao.insert(postsComments);

            treePath = parentPostsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
        }

        this.postsCommentsDao.updateById(postsComments.setTreePath(treePath));

        this.postsDao.incrementComments(postsCommentsVO.getPostsId());

        return Result.createWithSuccessMessage();
    }


    public Result replyComments(PostsCommentsVO postsCommentsVO) {
        AuthUser authUser = authUserDao.selectAdmin();
        PostsComments postsComments = postsCommentsDao.selectById(postsCommentsVO.getParentId())
                .setParentId(postsCommentsVO.getParentId())
                .setContent(postsCommentsVO.getContent())
                .setAuthorId(authUser.getId())
                .setCreateTime(LocalDateTime.now());
        this.postsCommentsDao.insert(postsComments);
        String treePath = postsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
        this.postsCommentsDao.updateById(postsComments.setTreePath(treePath));
        this.postsDao.incrementComments(postsCommentsVO.getPostsId());
        return Result.createWithSuccessMessage();
    }

    public Result getPostsCommentsByPostsIdList(PostsCommentsVO postsCommentsVO) {

        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsByPostsIdList(page, postsCommentsVO.getPostsId());

        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    public Result getPostsCommentsList(PostsCommentsVO postsCommentsVO) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(page, postsCommentsVO);
        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    public Result deletePostsComments(Long id) {
        this.postsCommentsDao.deleteById(id);
        return Result.createWithSuccessMessage();
    }

    public Result getPostsComment(Long id) {
        if (id != null) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(new PostsCommentsVO().setId(id));
        if (postsCommentsVOLis != null && postsCommentsVOLis.size() > 0) {
            return Result.createWithModel(postsCommentsVOLis.get(0));
        }
        return Result.createWithError();
    }
}
