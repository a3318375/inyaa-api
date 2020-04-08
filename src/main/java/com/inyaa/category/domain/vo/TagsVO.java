package com.inyaa.category.domain.vo;

import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.validator.annotion.NotBlank;
import com.inyaa.common.validator.group.Insert;
import com.inyaa.common.validator.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @date: 2019/9/1 20:52
 * @modified:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TagsVO extends BaseVO<TagsVO> {

    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 文章总数
     */
    private Integer postsTotal;
}
