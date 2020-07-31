package com.leo.kakao.exception

class SearchStopException(var errorCode: Int = 0, private var errorMessage: String = "새로운 검색으로 중지 합니다.") : RuntimeException(errorMessage)
