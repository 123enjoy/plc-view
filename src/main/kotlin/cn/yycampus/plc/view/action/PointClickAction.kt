package cn.yycampus.plc.view.action

import ModernDocking.Docking
import ModernDocking.internal.DockingInternal
import cn.hutool.core.io.FileUtil
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.ui.dialog.NewPointKtDialog
import cn.yycampus.plc.view.ui.dock.NavigationDock
import cn.yycampus.plc.view.util.configFile
import cn.yycampus.plc.view.util.rootJFrame
import cn.yycampus.plc.view.util.saveEntityAndRefresh
import javax.swing.JOptionPane
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath

class PointClickAction : MenuTreeClickAction {

    private val logger = Log.get()
    override fun onClick(path: TreePath) {
        NewPointKtDialog { point ->
            val node = path.lastPathComponent as DefaultMutableTreeNode
            val entities = node.children().toList().filter {
                it as DefaultMutableTreeNode
                it.userObject is PLCPointEntity
            }.map { (it as DefaultMutableTreeNode).userObject as PLCPointEntity }

            if (entities.any { it.title.contentEquals(point.title) }) {
                JOptionPane.showMessageDialog(
                    rootJFrame, "Point ${point.title} name already exists",
                    "Warning", JOptionPane.WARNING_MESSAGE)
                return@NewPointKtDialog
            }

            val newNode = DefaultMutableTreeNode(point)
            node.add(newNode)

            val obj = node.userObject
            if (obj is PLCEntity) {
                obj.points.add(point)
            }
            if (obj is PLCGroupEntity) {
                obj.points.add(point)
            }
            saveEntityAndRefresh(path, node)
        }
    }
}