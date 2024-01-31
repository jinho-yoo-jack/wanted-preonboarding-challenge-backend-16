package com.wanted.preonboarding.reservation.domain;

import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Embedded
	private NamePhone namePhone;

	@ElementCollection
	private List<ReserveItem> reserveItemList = new ArrayList<ReserveItem>();
	private Reservation(NamePhone namePhone) {
		this.namePhone = namePhone;
	}

	public static Reservation create(NamePhone namePhone) {
		return new Reservation(namePhone);
	}

	public static ReservationCancelEvent createCancelEvent(ReserveItem reserveItem) {
		return ReservationCancelEvent.create(reserveItem);
	}

	public static ReserveEvent createReserveEvent(UUID itemId, SeatInfo seatInfo) {
		return ReserveEvent.create(itemId,seatInfo);
	}

	public int cancel(UUID reserveItemNo){
		ReserveItem reserveItem = getReserveItem(reserveItemNo);
		int refundAmount = reserveItem.refundPolicyStub();
		reserveItem.cancel();
		return refundAmount;
	}

	public ReserveItem getReserveItem(UUID reserveItemNo) {
		return this.reserveItemList
			.stream()
			.filter(reserveItem->reserveItem.matchItem(reserveItemNo))
			.findFirst()
			.orElseThrow(()-> new RuntimeException("일치하는 예약된 아이템이 없습니다."));
	}

	public UUID reserve(Item item,int fee) {
		ReserveItem reserveItem = ReserveItem.create(item, fee);
		this.addReserveItem(reserveItem);
		return reserveItem.getNo();
	}

	private void addReserveItem(ReserveItem reserveItem) {
		this.reserveItemList.add(reserveItem);
	}




}
