package com.example.frontend_android.model.Chat

import java.time.LocalDateTime


data class MessageResponse (
     val id: Long,
     val senderId: Long,
     val recipientId: Long,
     val senderFirstname: String,
     val senderLastname: String,
     val content: String,
     val created: String,

)