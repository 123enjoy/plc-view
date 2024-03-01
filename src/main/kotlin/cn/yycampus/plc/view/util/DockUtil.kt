package cn.yycampus.plc.view.util

import ModernDocking.Docking
import ModernDocking.DockingRegion
import ModernDocking.exception.DockableRegistrationFailureException
import ModernDocking.internal.DockingInternal
import cn.hutool.core.io.FileUtil
import cn.hutool.core.util.ReflectUtil
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCGroupEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.enums.PopupMenuEnum
import cn.yycampus.plc.view.ui.dock.MainDock
import cn.yycampus.plc.view.ui.dock.NavigationDock
import cn.yycampus.plc.view.ui.panel.NavigationPanel
import cn.yycampus.plc.view.ui.panel.TabPanel
import cn.yycampus.plc.view.ui.panel.TabTitlePanel
import javax.swing.JOptionPane
import javax.swing.JTabbedPane
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath


val LOGGER = Log.get()
fun openNavigationDock(entity: PLCEntity) {
    val isDock = try {
        Docking.isDocked(DockWindowEnum.Navigation.name)
    } catch (e: DockableRegistrationFailureException) {
        LOGGER.info(e.message)
        false
    }
    if (!isDock) {
        val navigationDock = NavigationDock(NavigationPanel(entity))
        Docking.dock(
            navigationDock.persistentID,
            DockWindowEnum.Main.name,
            DockingRegion.WEST,
            0.2
        )
    } else {
        JOptionPane.showMessageDialog(
            rootJFrame, "Can't create new workspace!!,Exist a workspace",
            "Warning", JOptionPane.WARNING_MESSAGE
        )
    }
}


fun getMainTab(): JTabbedPane {
    val dock = DockingInternal.get(Docking.getSingleInstance())
        .getDockable(DockWindowEnum.Main.name) as MainDock
    return dock.tabPanel.tabbedPane
}

fun getMainTabMap(): MutableMap<String, TabPanel> {
    val dock = DockingInternal.get(Docking.getSingleInstance())
        .getDockable(DockWindowEnum.Main.name) as MainDock
    return dock.tabMap
}

fun openNewTab(component: TreePath) {
    val node = component.lastPathComponent as DefaultMutableTreeNode
    val menuEnum = PopupMenuEnum.convert(node.userObject.javaClass)
    val tab = getMainTab()
    val title = when (val obj = node.userObject) {
        is PLCEntity -> obj.name
        is PLCGroupEntity -> obj.title
        is PLCPointEntity -> obj.title
        else -> ""
    }

    if (!getMainTabMap().containsKey(component.toString())) {
        try {
            val rootObj =(component.getPathComponent(0) as DefaultMutableTreeNode).userObject
            val tabPanel = ReflectUtil.newInstance(menuEnum.tabClazz, rootObj,node.userObject)
            tab.addTab(title, tabPanel)
            val index = tab.indexOfComponent(tabPanel)
            tab.setTabComponentAt(index, TabTitlePanel(title, tab))
            getMainTabMap()[component.toString()] = tabPanel
        }catch (e:Exception){
            LOGGER.warn(e.message)
        }

    }
}

fun closeTab(path: TreePath) {
    val panel = getMainTabMap()[path.toString()]
    if (panel != null){
        panel.close()
        getMainTabMap().remove(panel.toString())
        getMainTab().remove(panel)

    }
}

fun saveEntityAndRefresh(path: TreePath, node: DefaultMutableTreeNode) {
    val entity = (path.getPathComponent(0) as DefaultMutableTreeNode).userObject as PLCEntity
    val dock = DockingInternal.get(Docking.getSingleInstance())
        .getDockable(DockWindowEnum.Navigation.name) as NavigationDock
    dock.navTree.refresh(node)
    FileService.save(entity, FileUtil.file(configFile))
}