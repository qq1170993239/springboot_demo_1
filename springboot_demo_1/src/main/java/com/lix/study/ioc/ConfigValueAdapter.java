package com.lix.study.ioc;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lix.study.common.conts.DemoConts;
import com.lix.study.config.redis.service.RedisCacheble;
import com.lix.study.db.service.DBCommonQueryService;
import com.lix.study.sdk.common.utils.StringUtils;

/**
 * ConfigValue 实现类
 * @author lix
 */
@Component
public class ConfigValueAdapter extends InstantiationAwareBeanPostProcessorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ConfigValueAdapter.class);
	
	private static final String SQLPREFIX= "select * from lix_base_config g where ";

    @Autowired
    private DBCommonQueryService dBCommonQueryService;
    
    @Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		for (Field field : bean.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(ConfigValue.class)) {
				ConfigValue cfg = field.getAnnotation(ConfigValue.class);
				List<Map<String, Object>> result = null;
				try {
					result = this.getConfigValue(cfg.code(), cfg.type());
					if (cfg.required() && result == null) {
						// 从配置表获取数据失败
						throw new ValidationException("Failed to get data from the configuration table");
					}
					field.setAccessible(true);
					Object obj = parseValue(field.getType(), result);
					field.set(bean, obj);
				} catch (Exception e) {
					logger.error("Failed to set value for " + bean.getClass().getSimpleName() + "." + field.getName()
							+ " with " + result, e);
					return false;
				}
			}
		}
		return true;
	}

    @RedisCacheble(key = "getConfigValue")
	private List<Map<String, Object>> getConfigValue(String code, String type) {
		String sql = "";
		if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(code)) {
			sql = SQLPREFIX + "g.code='" + code + "' and g.type='" + type + "'";
		} else if (StringUtils.isNotEmpty(type) && StringUtils.isEmpty(code)) {
			sql = SQLPREFIX + "g.type='" + type + "'";
		} else {
			return Collections.emptyList();
		}
		return dBCommonQueryService.execSqlData(sql);
	}

    /**
     * 稍微智能地适配到各种格式，如：
     * 使用List,Map,String 接收时自动封装数据成对应类型
     * @param cls
     * @param list
     * @return
     */
    private Object parseValue(Class<?> cls, List<Map<String, Object>> list) {
        if (cls.isAssignableFrom(List.class)) {
            return JSON.parseObject(JSON.toJSONString(list), new TypeReference<List<Map<String, Object>>>(){});

        } else if (cls.isAssignableFrom(Map.class)) {
            Map<String, String> valueMap = new HashMap<>();
            for (Map<String, Object> map : list) {
                valueMap.put(String.valueOf(map.get(DemoConts.CODE)), String.valueOf(map.get(DemoConts.VALUE)));
            }
            String json = JSON.toJSONString(valueMap);
            return JSON.parseObject(json, cls);
        } else if (cls.isAssignableFrom(String.class)) {
            return list.get(0).get(DemoConts.VALUE);
        } else {
            return JSON.parseObject((String)list.get(0).get(DemoConts.VALUE), cls);
        }
    }
}
