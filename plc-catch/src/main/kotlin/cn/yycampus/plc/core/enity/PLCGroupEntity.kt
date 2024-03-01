package cn.yycampus.plc.core.enity

data class PLCGroupEntity(
    var title: String = "",
    var children: MutableList<PLCGroupEntity> = mutableListOf(),
    var points: MutableList<PLCPointEntity> = mutableListOf()
)