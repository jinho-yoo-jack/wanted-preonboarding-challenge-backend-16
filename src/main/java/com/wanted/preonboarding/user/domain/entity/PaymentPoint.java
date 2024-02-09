package com.wanted.preonboarding.user.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class PaymentPoint extends Payment{

}
