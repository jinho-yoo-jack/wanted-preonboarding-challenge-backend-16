package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.aop.ResultCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticket.global.common.BaseEntity;
import com.wanted.preonboarding.ticket.global.common.ReserveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private Date start_date;
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;


    public void isReserve(ReserveStatus status) {
        if(!this.isReserve.equalsIgnoreCase(status.getValue())) {
            throw new ServiceException(ResultCode.RESERVE_NOT_VALID);
        }
    }

    public void checkHasEnoughBalance(long balance) {
        if(balance - this.price < 0) {
            throw new ServiceException(ResultCode.NOT_ENOUGH_BALANCE);
        }
    }
}
