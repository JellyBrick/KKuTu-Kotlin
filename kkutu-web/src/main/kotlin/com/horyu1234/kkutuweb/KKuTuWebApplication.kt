package com.horyu1234.kkutuweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan("com.horyu1234.kkutucommon")
@EnableJpaRepositories(basePackages = ["com.horyu1234.kkutucommon.repository"])
@SpringBootApplication
class KKuTuWebApplication

fun main(args: Array<String>) {
    runApplication<KKuTuWebApplication>(*args)
}
