package com.example.frontend_android.model.Chat


/**
 * DTO för meddelanden
 */
data class MessageRequest (
     val recipientId: Long?,
     val encryptedValue: String
)