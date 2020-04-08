package com.inyaa.config.domain.vo;

import com.inyaa.common.base.domain.vo.BaseVO;
import com.inyaa.common.validator.annotion.IntegerNotNull;
import com.inyaa.common.validator.annotion.NotBlank;
import com.inyaa.config.domain.validator.QueryConfigList;
import com.inyaa.config.domain.validator.UpdateConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ConfigVO extends BaseVO<ConfigVO> {
    private Integer type;

    @NotBlank(groups = {UpdateConfig.class})
    private String configKey;

    @NotBlank(groups = {UpdateConfig.class})
    private String configValue;

}
