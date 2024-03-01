package cn.yycampus.plc.view.ui.dock

import ModernDocking.DockableStyle
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.ui.dock.basic.AlwaysDisplayedPanel
import cn.yycampus.plc.view.ui.panel.TabPanel
import cn.yycampus.plc.view.ui.panel.TabPanelContainer
import java.awt.BorderLayout

class MainDock(val tabPanel: TabPanelContainer): AlwaysDisplayedPanel(DockWindowEnum.Main.name, DockWindowEnum.Main.name) {

    val tabMap: MutableMap<String, TabPanel> = mutableMapOf()
    init {
        content.add(tabPanel,BorderLayout.CENTER)
    }

    override fun getStyle(): DockableStyle {
        return DockableStyle.VERTICAL
    }

    override fun isPinningAllowed(): Boolean {
        return true
    }

}