package com.project.moon.controller.`interface`

import com.project.moon.model.User

interface ILoginRegisterService {
    fun login(username:String,password:String):Boolean
    fun register(user: User):Boolean
}