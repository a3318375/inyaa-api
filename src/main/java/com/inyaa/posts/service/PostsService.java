package com.inyaa.posts.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.base.domain.vo.UserSessionVO;
import com.inyaa.common.constant.Constants;
import com.inyaa.common.enums.ErrorEnum;
import com.inyaa.common.enums.OperateEnum;
import com.inyaa.common.exception.BusinessException;
import com.inyaa.common.util.*;
import com.inyaa.category.dao.TagsDao;
import com.inyaa.category.domain.po.Tags;
import com.inyaa.category.domain.vo.TagsVO;
import com.inyaa.log.dao.AuthUserLogDao;
import com.inyaa.log.domain.vo.AuthUserLogVO;
import com.inyaa.posts.dao.PostsAttributeDao;
import com.inyaa.posts.dao.PostsDao;
import com.inyaa.posts.dao.PostsTagsDao;
import com.inyaa.posts.domain.po.Posts;
import com.inyaa.posts.domain.po.PostsAttribute;
import com.inyaa.posts.domain.po.PostsTags;
import com.inyaa.posts.domain.vo.*;
import com.inyaa.util.Markdown2HtmlUtil;
import com.inyaa.util.PreviewTextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@Service
@Slf4j
public class PostsService {

    @Resource
    private PostsDao postsDao;

    @Resource
    private PostsAttributeDao postsAttributeDao;

    @Resource
    private TagsDao tagsDao;

    @Resource
    private PostsTagsDao postsTagsDao;

    @Resource
    private AuthUserLogDao authUserLogDao;
    private static long userId = 1;
    public Result savePosts(PostsVO postsVO) {
        String html = Markdown2HtmlUtil.html(postsVO.getContent());
        Posts posts = new Posts();
        posts.setTitle(postsVO.getTitle()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()).setThumbnail(postsVO.getThumbnail());
        posts.setStatus(postsVO.getStatus()).setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
                .setAuthorId(userId).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());
        postsDao.insert(posts);
        postsVO.setId(posts.getId());

        postsAttributeDao.insert(new PostsAttribute().setContent(postsVO.getContent()).setPostsId(posts.getId()));
        List<TagsVO> tagsList = postsVO.getTagsList();
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    Tags tags = new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());// saveMenu
                    tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                }
                postsTagsDao.insert(new PostsTags().setPostsId(posts.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
            });
        }

        return Result.createWithSuccessMessage();
    }

    public Result updatePosts(PostsVO postsVO) {
        String html = Markdown2HtmlUtil.html(postsVO.getContent());
        Posts posts1 = postsDao.selectOne(new LambdaQueryWrapper<Posts>().eq(Posts::getId, postsVO.getId()));
        if (posts1 == null) {
            throw new BusinessException(ErrorEnum.DATA_NO_EXIST);
        }

        posts1.setTitle(postsVO.getTitle()).setUpdateTime(LocalDateTime.now()).setThumbnail(postsVO.getThumbnail());
        posts1.setStatus(postsVO.getStatus()).setSyncStatus(Constants.NO).setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
                .setAuthorId(userId).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());

        postsDao.updateById(posts1);
        Wrapper<PostsAttribute> wrapper = new LambdaUpdateWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, posts1.getId());
        if (postsAttributeDao.selectCount(wrapper) > 0) {
            postsAttributeDao.update(new PostsAttribute().setContent(postsVO.getContent()), wrapper);
        } else {
            postsAttributeDao.insert(new PostsAttribute().setContent(postsVO.getContent()).setPostsId(posts1.getId()));
        }

        List<TagsVO> tagsList = postsVO.getTagsList();

        if (!CollectionUtils.isEmpty(tagsList)) {
            List<PostsTags> originalList = postsTagsDao.selectList(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()));
            List<PostsTags> categoryTagsList = originalList.stream().filter(postsTags -> !postsVO.getTagsList().stream().map(BaseVO::getId).collect(Collectors.toList())
                    .contains(postsTags.getTagsId())).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(categoryTagsList)) {
                categoryTagsList.forEach(categoryTags -> {
                    postsTagsDao.deleteById(categoryTags.getId());
                });
            }

            tagsList.forEach(tagsVO -> {
                if (tagsVO.getId() == null) {
                    // saveMenu
                    Tags tags = new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
                    tagsDao.insert(tags);
                    tagsVO.setId(tags.getId());
                    postsTagsDao.insert(new PostsTags().setPostsId(posts1.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
                } else {
                    PostsTags postsTags = postsTagsDao.selectOne(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()).eq(PostsTags::getTagsId, tagsVO.getId()));
                    if (postsTags == null) {
                        postsTagsDao.insert(new PostsTags().setPostsId(posts1.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
                    }
                }
            });
        } else {

            postsTagsDao.delete(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()));
        }
        return Result.createWithSuccessMessage();
    }

    public Result deletePosts(Long id) {

        Posts posts = postsDao.selectById(id);
        if (posts == null) {
            throw new BusinessException(ErrorEnum.DATA_NO_EXIST);
        }

        postsDao.deleteById(id);
        postsAttributeDao.delete(new LambdaUpdateWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, id));
        postsTagsDao.delete(new LambdaUpdateWrapper<PostsTags>().eq(PostsTags::getPostsId, id));

        return Result.createWithSuccessMessage();
    }

    public Result getPosts(Long id) {
        Posts posts = postsDao.selectOneById(id);
        if (posts == null) {
            throw new BusinessException(ErrorEnum.DATA_NO_EXIST);
        }

        PostsVO postsVO = new PostsVO();
        postsVO.setId(posts.getId())
                .setCreateTime(posts.getCreateTime())
                .setSummary(posts.getSummary())
                .setTitle(posts.getTitle())
                .setThumbnail(posts.getThumbnail())
                .setIsComment(posts.getIsComment())
                .setViews(posts.getViews())
                .setComments(posts.getComments())
                .setCategoryId(posts.getCategoryId())
                .setWeight(posts.getWeight())
                .setCategoryName(posts.getCategoryName());

        PostsAttribute postsAttribute = postsAttributeDao.selectOne(new LambdaQueryWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, posts.getId()));
        if (postsAttribute != null) {
            postsVO.setContent(postsAttribute.getContent());
        }
        List<PostsTags> postsTagsList = postsTagsDao.selectList(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts.getId()));
        List<TagsVO> tagsVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(postsTagsList)) {
            postsTagsList.forEach(postsTags -> {
                Tags tags = tagsDao.selectById(postsTags.getTagsId());
                tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
            });
        }

        postsVO.setTagsList(tagsVOList);

        postsDao.incrementView(posts.getId());
        return Result.createWithModel(postsVO);
    }

    public Result<PostsVO> getPostsList(PostsVO postsVO) {
        postsVO = Optional.ofNullable(postsVO).orElse(new PostsVO());
        Page page = Optional.of(PageUtil.checkAndInitPage(postsVO)).orElse(PageUtil.initPage());
        if (StringUtils.isNotBlank(postsVO.getKeywords())) {
            postsVO.setKeywords("%" + postsVO.getKeywords() + "%");
        }
        if (StringUtils.isNoneBlank(postsVO.getTitle())) {
            postsVO.setTitle("%" + postsVO.getTitle() + "%");
        }
        List<PostsVO> postsVOList = postsDao.selectPostsList(page, postsVO);
        if (!CollectionUtils.isEmpty(postsVOList)) {
            postsVOList.forEach(postsVO1 -> {
                List<PostsTags> postsTagsList = postsTagsDao.selectList(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, postsVO1.getId()));
                List<TagsVO> tagsVOList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(postsTagsList)) {
                    postsTagsList.forEach(postsTags -> {
                        Tags tags = tagsDao.selectById(postsTags.getTagsId());
                        tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
                    });
                }
                postsVO1.setTagsList(tagsVOList);
            });
        }

        return Result.createWithPaging(postsVOList, PageUtil.initPageInfo(page));
    }

    public Result<PostsVO> getArchiveTotalByDateList(PostsVO postsVO) {
        List<PostsVO> postsVOList = postsDao.selectArchiveTotalGroupDateList();
        postsVOList.forEach(obj -> {
            // 查询每一个时间点中的文章
            obj.setArchivePosts(postsDao.selectByArchiveDate(obj.getArchiveDate()));
        });
        return Result.createWithModels(postsVOList);
    }

    public Result getArchiveGroupYearList(PostsVO postsVO) {
        List<PostsVO> postsVOList = postsDao.selectArchiveGroupYearList();
        return Result.createWithModels(postsVOList);
    }

    public Result updatePostsStatus(PostsVO postsVO) {
        postsDao.updateById(new Posts().setId(postsVO.getId()).setStatus(postsVO.getStatus()).setUpdateTime(LocalDateTime.now()));
        return Result.createWithSuccessMessage();
    }

    public Result getHotPostsList(PostsVO postsVO) {
        postsVO = Optional.ofNullable(postsVO).orElse(new PostsVO());
        Page page = Optional.of(PageUtil.checkAndInitPage(postsVO)).orElse(PageUtil.initPage());
        if (StringUtils.isNotBlank(postsVO.getKeywords())) {
            postsVO.setKeywords("%" + postsVO.getKeywords() + "%");
        }
        List<AuthUserLogVO> logVOList = authUserLogDao.selectListByCode(OperateEnum.GET_POSTS_DETAIL.getCode());
        List<Long> ids = new ArrayList<>();
        logVOList.forEach(obj -> {
            JSONObject json = JSONObject.parseObject(obj.getParameter());
            ids.add(json.getLong("id"));
        });
        postsDao.selectPage(page, new QueryWrapper<Posts>().in("id", ids));
        return Result.createWithPaging(page.getRecords(), PageUtil.initPageInfo(page));
    }

}
