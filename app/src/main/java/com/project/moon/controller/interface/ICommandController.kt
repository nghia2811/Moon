package com.project.moon.controller.`interface`

import com.project.moon.model.Commands
import java.util.ArrayList

interface ICommandController {
    fun getCommandList(): ArrayList<Commands>
    fun sendCommand(commands: Commands){}
}