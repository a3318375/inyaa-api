package com.inyaa.links.domain.vo;

import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.validator.annotion.NotBlank;
import com.inyaa.links.domain.validator.InsertLink;
import com.inyaa.links.domain.validator.UpdateLink;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class FriendshipLinkVO extends BaseVO<FriendshipLinkVO> {

    @NotBlank(groups = {UpdateLink.class})
    private Long id;

    private String title;

    @NotBlank(groups = {InsertLink.class, UpdateLink.class})
    private String name;

    @NotBlank(groups = {InsertLink.class, UpdateLink.class})
    private String href;

    private String logo;

    private Integer sort;

    private String description;


}
