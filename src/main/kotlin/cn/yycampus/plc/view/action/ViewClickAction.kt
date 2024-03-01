package cn.yycampus.plc.view.action

import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.view.ui.dialog.NewPointKtDialog
import cn.yycampus.plc.view.util.rootJFrame
import javax.swing.JOptionPane
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath

class ViewClickAction : MenuTreeClickAction {
    override fun onClick(path: TreePath) {
        val node = path.lastPathComponent as DefaultMutableTreeNode
        val obj = node.userObject
        if (obj is PLCGroupEntity) {
            JOptionPane.showMessageDialog(rootJFrame, obj.title)
        }

        if (obj is PLCPointEntity) {
            NewPointKtDialog(obj, false) {}
        }
    }
}