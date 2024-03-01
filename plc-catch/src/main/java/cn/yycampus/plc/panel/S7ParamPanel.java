/*
 * Created by JFormDesigner on Mon Feb 26 10:45:19 CST 2024
 */

package cn.yycampus.plc.panel;

import java.awt.*;
import net.miginfocom.swing.*;
import javax.swing.*;

/**
 * @author admin
 */
public class S7ParamPanel extends JPanel {
  public S7ParamPanel() {
    initComponents();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
    label1 = new JLabel();
    rack = new JTextField();
    label2 = new JLabel();
    slot = new JTextField();

    //======== this ========
    setLayout(new MigLayout(
      "fillx,insets 0,hidemode 3",
      // columns
      "[fill]" +
      "[fill]" +
      "[fill]" +
      "[fill]",
      // rows
      "[]"));

    //---- label1 ----
    label1.setText("rack:");
    add(label1, "cell 0 0");

    //---- rack ----
    rack.setMinimumSize(new Dimension(120, 30));
    add(rack, "cell 1 0");

    //---- label2 ----
    label2.setText("slot:");
    add(label2, "cell 2 0");

    //---- slot ----
    slot.setMinimumSize(new Dimension(120, 30));
    add(slot, "cell 3 0");
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  private JLabel label1;
  public JTextField rack;
  private JLabel label2;
  public JTextField slot;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
