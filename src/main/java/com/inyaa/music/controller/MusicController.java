package com.inyaa.music.controller;

import com.inyaa.common.base.domain.Result;
import com.inyaa.music.service.MusicService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("/music/v1/list")
    public Result getPlayList(){
        return musicService.getPlayList();
    }

}
