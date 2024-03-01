package cn.yycampus.plc.core.connect.plc

import cn.yycampus.plc.core.connect.Connection
import cn.yycampus.plc.core.enity.PLCEntity


class McConnection :Connection{
    override fun initEntity(plcEntity: PLCEntity) {
    }

    override fun setParams(ip: String, port: Int) {

    }
}