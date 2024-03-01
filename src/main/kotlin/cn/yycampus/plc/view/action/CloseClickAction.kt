package cn.yycampus.plc.view.action

import cn.hutool.log.Log
import cn.yycampus.plc.view.util.closeTab
import javax.swing.tree.TreePath

class CloseClickAction :MenuTreeClickAction{
    override fun onClick(path: TreePath) {
        closeTab(path)
    }
}