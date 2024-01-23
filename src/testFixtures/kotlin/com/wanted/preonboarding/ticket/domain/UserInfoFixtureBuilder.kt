package com.wanted.preonboarding.ticket.domain

data class UserInfoFixtureBuilder(
    val name: String = "홍길동",
    val phoneNumber: String = "010-1234-5678",
) {
    fun build(): UserInfo {
        return UserInfo(
            name = name,
            phoneNumber = phoneNumber,
        )
    }
}
