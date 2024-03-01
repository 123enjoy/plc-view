package cn.yycampus.plc.view

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.resource.ResourceUtil
import cn.hutool.json.JSONObject
import cn.yycampus.plc.core.connect.plc.S7Connection
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.core.enums.DataTypeEnum
import javax.swing.ImageIcon
import kotlin.test.Test

class AppTest {

    @Test
    fun test01() {
        println("test01")
    }

    @Test
    fun test02(){
        val str = """
            {"name":"demo","ip":"127.0.0.1","port":102,"plcType":"S7","param":["0","1"],"children":[
            ],"points":[]}
        """.trimIndent()
        val obj = JSONObject(str).toBean(PLCEntity::class.java)
        val point = PLCPointEntity("dot1","DB1.0", DataTypeEnum.INT.typeName)
        val group = PLCGroupEntity("demo", points = mutableListOf(point,point))
        obj.points.add(point)
        obj.points.add(point)
        obj.children.add(group)
        println(JSONObject(obj))
    }

    @Test
    fun test03(){
       val path =  ResourceUtil.getResource("icon/devices.png")
       val icon = ImageIcon(path)
        println()
    }

    @Test
    fun test04(){
        val path = """C:\Users\admin\Documents\plc\test.json"""
        val entity = JSONObject(FileUtil.file(path).readText()).toBean(PLCEntity::class.java)
        val entities = FileService.parsePLCEntity(entity)
        val result = FileService.merge(entities)
    }

    @Test
    fun test05(){
        val result = DataTypeEnum.BOOLEAN.parser.byteToType(byteArrayOf(192.toByte()),"0.3")
        println(result)
    }

    @Test
    fun test06(){
        val result = S7Connection("127.0.0.1").readReal(1,0)
        println(result)
    }

}