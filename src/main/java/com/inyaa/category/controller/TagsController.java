package com.inyaa.category.controller;

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.util.ThrowableUtils;
import com.inyaa.common.validator.annotion.NotNull;
import com.inyaa.common.validator.group.Insert;
import com.inyaa.common.validator.group.Update;
import com.inyaa.category.domain.vo.TagsVO;
import com.inyaa.category.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @GetMapping("/tags/v1/list")
    public Result getTagsList(TagsVO tagsVO) {
        return this.tagsService.getTagsList(tagsVO);
    }

    @GetMapping("/tags-article-quantity/v1/list")
    public Result getTagsAndArticleQuantityList(TagsVO tagsVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.getTagsAndArticleQuantityList(tagsVO);
    }

    @GetMapping("/tags/v1/{id}")
    public Result getTags(@PathVariable Long id) {
        return this.tagsService.getTags(id);
    }

    @LoginRequired
    @PostMapping("/tags/v1/add")
    public Result saveTags(@Validated({Insert.class}) @RequestBody TagsVO tagsVO,BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.saveTags(tagsVO);
    }

    @LoginRequired
    @PutMapping("/tags/v1/update")
    public Result updateTags(@Validated({Update.class}) @RequestBody TagsVO tagsVO,BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return this.tagsService.updateTags(tagsVO);
    }

    @LoginRequired
    @DeleteMapping("/tags/v1/{id}")
    public Result updateTags(@PathVariable Long id) {
        return this.tagsService.deleteTags(id);
    }
}
