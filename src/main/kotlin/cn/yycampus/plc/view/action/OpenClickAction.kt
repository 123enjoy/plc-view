package cn.yycampus.plc.view.action

import cn.hutool.log.Log
import cn.yycampus.plc.view.util.openNewTab
import javax.swing.tree.TreePath

class OpenClickAction:MenuTreeClickAction {
    override fun onClick(path: TreePath) {
        openNewTab(path)
    }
}