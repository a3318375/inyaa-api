package com.inyaa.category.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.constant.Constants;
import com.inyaa.common.enums.ErrorEnum;
import com.inyaa.common.exception.BusinessException;
import com.inyaa.common.util.PageUtil;
import com.inyaa.category.dao.CategoryTagsDao;
import com.inyaa.category.dao.TagsDao;
import com.inyaa.category.domain.po.CategoryTags;
import com.inyaa.category.domain.po.Tags;
import com.inyaa.category.domain.vo.TagsVO;
import com.inyaa.posts.dao.PostsTagsDao;
import com.inyaa.posts.domain.po.PostsTags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@Service
public class TagsService  {

    @Resource
    private TagsDao tagsDao;

    @Resource
    private PostsTagsDao postsTagsDao;

    @Resource
    private CategoryTagsDao categoryTagsDao;

    public Result<TagsVO> getTagsAndArticleQuantityList(TagsVO tagsVO) {
        List<Tags> records = this.tagsDao.selectList(new LambdaQueryWrapper<Tags>().orderByDesc(Tags::getId));

        List<TagsVO> tagsList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(tags -> {
                Integer total = postsTagsDao.selectCount(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getTagsId, tags.getId()));
                tagsList.add(new TagsVO().setId(tags.getId()).setPostsTotal(total).setName(tags.getName()));
            });
        }

        return Result.createWithModels(tagsList);
    }

    public Result<TagsVO> getTagsList(TagsVO tagsVO) {

        List<TagsVO> tagsList = new ArrayList<>();
        if (tagsVO == null || tagsVO.getPage() == null || tagsVO.getSize() == null) {
            List<Tags> records = this.tagsDao.selectList(new LambdaQueryWrapper<Tags>().orderByDesc(Tags::getId));
            if (!CollectionUtils.isEmpty(records)) {
                records.forEach(tags -> {
                    tagsList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
                });
            }
            return Result.createWithModels(tagsList);
        }
        LambdaQueryWrapper<Tags> tagsLambdaQueryWrapper = new LambdaQueryWrapper<Tags>();
        if (StringUtils.isNotBlank(tagsVO.getKeywords())){
            tagsLambdaQueryWrapper.like(Tags::getName, tagsVO.getKeywords());
        }
        if (StringUtils.isNotBlank(tagsVO.getName())){
            tagsLambdaQueryWrapper.eq(Tags::getName, tagsVO.getName());
        }
        Page page = PageUtil.checkAndInitPage(tagsVO);
        IPage<TagsVO> tagsIPage = this.tagsDao.selectPage(page,tagsLambdaQueryWrapper.orderByDesc(Tags::getId));
        return Result.createWithPaging(tagsIPage.getRecords(), PageUtil.initPageInfo(page));
    }

    public Result<TagsVO> getTags(Long id) {

        Tags tags = this.tagsDao.selectById(id);
        return Result.createWithModel(new TagsVO().setId(tags.getId()).setName(tags.getName()));
    }

    public Result<TagsVO> updateTags(TagsVO tagsVO) {

        Integer count  = this.tagsDao.selectCount(new LambdaQueryWrapper<Tags>().eq(Tags::getId,tagsVO.getId()));
        if (count.equals(Constants.ZERO)) {
            throw new BusinessException(ErrorEnum.DATA_NO_EXIST);
        }

        this.tagsDao.updateById(new Tags().setId(tagsVO.getId()).setName(tagsVO.getName()).setUpdateTime(LocalDateTime.now()));
        return Result.createWithSuccessMessage();
    }

    public Result<TagsVO> deleteTags(Long id) {

        this.tagsDao.deleteById(id);
        this.categoryTagsDao.delete(new LambdaQueryWrapper<CategoryTags>().eq(CategoryTags::getTagsId, id));
        this.postsTagsDao.delete(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getTagsId, id));

        return Result.createWithSuccessMessage();
    }

    public Result<TagsVO> saveTags(TagsVO tagsVO) {

        this.tagsDao.insert(new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
        return Result.createWithSuccessMessage();
    }
}
