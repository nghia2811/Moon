package com.project.moon.controller.service

import com.project.moon.controller.`interface`.IDeviceController
import com.project.moon.model.SocketSingleton

class DeviceController : IDeviceController  {

    private var socket = SocketSingleton.getSocket()

    override fun emitCommand(command: Int){
        socket.connect()
        when (command) {
            1 -> socket.emit("go-ahead", "go-ahead")
            2 -> socket.emit("go-back", "go-back")
            3 -> socket.emit("left", "left")
            4 -> socket.emit("right", "right")
            else -> socket.emit("stop", "stop")
        }
    }

    override fun getStreamUrl(): String {
        return "http://192.168.43.125/"
    }
}