package cn.yycampus.plc.view.ui.panel

import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.service.FileService
import javax.swing.JLabel

class PLCDeviceTabPanel(val config: PLCEntity,entity: PLCEntity):TabPanel() {
    init {
       FileService.parsePLCEntity(entity)
    }



}