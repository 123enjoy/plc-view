package cn.yycampus.plc.view.action

import javax.swing.tree.TreePath

interface MenuTreeClickAction {
    fun onClick(path: TreePath)
}