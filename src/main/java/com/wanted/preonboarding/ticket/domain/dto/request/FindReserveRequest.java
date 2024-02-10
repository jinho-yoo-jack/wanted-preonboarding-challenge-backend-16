package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindReserveRequest {
	private String name;
	private String phoneNumber;
}
