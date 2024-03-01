package cn.yycampus.plc.view.action

import cn.hutool.core.bean.BeanUtil
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.view.ui.dialog.NewPointKtDialog
import cn.yycampus.plc.view.util.rootJFrame
import cn.yycampus.plc.view.util.saveEntityAndRefresh
import javax.swing.JOptionPane
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath

class UpdatePointClickAction : MenuTreeClickAction {
    override fun onClick(path: TreePath) {
        val node = path.lastPathComponent as DefaultMutableTreeNode
        val obj = node.userObject
        if (obj is PLCGroupEntity) {
            val groupName = JOptionPane.showInputDialog(rootJFrame, "Please input new name!!")
            obj.title = groupName
        }

        if (obj is PLCPointEntity) {
            NewPointKtDialog(obj, true) {
                BeanUtil.copyProperties(it, obj)
            }
        }
        saveEntityAndRefresh(path, node)
    }
}