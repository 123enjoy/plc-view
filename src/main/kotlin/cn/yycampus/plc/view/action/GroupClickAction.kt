package cn.yycampus.plc.view.action

import ModernDocking.Docking
import ModernDocking.internal.DockingInternal
import cn.hutool.core.io.FileUtil
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.ui.dock.NavigationDock
import cn.yycampus.plc.view.util.configFile
import cn.yycampus.plc.view.util.rootJFrame
import javax.swing.JOptionPane
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath

class GroupClickAction : MenuTreeClickAction {

    private val logger = Log.get()
    override fun onClick(path: TreePath) {
        val node = path.lastPathComponent as DefaultMutableTreeNode
        val groupName = JOptionPane.showInputDialog("Input a group")?:return
        if (groupName.isBlank()) return
        val group = PLCGroupEntity(groupName)
        val entities = node.children().toList().filter {
            it as DefaultMutableTreeNode
            it.userObject is PLCGroupEntity
        }.map { (it as DefaultMutableTreeNode).userObject as PLCGroupEntity }
        if (entities.any { it.title.contentEquals(groupName) }){
            JOptionPane.showMessageDialog(rootJFrame, "Group $groupName name already exists",
                "Warning", JOptionPane.WARNING_MESSAGE)
            return
        }
        val obj = node.userObject
        if (obj is PLCEntity) {
            obj.children.add(group)
        }
        if (obj is PLCGroupEntity) {
            obj.children.add(group)
        }
        val newNode = DefaultMutableTreeNode(group)
        node.insert(newNode,entities.size)


        val parent = path.getPathComponent(0) as DefaultMutableTreeNode
        val entity = parent.userObject as PLCEntity
        val dock = DockingInternal.get(Docking.getSingleInstance())
            .getDockable(DockWindowEnum.Navigation.name) as NavigationDock
        dock.navTree.refresh(node)
        FileService.save(entity, FileUtil.file(configFile))
    }
}