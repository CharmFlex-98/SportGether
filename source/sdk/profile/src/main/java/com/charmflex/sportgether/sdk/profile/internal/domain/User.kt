package com.charmflex.sportgether.sdk.profile.internal.domain

class User {
}


enum class UserProfileIconType {
    FOREIGNER_LONG_HAIR_GIRL,
    CHINESE_GIRL,
    MALAY_BOY,
    CHINESE_BOY,
    FOREIGNER_SHORT_HAIR_GIRL,
    BALD_UNCLE,
    MALAY_GIRL,
    INDIAN_BOY,
    INDIAN_GIRL;

    companion object {
        fun defaultType() = CHINESE_BOY
        fun fromString(from: String): UserProfileIconType {
            return when (from) {
                FOREIGNER_LONG_HAIR_GIRL.toString() -> FOREIGNER_LONG_HAIR_GIRL
                CHINESE_GIRL.toString() -> CHINESE_GIRL
                MALAY_BOY.toString() -> MALAY_BOY
                CHINESE_BOY.toString() -> CHINESE_BOY
                FOREIGNER_SHORT_HAIR_GIRL.toString() -> FOREIGNER_SHORT_HAIR_GIRL
                BALD_UNCLE.toString() -> BALD_UNCLE
                MALAY_GIRL.toString() -> BALD_UNCLE
                INDIAN_BOY.toString() -> INDIAN_BOY
                INDIAN_GIRL.toString() -> INDIAN_GIRL
                else -> defaultType()
            }
        }
    }
}