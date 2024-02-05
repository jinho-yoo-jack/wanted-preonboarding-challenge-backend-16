package com.wanted.preonboarding.base.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	// core 사이즈 만큼 일 처리
	// 기본 스레드가 처리할 수 있는 작업량 넘어설 경우 큐에서 대기
	// 큐의 크기가 넘친다면 MaxSize 만큼 스레드 추가 생성
	// -----------------------예시----------------------
	// 5개로 작업 -> 20개인 큐에서 대기 -> 넘침 -> 30개로 늘림
	// 큐에서 요청이 넘긴다면 RejectExecutionException 발생 -> CallerRunsPolicy 처리
	// 요청한 Caller Thread에서 직접 처리

	@Bean(name = "taskExecutor1")
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);  // 스레드 풀에 속한 기본 스레드 개수
		taskExecutor.setQueueCapacity(20);  // 이벤트 대기 큐 크기
		taskExecutor.setMaxPoolSize(30); // 최대 스레드 개수
		taskExecutor.setThreadNamePrefix("Executor-");
		// ThreadPoolExecutor.CallerRunsPolicy()는 RejectedExecutionHandler 인터페이스의 구현체
		// 작업 큐가 가득 차서 더 이상 새로운 작업을 받아들일 수 없을 때의 정책을 정의합니다.
		// 이 정책에 따르면, 작업 큐가 가득 찼을 때 새로 들어온 작업은 작업을 요청한 쓰레드에서 직접 처리
		// 밑에 구현체 때문에 5개 스레드 꽉참 -> 큐 대기 -> 큐 꽉참 -> 30개 쓰레드 활용 -> 그래도 큐 꽉참 일때
		// 예외가 발생하는데, 이때 예외 처리를 담당한다. 예외 시 이 작업을 요청한 스레드에서 직접 처리하도록
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true); // shutdown시 queue에 남아있는 모든 작업이 완료된 후 shutdown!
		taskExecutor.setAwaitTerminationSeconds(60);    // shutdown 최대 60초 대기
		return taskExecutor;
	}
}