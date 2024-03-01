package cn.yycampus.plc.core.connect.plc

import cn.hutool.core.util.ByteUtil
import cn.hutool.log.Log
import cn.yycampus.plc.panel.S7ParamPanel
import cn.yycampus.plc.core.connect.Connection
import cn.yycampus.plc.core.enity.PLCEntity
import com.github.s7connector.api.DaveArea
import com.github.s7connector.api.S7Connector
import com.github.s7connector.api.factory.S7ConnectorFactory
import java.nio.ByteOrder
import javax.swing.JPanel
import kotlin.experimental.and

class S7Connection(var ip:String = "", var port:Int = 102,
                   private var rack:Int = 0,private var slot:Int = 1) : Connection {

    private val logger = Log.get(S7Connection::class.java)

    private val panel = S7ParamPanel()

    private var s7Connection: S7Connector? = null

    init {
        panel.rack.text = "$rack"
        panel.slot.text = "$slot"
    }

    override fun initEntity(plcEntity: PLCEntity){
        ip= plcEntity.ip
        port = plcEntity.port
        panel.rack.text = plcEntity.param[0].toString()
        panel.slot.text = plcEntity.param[1].toString()
    }
    override fun setParams(ip: String, port: Int) {
        this.ip = ip
        this.port = port
    }

    override fun getAdditionalParamPanel(): JPanel {
        return panel
    }

    override fun getPanelHeight(): Int = 40

    override fun connect(): Boolean {
        try {
           s7Connection = S7ConnectorFactory.buildTCPConnector()
                .withHost(ip)
                .withPort(port)
                .withRack(panel.rack.text.toInt())
                .withSlot(panel.slot.text.toInt())
                .withTimeout(5000)
                .build()
            return true
        }catch (e:Exception){
            logger.warn("Connect Error: ${e.message}")
            return false
        }
    }

    override fun readBlock(db: Int, start: Int, size: Int): ByteArray {
        if (s7Connection == null){
            if (!connect()){
                logger.info("Connect S7 ip:$ip,port:$port fail")
                return byteArrayOf()
            }
        }
        val connection = s7Connection!!
        return connection.read(DaveArea.DB, db, start, size)
    }

    override fun check(): Boolean {
        val result = connect()
        s7Connection?.close()
        return result
    }
    override fun getParams(): List<Any> {
        return listOf(panel.rack.text,panel.slot.text)
    }

    override fun readShort(db: Int, start: Int): Short = readBlock(db,start,2).let {
        if (it.isEmpty()) 0 else ByteUtil.bytesToShort(it, ByteOrder.BIG_ENDIAN)
    }

    override fun readByte(db: Int, start: Int): Byte = readBlock(db,start,1).let {
        if (it.isEmpty()) 0 else it[0]
    }

    override fun readBool(db: Int, start: Int, position: Int): Boolean = readByte(db,start).let {
        it and ((1 shl (7 - position)).toByte()) >0
    }
    override fun readReal(db: Int, start: Int): Float  = readBlock(db,start,4).let {
        if (it.isEmpty()) 0f else ByteUtil.bytesToFloat(it, ByteOrder.BIG_ENDIAN)
    }
}