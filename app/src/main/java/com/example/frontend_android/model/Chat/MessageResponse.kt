package com.example.frontend_android.model.Chat

import java.time.LocalDateTime


data class MessageResponse (
     val id: Long? = null,
     val senderId: Long? = null,
     val recipientId: Long? = null,
     val senderUsername: String? = "",
     val recipientUsername: String? = "",
     val content: String? = null,
     val created: String? = null
)
