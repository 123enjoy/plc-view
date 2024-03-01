package cn.yycampus.plc.view

import cn.hutool.core.thread.ThreadUtil
import cn.yycampus.plc.view.ui.AppView
import cn.yycampus.plc.view.util.DIR_CHOOSER
import cn.yycampus.plc.view.util.rootJFrame
import com.formdev.flatlaf.FlatDarculaLaf
import javax.swing.JFileChooser
import javax.swing.SwingUtilities

fun main() {
   FlatDarculaLaf.setup()

   ThreadUtil.execAsync {
      DIR_CHOOSER = JFileChooser().apply {
         fileSelectionMode = JFileChooser.SAVE_DIALOG or JFileChooser.DIRECTORIES_ONLY
      }
   }
   SwingUtilities.invokeLater {
      rootJFrame = AppView()
   }
}