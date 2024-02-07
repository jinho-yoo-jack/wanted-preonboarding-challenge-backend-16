package com.wanted.preonboarding.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.wanted.preonboarding.ticket.domain.dto.AlarmMessage;

@Configuration
public class RedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(AlarmMessage.class));
		return redisTemplate;
	}

	// @Bean
	// MessageListenerAdapter messageListenerAdapter() {
	// 	return new MessageListenerAdapter(new PerformanceCancelSubService());
	// }

	// @Bean
	// public StringRedisSerializer stringRedisSerializer() {
	// 	return new StringRedisSerializer();
	// }

	@Bean
	public RedisMessageListenerContainer redisMessageListener(
		RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		return container;
	}
}
