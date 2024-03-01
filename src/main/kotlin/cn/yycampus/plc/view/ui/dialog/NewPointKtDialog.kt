package cn.yycampus.plc.view.ui.dialog

import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.enums.DataTypeEnum
import cn.yycampus.plc.view.util.rootJFrame
import cn.yycampus.plc.view.util.x
import javax.swing.DefaultComboBoxModel
import javax.swing.JOptionPane

class NewPointKtDialog(
    val entity: PLCPointEntity? = null,
    val isShow: Boolean = true,
    private val callback: (point: PLCPointEntity) -> Unit
) :
    NewPointDialog(rootJFrame) {

    init {
        isVisible = true
        preferredSize = 600 x 400

        dataType.model =
            DefaultComboBoxModel(DataTypeEnum.entries.map { it.typeName }.toTypedArray())
        if (entity != null) {
            submit.text = "update"
            name.text = entity.title
            dataType.selectedItem = entity.type
            db.text = entity.db
        }
        if (isShow){
            submit.addActionListener {
                val nameTxt = name.text
                val typeTxt = dataType.selectedItem as String
                val dbTxt = db.text
                if (nameTxt.isBlank() || typeTxt.isBlank() || dbTxt.isBlank()
                    || !dbTxt.contains("DB", true)
                    || !dbTxt.contains(".")
                ) {
                    JOptionPane.showMessageDialog(
                        this, "Please input name, type and db!!",
                        "Warning", JOptionPane.WARNING_MESSAGE
                    )
                    return@addActionListener
                }
                callback(PLCPointEntity(nameTxt, dbTxt, typeTxt))
                dispose()
            }
        }else{
            submit.isVisible = false
            name.isEnabled = false
            dataType.isEnabled = false
            db.isEnabled = false
        }

    }
}