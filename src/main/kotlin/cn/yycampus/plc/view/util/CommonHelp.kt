package cn.yycampus.plc.view.util

import cn.hutool.core.io.resource.ResourceUtil
import java.awt.Dimension
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JFileChooser
import javax.swing.JFrame


infix fun Int.x(y:Int) = Dimension(this,y)

lateinit var rootJFrame: JFrame

lateinit var DIR_CHOOSER:JFileChooser

lateinit var configFile:String

val DEVICE_ICON = ImageIcon(ResourceUtil.getResource("icon/devices.png"))
val DOT_ICON = ImageIcon(ResourceUtil.getResource("icon/dot.png"))
val CATEGORY_ICON = ImageIcon(ResourceUtil.getResource("icon/category.png"))


