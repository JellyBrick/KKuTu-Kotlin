package com.horyu1234.kkutuweb

import org.slf4j.LoggerFactory
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.text.MessageFormat
import java.util.*

object Locale {
    private val logger = LoggerFactory.getLogger(Locale::class.java)

    private const val MESSAGE_FILE = "locale.messages"
    private val messageBundle = ResourceBundle.getBundle(MESSAGE_FILE)

    fun getString(key: String): String {
        return getEncodedString(messageBundle.getString(key))
    }

    fun getString(key: String, vararg params: Any): String {
        return MessageFormat.format(getString(key), *params)
    }

    val webLocales: Map<String, String>
        get() {
            val webLocales = HashMap<String, String>()

            val keys = messageBundle.keys
            while (keys.hasMoreElements()) {
                var key = keys.nextElement()
                val value = getString(key)

                if (!key.startsWith("web.")) {
                    continue
                }

                key = key.replace("web.", "")

                webLocales[key] = value
            }

            return webLocales
        }

    private fun getEncodedString(original: String): String {
        try {
            return String(original.toByteArray(charset("8859_1")), StandardCharsets.UTF_8)
        } catch (e: UnsupportedEncodingException) {
            logger.error("지원되지 않는 인코딩입니다.", e)
        }

        return "ERROR"
    }
}
