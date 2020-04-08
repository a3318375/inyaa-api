package com.inyaa.music.service;

import com.inyaa.common.base.domain.Result;
import com.inyaa.music.util.MusicUtil;
import org.springframework.stereotype.Service;

@Service
public class MusicService {
    public Result getPlayList() {
        return Result.createWithModels(MusicUtil.getPlayList());
    }
}
