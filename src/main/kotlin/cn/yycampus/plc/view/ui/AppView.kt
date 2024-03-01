package cn.yycampus.plc.view.ui

import ModernDocking.*
import ModernDocking.layouts.DockingLayouts
import ModernDocking.settings.Settings
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.view.enums.MenuEnum
import cn.yycampus.plc.view.ui.dialog.NewWorkspaceKtDialog
import cn.yycampus.plc.view.ui.dock.MainDock
import cn.yycampus.plc.view.ui.panel.TabPanel
import cn.yycampus.plc.view.ui.panel.TabPanelContainer
import cn.yycampus.plc.view.util.DIR_CHOOSER
import cn.yycampus.plc.view.util.configFile
import cn.yycampus.plc.view.util.openNavigationDock
import cn.yycampus.plc.view.util.x
import docking.ui.DockingUI
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JMenuBar
import kotlin.system.exitProcess

class AppView : JFrame("plc-view") {
    init {
        Settings.setAlwaysDisplayTabMode(true)
        Docking.initialize(this)
        DockingUI.initialize();
    }

    private val docker: RootDockingPanel = RootDockingPanel(this)

    private val mainUIDock = MainDock(TabPanelContainer())

//    private val navigationUIDock = NavigationDock()

    private val defaultLayout = WindowLayoutBuilder(mainUIDock.persistentID)
//        .dockToRoot(navigationUIDock.persistentID, DockingRegion.WEST)
        .buildApplicationLayout()


    init {

        layout = GridBagLayout()
        val gbc = GridBagConstraints()

        gbc.gridy++
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH

        gbc.insets = Insets(0, 5, 5, 5)

        contentPane.add(docker,gbc)

        DockingLayouts.addLayout("default", defaultLayout)

        AppState.setDefaultApplicationLayout(defaultLayout)

        AppState.restore()


        jMenuBar = JMenuBar()
        MenuEnum.buildMenu(null, null) { clickMenu(it) }
            .forEach { if (it != null) jMenuBar.add(it) }

        isVisible = true
        size = 1200 x 900
        setLocationRelativeTo(null)
        setDefaultCloseOperation(EXIT_ON_CLOSE)
        addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                println("Close Window")
                exitProcess(0)
            }
        })
    }

    private fun clickMenu(menu: MenuEnum) {
        when(menu){
            MenuEnum.FILE -> {
                println("click file menu")
            }
            MenuEnum.NEW -> {
                println("click new menu")
            }
            MenuEnum.OPEN -> {
                DIR_CHOOSER.fileSelectionMode = JFileChooser.FILES_ONLY
                val option = DIR_CHOOSER.showOpenDialog(this)
                if (option == JFileChooser.APPROVE_OPTION) {
                    val entity = FileService.load(DIR_CHOOSER.selectedFile)
                    configFile = DIR_CHOOSER.selectedFile.toString()
                    openNavigationDock(entity)
                }
            }
            MenuEnum.SAVE -> TODO()
            MenuEnum.SAVE_AS -> TODO()
            MenuEnum.WorkSpace -> {
                NewWorkspaceKtDialog()
            }
        }
    }
}