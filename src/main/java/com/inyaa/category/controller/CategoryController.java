package com.inyaa.category.controller;

import com.inyaa.common.validator.annotion.NotNull;
import com.inyaa.common.validator.group.Insert;
import com.inyaa.common.validator.group.Update;
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

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.common.util.ThrowableUtils;
import com.inyaa.category.domain.vo.CategoryVO;
import com.inyaa.category.service.CategoryService;

import javax.annotation.Resource;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @LoginRequired
    @PostMapping("/category/v1/add")
    public Result saveCategory(@Validated({Insert.class}) @RequestBody CategoryVO categoryVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryService.saveCategory(categoryVO);
    }


    @LoginRequired
    @GetMapping("/statistics/v1/list")
    public  Result statisticsList(CategoryVO categoryVO){
        return categoryService.statisticsList(categoryVO);
    }

    @LoginRequired
    @PutMapping("/category/v1/update")
    public Result updateCategory(@Validated({Update.class}) @RequestBody CategoryVO categoryVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryService.updateCategory(categoryVO);
    }

    @LoginRequired
    @GetMapping("/category-tags/v1/{id}")
    public Result getCategoryTags(@PathVariable(value = "id", required = true) Long id) {
        return categoryService.getCategoryTags(id);
    }

    @LoginRequired
    @GetMapping("/category-tags/v1/list")
    public Result getCategoryTagsList(CategoryVO categoryVO) {
        return categoryService.getCategoryTagsList(categoryVO);
    }

    @GetMapping("/category/v1/{id}")
    public Result getCategory(@PathVariable(value = "id", required = true) Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/category/v1/list")
    public Result getCategoryList(CategoryVO categoryVO) {
        return categoryService.getCategoryList(categoryVO);
    }

    @LoginRequired
    @DeleteMapping("/category/v1/{id}")
    public Result deleteCategory(@PathVariable(value = "id", required = true) Long id) {
        return categoryService.deleteCategory(id);
    }

}