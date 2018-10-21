package com.horyu1234.kkutuweb.controller

import com.horyu1234.kkutuweb.Locale
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("locale", Locale.webLocales)
        model.addAttribute("viewName", "login")

        return "layout"
    }
}
