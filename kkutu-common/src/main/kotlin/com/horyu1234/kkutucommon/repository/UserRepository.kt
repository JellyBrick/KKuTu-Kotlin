package com.horyu1234.kkutucommon.repository

import com.horyu1234.kkutucommon.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID>