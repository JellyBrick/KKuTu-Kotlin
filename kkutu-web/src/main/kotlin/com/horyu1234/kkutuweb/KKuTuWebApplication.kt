package com.horyu1234.kkutuweb

import com.horyu1234.kkutuweb.util.JarUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.system.ApplicationHome

private val logger = LoggerFactory.getLogger(KKuTuWebApplication::class.java)

@SpringBootApplication
class KKuTuWebApplication {
    companion object {
        const val SETTING_FILE_NAME = "application"
    }
}

fun main(args: Array<String>) {
    setStartupDirSystemProperty()
    copyResourcesIfNotExist()

    runApplication<KKuTuWebApplication>(*args)
}

private fun setStartupDirSystemProperty() {
    val applicationStartupDir = ApplicationHome(KKuTuWebApplication::class.java).getDir()
    var startupDirPath = applicationStartupDir.getPath()
    startupDirPath = startupDirPath.replace("\\", "/")

    System.setProperty("app.home", startupDirPath)
}

private fun copyResourcesIfNotExist() {
    if (JarUtils.copyResource("${KKuTuWebApplication.SETTING_FILE_NAME}.yml", "config")) {
        logger.info("설정 파일이 존재하지 않아, 새로 생성되었습니다.")
    }

    if (JarUtils.copyResource("static", "public")) {
        logger.info("웹 리소스 파일이 존재하지 않아, 새로 생성되었습니다.")
    }

    if (JarUtils.copyResource("templates", "public")) {
        logger.info("웹 템플릿 파일이 존재하지 않아, 새로 생성되었습니다.")
    }
}
