package com.rbleuse.redditclonekotlin.service

import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Component
class MailBuilder(
    private val templateEngine: TemplateEngine,
) {
    fun build(message: String): String {
        val context = Context()
        context.setVariable("body", message)
        return templateEngine.process("mailTemplate", context)
    }
}
