package com.wanted.preonboarding.reservation.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Item {

	@Column(nullable = false)
	private UUID id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int round;

	@Embedded
	private SeatInfo seatInfo;


	public static Item create(UUID id, String name,int round,SeatInfo seatInfo){
	        return new Item(id,name,round,seatInfo);
	    }


}
