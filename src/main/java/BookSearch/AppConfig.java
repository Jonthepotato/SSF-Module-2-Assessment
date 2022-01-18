package BookSearch;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import BookSearch.Model.BooksList;

@Configuration
public class AppConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    
    @Bean
    // (Constants.TODO_REDIS)
    public RedisTemplate<String, BooksList> createRedisTemplate() {

        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDatabase);

        final String redisPassword = System.getenv("redispass");
        if (null != redisPassword) {
            redisConfig.setPassword(redisPassword);
        }

        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new  JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, BooksList> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        // template.setKeySerializer(new StringRedisSerializer());
        // template.setValueSerializer(new StringRedisSerializer());
        return template;
}
}
