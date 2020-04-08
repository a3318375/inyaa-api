package com.inyaa.posts.domain.vo;

import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.validator.annotion.NotBlank;
import com.inyaa.common.validator.annotion.NotNull;
import com.inyaa.posts.domain.validator.InsertPostsComments;
import com.inyaa.posts.domain.validator.QueryPostsComments;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 * @author inyaa
 * @since 2019-09-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PostsCommentsVO extends BaseVO<PostsCommentsVO> {

    private Long authorId;

    @NotBlank(groups = {InsertPostsComments.class})
    private String content;

    private Long parentId;

    private Integer status;

    @NotNull(groups = {InsertPostsComments.class, QueryPostsComments.class})
    private Long postsId;

    private String treePath;

    private String authorName;

    private String authorAvatar;

    private String parentUserName;

    private LocalDateTime createTime;

}
