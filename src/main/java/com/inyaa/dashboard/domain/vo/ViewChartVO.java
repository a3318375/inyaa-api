package com.inyaa.dashboard.domain.vo;

import com.inyaa.log.domain.vo.AuthUserLogVO;
import com.inyaa.posts.domain.vo.PostsVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: inyaa
 * @date: 2020/1/16 21:32
 */
@Data
@Accessors(chain = true)
public class ViewChartVO {

    private List<ViewChartSpotVO> viewRecordList;

}
