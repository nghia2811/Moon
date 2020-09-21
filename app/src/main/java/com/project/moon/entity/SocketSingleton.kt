package com.project.moon.entity

import io.socket.client.IO
import io.socket.client.Socket

object SocketSingleton {
    private val socket = IO.socket("http://192.168.1.35:3484/")
    fun getSocket(): Socket {
        return socket
    }
}