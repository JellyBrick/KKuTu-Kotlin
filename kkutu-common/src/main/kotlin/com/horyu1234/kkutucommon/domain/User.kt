package com.horyu1234.kkutucommon.domain

import java.util.*
import javax.persistence.*

@Table(name = "users")
@Entity
data class User(
        @Id
        @GeneratedValue
        val userId: UUID,

        @Column(nullable = false)
        val loginVendor: String,

        @Column(nullable = false)
        val loginVendorId: Int,

        @Column(nullable = true)
        val nickname: String,

        @Column(nullable = false)
        val money: Int,

        @Column(nullable = false)
        val exp: Long,

        @Column(nullable = true)
        val introduction: String
)