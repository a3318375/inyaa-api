package com.inyaa.category.domain.vo;

import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.validator.Messages;
import com.inyaa.common.validator.annotion.NotBlank;
import com.inyaa.common.validator.group.Insert;
import com.inyaa.common.validator.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CategoryVO extends BaseVO<CategoryVO> {

    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private List<TagsVO> tagsList;

    private Integer total;
}
