package cn.yycampus.plc.view.enums

import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.view.ui.panel.GroupPanel
import cn.yycampus.plc.view.ui.panel.PLCDeviceTabPanel
import cn.yycampus.plc.view.ui.panel.PointPanel
import cn.yycampus.plc.view.ui.panel.TabPanel
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.tree.TreePath

enum class PopupMenuEnum(val clazz: Class<*>,val tabClazz:Class<out TabPanel> ,vararg event: TreeActionEventEnum) {
    PLC_DEVICE(
        PLCEntity::class.java,
        PLCDeviceTabPanel::class.java,
        TreeActionEventEnum.OPEN,
        TreeActionEventEnum.CLOSE,
        TreeActionEventEnum.ADD_GROUP,
        TreeActionEventEnum.ADD_POINT
    ),
    GROUP(
        PLCGroupEntity::class.java,
        GroupPanel::class.java,
        TreeActionEventEnum.ADD_GROUP,
        TreeActionEventEnum.VIEW,
        TreeActionEventEnum.UPDATE_POINT,
        TreeActionEventEnum.ADD_POINT
    ),
    POINT(PLCPointEntity::class.java,
        PointPanel::class.java
        ,TreeActionEventEnum.OPEN, TreeActionEventEnum.CLOSE,TreeActionEventEnum.VIEW,
        TreeActionEventEnum.UPDATE_POINT),
    ;

    private val menus = event.associateWith {
        object : JMenuItem(it.title) {
            var path: TreePath? = null
        }.apply {
            addActionListener { _ ->  it.action.onClick(path!!) }
        }
    }
    private val popup = JPopupMenu().apply { menus.forEach { this.add(it.value) } }

    fun getPopup(path: TreePath): JPopupMenu {
        menus.forEach { (_, menu) ->
            menu.path = path
        }
        return popup
    }

    companion object {
        fun convert(clazz: Class<out Any>): PopupMenuEnum {
            return PopupMenuEnum.entries.first { it.clazz == clazz }
        }
    }
}