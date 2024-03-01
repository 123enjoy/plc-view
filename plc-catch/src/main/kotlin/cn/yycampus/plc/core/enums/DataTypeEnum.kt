package cn.yycampus.plc.core.enums

import cn.hutool.core.lang.Tuple
import cn.hutool.core.util.ByteUtil
import cn.yycampus.plc.core.connect.Connection
import cn.yycampus.plc.core.connect.plc.S7Connection
import java.nio.ByteOrder
import kotlin.experimental.and

enum class DataTypeEnum(val typeName: String, val size: Int, val parser: Parser<*>) {
    BYTE("Byte", 1, object : Parser<Byte> {
        override fun byteToType(bytes: ByteArray, start: String): Byte {
            return bytes[start.toInt()]
        }

        override fun readValue(connection: Connection, db: Int, start: Int, pos: Int): Byte = connection.readByte(db, start)
    }),
    INT("Int", 2, object : Parser<Int> {
        override fun byteToType(bytes: ByteArray, start: String): Int {
            return ByteUtil.bytesToInt(bytes, start.toInt(), ByteOrder.BIG_ENDIAN)
        }
        override fun readValue(connection: Connection, db: Int, start: Int, pos: Int): Int = connection.readShort(db, start).toInt()
    }),
    SHORT("Short", 2, object : Parser<Short> {
        override fun byteToType(bytes: ByteArray, start: String): Short {
            return ByteUtil.bytesToShort(bytes, start.toInt(), ByteOrder.BIG_ENDIAN)
        }

        override fun readValue(connection: Connection, db: Int, start: Int, pos: Int): Short = connection.readShort(db, start)
    }),
    FLOAT("Float", 4, object : Parser<Float> {
        override fun byteToType(bytes: ByteArray, start: String): Float {
            return ByteUtil.bytesToFloat(
                bytes.copyOfRange(start.toInt(), start.toInt() + 4),
                ByteOrder.BIG_ENDIAN
            )
        }

        override fun readValue(connection: Connection, db: Int, start: Int, pos: Int): Float = connection.readReal(db, start)
    }),
    BOOLEAN("Boolean", 1, object : Parser<Boolean> {
        override fun byteToType(bytes: ByteArray, start: String): Boolean {
            val split = start.split(".")
            val byte = bytes[split[0].toInt()]
            val position = split[1].toInt()
            return (byte and ((1 shl (7 - position)).toByte())) > 0
        }
        override fun readValue(connection: Connection, db: Int, start: Int, pos: Int): Boolean = connection.readBool(db, start, pos)
    }),
    REAL("Real", 4, FLOAT.parser),
    ;


    interface Parser<T> {
        fun parseDbAddr(addr: String): Tuple {
            val split = addr.replace("db", "", true)
                .split(".")
            val db = split[0].toInt()
            val start = split[1].toInt()
            return Tuple(db, start, if (split.size == 3) split[2].toInt() else 0)
        }

        fun byteToType(bytes: ByteArray, start: String): T
        fun readValue(connection: Connection, addr: String): T =
            parseDbAddr(addr).run { readValue(connection, this[0], this[1], this[2]) }


        fun readValue(connection: Connection, db: Int, start: Int, pos: Int = 0): T
    }

    companion object {
        /**
         * 根据类型名称转换为对应的枚举类型
         *
         * @param typeName 类型名称
         * @return 枚举类型
         */
        @JvmStatic
        fun convert(typeName: String): DataTypeEnum {
            return DataTypeEnum.entries.first { it.typeName == typeName }
        }


    }

}