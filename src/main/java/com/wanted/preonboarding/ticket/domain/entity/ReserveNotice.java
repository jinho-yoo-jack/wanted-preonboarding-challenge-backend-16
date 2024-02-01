package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReserveNoticePK.class)
@Table(name = "reserve_notice")
public class ReserveNotice {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name")
    private User userName;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performanceId;

    @Column(nullable = false, name = "notice_yn")
    private String noticeYn;

    public static ReserveNotice from(AlarmInfo info) {
        return ReserveNotice.builder()
                .userName(User.builder().userName(info.getName()).build())
                .performanceId(Performance.builder().id(info.getPerformanceId()).build())
                .noticeYn(info.getNoticeYn())
                .build();
    }
}
