package com.horyu1234.kkutuweb.controller

import com.horyu1234.kkutuweb.Locale
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LobbyController {
    @GetMapping("/")
    fun lobby(model: Model): String {
        model.addAttribute("locale", Locale.webLocales)
        model.addAttribute("viewName", "lobby")

        return "layout"
    }
}
