package cn.sias.community.ruike.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory factory){
        //注入连接工厂
        RedisTemplate<String ,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        //设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        //设置hash的key的序列化
        template.setHashKeySerializer(RedisSerializer.string());
        //设置hash的value的序列化
        template.setHashValueSerializer(RedisSerializer.json());
        //设置完之后要触发一下,要生效
        template.afterPropertiesSet();
        return template;
    }

}
