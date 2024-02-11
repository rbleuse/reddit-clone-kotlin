package com.rbleuse.redditclonekotlin.service

import com.rbleuse.redditclonekotlin.exception.ActivationException
import com.rbleuse.redditclonekotlin.model.NotificationEmail
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MailService(private val javaMailSender: JavaMailSender, private val mailBuilder: MailBuilder) {
    @Async
    fun sendEmail(notificationEmail: NotificationEmail) {
        val messagePreparator =
            MimeMessagePreparator { mimeMessage ->
                val messageHelper = MimeMessageHelper(mimeMessage)
                messageHelper.setFrom("activation@redditclone.com")
                messageHelper.setTo(notificationEmail.recipient)
                messageHelper.setSubject(notificationEmail.subject)
                messageHelper.setText(mailBuilder.build(notificationEmail.body))
            }

        try {
            javaMailSender.send(messagePreparator)
            println("Activation Email Sent")
        } catch (e: MailException) {
            throw ActivationException("Error sending activation email to ${notificationEmail.recipient}")
        }
    }
}
