package com.wanted.preonboarding.common.util;

import lombok.Getter;

@Getter
public enum ResponseType {
	SUCCESS(200, "Success"),
	CREATED(201, "Created"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	SERVER_ERROR(500, "Internal Server Error");

	private final int statusCode;
	private final String message;

	ResponseType(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
