package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class User {
    @Id
    @Column(nullable = false, name = "user_name")
    private String userName;
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "total_amount")
    private int totalAmount;
}
