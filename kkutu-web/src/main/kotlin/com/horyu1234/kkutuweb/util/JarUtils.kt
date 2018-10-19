package com.horyu1234.kkutuweb.util

import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

object JarUtils {
    private val logger = LoggerFactory.getLogger(JarUtils::class.java)

    fun copyResource(resourceName: String, targetFolder: String): Boolean {
        if (getTargetFile(targetFolder, resourceName).exists()) {
            return false
        }

        val resourceStreamMap = JarUtils.getResourceStreamMap(resourceName)
        for ((fileName, inputStream) in resourceStreamMap) {

            val targetFile = getTargetFile(targetFolder, fileName)
            if (targetFile.exists()) {
                continue
            }

            copyFile(inputStream, targetFile)
        }

        return true
    }

    private fun copyFile(inputStream: InputStream, targetFile: File) {
        if (targetFile.parentFile != null && !targetFile.parentFile.exists() && !targetFile.parentFile.mkdirs()) {
            logger.warn("대상 위치의 상위 폴더를 생성하는 중 문제가 발생하였습니다.")
        }

        try {
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            logger.error("대상 위치에 스트림을 기록하는 중 오류가 발생하였습니다.", e)
        }

    }

    private fun getTargetFile(targetFolder: String, resourceName: String): File {
        return Paths.get(System.getProperty("app.home"), targetFolder, resourceName).toFile()
    }

    private fun isRunFromJarFile(resourceName: String): Boolean {
        val jarFileUri: URI
        try {
            jarFileUri = Objects.requireNonNull(JarUtils::class.java.classLoader.getResource(resourceName)).toURI()
        } catch (e: URISyntaxException) {
            logger.error("JAR 파일의 주소를 가져오는 중 오류가 발생하였습니다.", e)
            return false
        }

        return jarFileUri.scheme.contains("jar")
    }

    private val jarFile: File
        get() {
            val filePathPrefix = "file:/"
            val filePathSuffix = "!/BOOT-INF"

            var file = JarUtils::class.java.protectionDomain.codeSource.location.file
            file = file.substring(file.indexOf(filePathPrefix) + filePathPrefix.length, file.indexOf(filePathSuffix))

            return File(file)
        }

    private fun getResourceStreamMap(resourceName: String): Map<String, InputStream> {
        val resourceInputStreams = HashMap<String, InputStream>()
        if (JarUtils.isRunFromJarFile(resourceName)) {
            resourceInputStreams.putAll(getResourceStreamMapWhenJarFile(resourceName))
        } else {
            resourceInputStreams.putAll(getResourceStreamMapWhenIDE(resourceName))
        }

        return resourceInputStreams
    }

    private fun getResourceStreamMapWhenJarFile(resourceName: String): Map<String, InputStream> {
        val resourceStreamMap = HashMap<String, InputStream>()

        for (jarEntry in jarEntries) {
            var entryFileName = jarEntry.name
            if (!entryFileName.startsWith("BOOT-INF/classes/$resourceName")) {
                continue
            }

            entryFileName = removeParentPath(entryFileName, resourceName)
            if (isFile(entryFileName)) {
                resourceStreamMap[entryFileName] = getResourceFileStream(entryFileName)
            }
        }

        return resourceStreamMap
    }

    private val jarEntries: List<JarEntry>
        get() {
            val jarEntries = ArrayList<JarEntry>()
            try {
                JarFile(JarUtils.jarFile).use { jar -> jarEntries.addAll(Collections.list(jar.entries())) }
            } catch (e: IOException) {
                logger.error("JAR 파일에서 스트림을 가져오는 중 오류가 발생하였습니다.", e)
            }

            return jarEntries
        }

    private fun getResourceStreamMapWhenIDE(resourceName: String): Map<String, InputStream> {
        val resourceStreamMap = HashMap<String, InputStream>()

        val resourceUrl = Thread.currentThread().contextClassLoader.getResource(resourceName)
        if (resourceUrl != null) {
            val resource = File(resourceUrl.file)

            val resourceFiles = ArrayList<File>()
            if (resource.isFile) {
                resourceFiles.add(resource)
            } else if (resource.isDirectory) {
                resourceFiles.addAll(FileUtils.listFiles(resource, null, true))
            }

            resourceStreamMap.putAll(toResourceStreamMap(resourceName, resourceFiles))
        }

        return resourceStreamMap
    }

    private fun toResourceStreamMap(resourceName: String, resourceFiles: List<File>): Map<String, InputStream> {
        val resourceStreamMap = HashMap<String, InputStream>()
        for (resourceFile in resourceFiles) {
            val fileLocation = resourceFile.path
            val fileName = removeParentPath(fileLocation, resourceName)

            try {
                resourceStreamMap[fileName] = FileUtils.openInputStream(resourceFile)
            } catch (e: IOException) {
                logger.error("파일에서 입력 스트림을 여는 중 오류가 발생하였습니다.", e)
            }

        }

        return resourceStreamMap
    }

    private fun isFile(entryFileName: String): Boolean {
        return "" != FilenameUtils.getExtension(entryFileName)
    }

    private fun removeParentPath(path: String, folderName: String): String {
        return path.substring(path.indexOf(folderName), path.length)
    }

    private fun getResourceFileStream(fileName: String): InputStream {
        return Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
    }
}
