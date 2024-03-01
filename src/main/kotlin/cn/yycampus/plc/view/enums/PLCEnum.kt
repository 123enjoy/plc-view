package cn.yycampus.plc.view.enums

import cn.yycampus.plc.core.connect.Connection
import cn.yycampus.plc.core.connect.plc.S7Connection
import cn.yycampus.plc.core.connect.plc.McConnection

enum class PLCEnum(val connect: Class<out Connection>) {
    S7(S7Connection::class.java),
    Mc(McConnection::class.java)
    ;

    companion object {
        fun convert(name: String): PLCEnum {
            return entries.firstOrNull { it.name.contentEquals(name, true) } ?: S7
        }
    }
}