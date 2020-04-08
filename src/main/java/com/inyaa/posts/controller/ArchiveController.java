package com.inyaa.posts.controller;

import com.inyaa.common.base.domain.Result;
import com.inyaa.posts.domain.vo.PostsVO;
import com.inyaa.posts.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Resource
    private PostsService postsService;

    @GetMapping("/archive/v1/list")
    public Result<PostsVO> getArchiveTotalByDateList(PostsVO postsVO) {
        return postsService.getArchiveTotalByDateList(postsVO);
    }

    @GetMapping("/year/v1/list")
    public Result<PostsVO> getArchiveGroupYearList(PostsVO postsVO) {
        return postsService.getArchiveGroupYearList(postsVO);
    }
}
