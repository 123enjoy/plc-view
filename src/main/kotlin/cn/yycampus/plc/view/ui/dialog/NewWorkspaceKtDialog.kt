package cn.yycampus.plc.view.ui.dialog

import ModernDocking.Dockable
import ModernDocking.Docking
import ModernDocking.DockingRegion
import ModernDocking.exception.DockableRegistrationFailureException
import cn.hutool.core.util.ReflectUtil
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.service.FileService
import cn.yycampus.plc.view.enums.DockWindowEnum
import cn.yycampus.plc.view.enums.PLCEnum
import cn.yycampus.plc.view.ui.dock.NavigationDock
import cn.yycampus.plc.view.ui.panel.NavigationPanel
import cn.yycampus.plc.view.util.*
import java.awt.event.ItemEvent
import java.io.File
import javax.swing.DefaultComboBoxModel
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class NewWorkspaceKtDialog : NewWorkspaceDialog(rootJFrame) {

    private val logger = Log.get(NewWorkspaceKtDialog::class.java)

    init {
        isVisible = true
        preferredSize = 400 x 400
        comfirm.isEnabled = false

        plcType.model = DefaultComboBoxModel(PLCEnum.entries.toTypedArray())

        val first = PLCEnum.entries.first()
        var connect = ReflectUtil.newInstance(first.connect)
        var additionalPanel = connect.getAdditionalParamPanel()
        param.add(additionalPanel)
        preferredSize =
            preferredSize.width x (preferredSize.height + connect.getPanelHeight())
        ip.text = "127.0.0.1"
        port.text = "102"
        plcType.addActionListener {
            SwingUtilities.invokeLater {
                val select = (plcType.selectedItem as PLCEnum).connect
                connect = ReflectUtil.newInstance(select)
                param.size = 400 x connect.getPanelHeight()
                param.removeAll()
                additionalPanel = connect.getAdditionalParamPanel()
                param.add(additionalPanel)
                param.revalidate()
            }
        }
        test.addActionListener {
            val select = (plcType.selectedItem as PLCEnum).connect
            connect.setParams(ip.text,port.text.toInt())
            val isOk = connect.check()
            if (isOk) {
                logger.info("Connect Success!!")
                comfirm.isEnabled = true
            } else {
                logger.info("Connect Fail!!")
                comfirm.isEnabled = false
                JOptionPane.showMessageDialog(
                    this, "Connect Fail!!",
                    "Warning", JOptionPane.WARNING_MESSAGE
                )
            }
        }
        comfirm.addActionListener {
            val nameTxt = name.text
            if (nameTxt.isBlank()) {
                JOptionPane.showMessageDialog(
                    this, "Please input name!!",
                    "Warning", JOptionPane.WARNING_MESSAGE
                )
                return@addActionListener
            }

            if (JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure to create new PLC?",
                    "Confirm", JOptionPane.YES_NO_OPTION
                ) == JOptionPane.YES_OPTION
            ) {

                val item = plcType.selectedItem as PLCEnum

                val entity = PLCEntity(
                    nameTxt, ip.text, port.text.toInt(),
                    item.name, connect.getParams()
                )
                DIR_CHOOSER.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                val option = DIR_CHOOSER.showOpenDialog(this)
                DIR_CHOOSER.currentDirectory = File(System.getProperty("user.dir"))
                if (option == JFileChooser.APPROVE_OPTION) {
                    val dir = DIR_CHOOSER.selectedFile
                    logger.info("Create new workspace: $nameTxt,Save to $dir")
                    openNavigationDock(entity)

                    val file = File(dir, "$nameTxt.json")
                    configFile = file.path
                    FileService.save(entity, file)
                    comfirm.isEnabled = true

                }

                dispose()
            }
        }

    }
}
