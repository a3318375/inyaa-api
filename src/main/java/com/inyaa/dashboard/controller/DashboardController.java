package com.inyaa.dashboard.controller;

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.dashboard.service.DashboardService;
import com.inyaa.log.domain.vo.AuthUserLogVO;
import com.inyaa.posts.domain.vo.PostsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: inyaa
 * @date: 2019/09/03 19:25
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @LoginRequired
    @GetMapping("/blog-total/v1/quantity")
    public Result getPostsQuantityTotal(PostsVO postsVO) {
        return dashboardService.getPostsQuantityTotal();
    }

    @LoginRequired
    @GetMapping("/post-statistics/v1/list")
    public Result getPostsStatistics(@Valid AuthUserLogVO authUserLogVO) {
        return dashboardService.getPostsStatistics(authUserLogVO);
    }

    @LoginRequired
    @GetMapping("/post-ranking/v1/list")
    public Result getPostsRanking(@Valid AuthUserLogVO authUserLogVO) {
        return dashboardService.getPostsRanking(authUserLogVO);
    }

}
