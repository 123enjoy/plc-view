package org.example.cn.yycampus.plc.core.enity

import cn.yycampus.plc.core.enity.PLCPointEntity
import java.util.*

data class PointRange(val start: Int = 0,var end: Int =0 ,val points: LinkedList<PLCPointEntity> = LinkedList<PLCPointEntity>())