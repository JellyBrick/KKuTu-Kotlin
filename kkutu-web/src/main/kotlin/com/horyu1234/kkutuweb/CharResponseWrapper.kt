package com.horyu1234.kkutuweb

import java.io.CharArrayWriter
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

class CharResponseWrapper internal constructor(response: HttpServletResponse) : HttpServletResponseWrapper(response) {
    private val output = CharArrayWriter()

    override fun toString(): String {
        return output.toString()
    }

    override fun getWriter(): PrintWriter {
        return PrintWriter(output)
    }
}