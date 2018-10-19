package com.horyu1234.kkutuweb

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class MinifyFilter : Filter {
    private var htmlCompressor: HtmlCompressor? = null

    override fun init(filterConfig: FilterConfig) {
        htmlCompressor = HtmlCompressor()
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val responseWrapper = CharResponseWrapper(
                response as HttpServletResponse)

        chain.doFilter(request, responseWrapper)

        val servletResponse = responseWrapper.toString()
        if (!response.isCommitted()) {
            response.getWriter().write(htmlCompressor!!.compress(servletResponse))
        }
    }

    override fun destroy() {
        // nothing
    }
}
