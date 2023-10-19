package com.tyf.bookreaderplus.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 跨域配置属性
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
@ConfigurationProperties(prefix = "br.cors")
public class CorsProperties{
    private List<String> allowOrigins;
}
