package com.example.frontend_android

 object ErrorMessages {


     fun get_error(from:String, data:String): String = "Från: $from : Kunde inte hitta $data"
     fun post_error(from:String, data:String): String = "Från: $from : Kunde inte skapa $data"
     fun patch_error(from:String, data:String): String = "Från: $from :  Kunde inte uppdatera $data"
     fun registration_error(from:String, data:String): String = "Från: $from : Registrering misslyckades!"
     fun login_error(from:String, data:String): String = "Från: $from :  Login misslyckades!"
     fun delete_error(from:String, data:String): String = "Från: $from :  Radering misslyckades!"

}