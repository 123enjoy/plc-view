package cn.yycampus.plc.core.enity

data class PLCEntity(
    var name: String = "",
    var ip: String = "",
    var port: Int = 0,
    var plcType: String = "S7",
    var param: List<Any> = emptyList(),
    var children: MutableList<PLCGroupEntity> = mutableListOf(),
    var points: MutableList<PLCPointEntity> = mutableListOf()
)