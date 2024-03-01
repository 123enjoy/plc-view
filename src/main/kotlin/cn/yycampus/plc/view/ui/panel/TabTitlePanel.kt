package cn.yycampus.plc.view.ui.panel

import cn.yycampus.plc.view.util.getMainTabMap
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*
import javax.swing.plaf.basic.BasicButtonUI


class TabTitlePanel(private val tabTitle:String,private val panel:JTabbedPane)
    :JPanel(FlowLayout(FlowLayout.LEFT,0,0)) {

    private val buttonMouseListener: MouseListener = object : MouseAdapter() {
        override fun mouseEntered(e: MouseEvent) {
            val component: Component = e.component
            if (component is AbstractButton) {
                component.isBorderPainted = true
            }
        }

        override fun mouseExited(e: MouseEvent) {
            val component: Component = e.component
            if (component is AbstractButton) {
                component.isBorderPainted = false
            }
        }
    }

    private class TabButton(val tabPanel:TabTitlePanel) : JButton(), ActionListener {
        init {
            val size = 17
            preferredSize = Dimension(size, size)
            toolTipText = "close this tab"
            //Make the button looks the same for all Laf's
            setUI(BasicButtonUI())
            //Make it transparent
            isContentAreaFilled = false
            //No need to be focusable
            isFocusable = false
            border = BorderFactory.createEtchedBorder()
            isBorderPainted = false
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(tabPanel.buttonMouseListener)
            isRolloverEnabled = true
            //Close the proper tab by clicking the button
            addActionListener(this)
        }

        override fun actionPerformed(e: ActionEvent) {
            val i: Int = tabPanel.panel.indexOfTabComponent(tabPanel)
            if (i != -1) {
                val panel = tabPanel.panel.getComponentAt(i)
                val entry = getMainTabMap().entries.firstOrNull { it.value == panel }
                if (entry != null){
                    entry.value.close()
                    getMainTabMap().remove(entry.key)
                }
                tabPanel.panel.remove(i)
            }
        }

        //we don't want to update UI for this button
        override fun updateUI() {
        }

        //paint the cross
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            val g2 = g.create() as Graphics2D
            //shift the image for pressed buttons
            if (getModel().isPressed) {
                g2.translate(1, 1)
            }
            g2.stroke = BasicStroke(2f)
            g2.color = Color.BLACK
            if (getModel().isRollover) {
                g2.color = Color.MAGENTA
            }
            val delta = 6
            g2.drawLine(delta, delta, width - delta - 1, height - delta - 1)
            g2.drawLine(width - delta - 1, delta, delta, height - delta - 1)
            g2.dispose()
        }
    }
    init {
        isOpaque = false
        val label = JLabel(tabTitle)
        label.border = BorderFactory.createEmptyBorder(0,0,0,5)
        this.border = BorderFactory.createEmptyBorder(2, 0, 0, 0)
        this.add(label)
        this.add(TabButton(this))

    }








}