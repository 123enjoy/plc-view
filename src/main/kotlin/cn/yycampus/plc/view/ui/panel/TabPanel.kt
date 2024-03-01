package cn.yycampus.plc.view.ui.panel

import java.awt.BorderLayout
import java.awt.Panel

abstract class TabPanel: Panel(BorderLayout()) {

    open fun close(){}

    open fun stopAutoRefresh(){}


}