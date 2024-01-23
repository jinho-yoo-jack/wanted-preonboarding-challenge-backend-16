package com.wanted.preonboarding.ticket.infra.entity.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.wanted.preonboarding.ticket.infra"])
@EnableJpaRepositories(basePackages = ["com.wanted.preonboarding.ticket.infra"])
class JpaConfig
