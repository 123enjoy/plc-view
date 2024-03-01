package cn.yycampus.plc.core.service

import cn.hutool.core.collection.ListUtil
import cn.hutool.core.io.FileUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONObject
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import org.example.cn.yycampus.plc.core.enity.PointRange
import cn.yycampus.plc.core.enums.DataTypeEnum
import java.io.File
import java.util.LinkedList
import kotlin.math.log

object FileService {

    private val logger = Log.get()
    fun save(entity: PLCEntity, file: File) {
        file.writeText(JSONObject(entity).toString())
    }

    fun load(file: File): PLCEntity = JSONObject(file.readText()).toBean(PLCEntity::class.java)



    fun merge(entities: List<PLCPointEntity>): Map<Int, List<PointRange>> {
        val points = entities.filter { it.db.split(".").size >= 2 }.sortedBy { it.db }
        val dbPoints = points.groupBy {
            val block = it.db.split(".")[0]
            StrUtil.replace(block, "DB", "", true).toInt()
        }
        val dbRange = dbPoints.map { (k, v) ->
           val value = v.map {
                it.db.split(".")[1].toInt() to DataTypeEnum.convert(it.type) to it
            }.sortedBy { it.first.first }
            val range = mutableMapOf<Int,PointRange>()
            value.forEach {
                if(range.isEmpty()){
                    range[it.first.first] = PointRange(it.first.first,
                        it.first.first + it.first.second.size,
                        ListUtil.toLinkedList(it.second)
                    )
                }else{
                    val entries = range.entries.last()
                    if (it.first.first < entries.value.end){
                        if (it.first.second == DataTypeEnum.BOOLEAN
                            && it.first.first + DataTypeEnum.BOOLEAN.size == entries.value.end){
                            entries.value.points.add(it.second)
                        }else {
                            logger.warn("dot points config,end:${entries.value.end},DB$k.${it.first}")
                        }
                    }
                    if(it.first.first == entries.value.end){
                        entries.value.end += it.first.second.size
                        entries.value.points.add(it.second)
                    }
                    if (it.first.first > entries.value.end){
                        range[it.first.first] = PointRange(it.first.first,
                            it.first.first + it.first.second.size,
                            ListUtil.toLinkedList(it.second)
                        )


                    }
                }
            }
            k to range.map { it.value }
        }.toMap()
        return dbRange


    }

    fun parsePLCEntity(entity: PLCEntity): List<PLCPointEntity> {
        val groups = entity.points
        entity.children.forEach { child ->
            groups.addAll(parseGroupPoints(child))
        }
        return groups
    }


    fun parseGroupPoints(entity: PLCGroupEntity): List<PLCPointEntity> {
        val points = mutableListOf<PLCPointEntity>()
        points.addAll(entity.points)
        entity.children.forEach { child ->
            points.addAll(parseGroupPoints(child))
        }
        return points
    }

}