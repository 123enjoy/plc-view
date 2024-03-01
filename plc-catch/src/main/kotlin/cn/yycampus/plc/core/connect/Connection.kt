package cn.yycampus.plc.core.connect

import cn.yycampus.plc.core.enity.PLCEntity
import javax.swing.JPanel

interface Connection {

    /**
     * 获取额外参数面板
     *
     * @return 额外参数面板
     */

    fun initEntity(plcEntity: PLCEntity)
    fun setParams(ip: String, port: Int)
    fun getAdditionalParamPanel(): JPanel = JPanel()

    /**
     * 获取面板的高度
     *
     * @return 面板的高度
     */
    fun getPanelHeight(): Int = 0

    fun getParams(): List<Any> = emptyList()

    fun check(): Boolean = false

    fun connect(): Boolean = false
    fun readBlock(db: Int, start: Int, size: Int): ByteArray = byteArrayOf()
    fun readShort(db: Int, start: Int): Short = 0
    fun readReal(db: Int, start: Int): Float = 0.0f
    fun readBool(db: Int, start: Int,position:Int): Boolean = false
    fun readByte(db: Int, start: Int): Byte = 0
}