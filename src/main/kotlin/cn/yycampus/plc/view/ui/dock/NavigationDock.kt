package cn.yycampus.plc.view.ui.dock

import ModernDocking.DockableStyle
import ModernDocking.Docking
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.ui.dock.basic.AlwaysDisplayedPanel
import cn.yycampus.plc.view.ui.panel.NavigationPanel
import java.awt.BorderLayout

class NavigationDock(val navTree:NavigationPanel)
    :AlwaysDisplayedPanel(DockWindowEnum.Navigation.name, DockWindowEnum.Navigation.name) {

    init {
        content.add(navTree,BorderLayout.CENTER)
    }

    override fun getStyle(): DockableStyle {
        return DockableStyle.VERTICAL
    }

    override fun isPinningAllowed(): Boolean {
        return true
    }
}