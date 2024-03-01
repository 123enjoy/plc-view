package cn.yycampus.plc.view.enums

import cn.yycampus.plc.view.action.*

enum class TreeActionEventEnum(val title:String,val action:MenuTreeClickAction) {
    OPEN("open",OpenClickAction()),
    CLOSE("close",CloseClickAction()),
    ADD_GROUP("add group",GroupClickAction()),
    ADD_POINT("add point",PointClickAction()),
    UPDATE_POINT("update", UpdatePointClickAction()),
    VIEW("view",ViewClickAction())
    ;

    companion object {
        fun convert(action: String): TreeActionEventEnum {
            return entries.first { it.name == action }
        }
    }
}