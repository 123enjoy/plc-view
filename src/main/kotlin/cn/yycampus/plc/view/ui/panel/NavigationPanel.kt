package cn.yycampus.plc.view.ui.panel

import cn.hutool.core.util.ReflectUtil
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.view.enums.PopupMenuEnum
import cn.yycampus.plc.view.ui.dialog.NewPointKtDialog
import cn.yycampus.plc.view.util.*
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Panel
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPopupMenu
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel

class NavigationPanel(private val entity: PLCEntity) : Panel(BorderLayout()) {

    private val navTree = JTree()

    private val rootNode = DefaultMutableTreeNode(entity)

    private val treeModel = DefaultTreeModel(rootNode)

    init {
        entity.children.forEach {
            val node = DefaultMutableTreeNode(it)
            rootNode.add(node)
            loadTree(node, it)
        }
        entity.points.forEach { rootNode.add(DefaultMutableTreeNode(it)) }
        navTree.model = treeModel

        navTree.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                val component =
                    navTree.getPathForLocation(e.x, e.y) ?: navTree.selectionPath ?: return
                val node = component.lastPathComponent as DefaultMutableTreeNode
                val menuEnum = PopupMenuEnum.convert(node.userObject.javaClass)
                if (e.button == MouseEvent.BUTTON3) {
                    val popup = menuEnum.getPopup(component)
                    popup.show(e.component, e.x, e.y)
                }
                if (e.clickCount == 2) {
                    openNewTab(component)
                }
            }
        })

        navTree.cellRenderer = object : DefaultTreeCellRenderer() {
            override fun getTreeCellRendererComponent(
                tree: JTree, value: Any, selected: Boolean,
                expanded: Boolean, leaf: Boolean, row: Int, hasFocus: Boolean
            ): Component {
                super.getTreeCellRendererComponent(
                    tree, value, selected,
                    expanded, leaf, row, hasFocus
                )
                value as DefaultMutableTreeNode
                val obj = value.userObject
                if (obj is PLCEntity) {
                    icon = DEVICE_ICON
                    text = obj.name
                }
                if (obj is PLCGroupEntity) {
                    icon = CATEGORY_ICON
                    text = obj.title
                }
                if (obj is PLCPointEntity) {
                    icon = DOT_ICON
                    text = "${obj.title}-(${obj.db})"
                }
                return this
            }
        }
        add(navTree, BorderLayout.CENTER)
    }

    fun refresh() {
        treeModel.reload()
    }

    fun refresh(treeNode: DefaultMutableTreeNode) {
        treeModel.reload(treeNode)
    }

    private fun loadTree(parent: DefaultMutableTreeNode, group: PLCGroupEntity) {
        group.children.forEach {
            val node = DefaultMutableTreeNode(it)
            parent.add(node)
            loadTree(node, it)
        }
        group.points.forEach {
            val node = DefaultMutableTreeNode(it)
            parent.add(node)
        }
    }
}