package com.wanted.preonboarding.base.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RsData<T> {
	private String resultCode;
	private String msg;
	private T data;

	public static <T> RsData<T> of(String resultCode, String msg, T data) {
		return new RsData<>(resultCode, msg, data);
	}

	public static <T> RsData<T> of(String resultCode, String msg) {
		return of(resultCode, msg, null);
	}

	@JsonIgnore // Json 형태 미포함
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}

	@JsonIgnore
	public boolean isFail() {
		return !isSuccess();
	}
}