package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    //定义第三方的Bean
    //redisTemplate访问数据库需要创建连接，故需要注入RedisConnectionFactory连接工厂
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) { //返回值：需要装配到容器中的对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        //做完设置触发生效
        template.afterPropertiesSet();

        return template;
    }

}
