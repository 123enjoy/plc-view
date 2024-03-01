/*
 * Created by JFormDesigner on Tue Feb 27 12:50:23 CST 2024
 */

package cn.yycampus.plc.view.ui.dialog;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author admin
 */
public class NewPointDialog extends JDialog {
  public NewPointDialog(Window owner) {
    super(owner);
    initComponents();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
    label1 = new JLabel();
    name = new JTextField();
    label2 = new JLabel();
    db = new JTextField();
    label3 = new JLabel();
    dataType = new JComboBox();
    submit = new JButton();

    //======== this ========
    setPreferredSize(new Dimension(320, 180));
    var contentPane = getContentPane();
    contentPane.setLayout(new MigLayout(
      "fillx,hidemode 3",
      // columns
      "[fill]" +
      "[grow]",
      // rows
      "[]" +
      "[]" +
      "[]" +
      "[]"));

    //---- label1 ----
    label1.setText("name");
    contentPane.add(label1, "cell 0 0");
    contentPane.add(name, "cell 1 0,growx");

    //---- label2 ----
    label2.setText("db:");
    contentPane.add(label2, "cell 0 1");
    contentPane.add(db, "cell 1 1,growx");

    //---- label3 ----
    label3.setText("DataType:");
    contentPane.add(label3, "cell 0 2");
    contentPane.add(dataType, "cell 1 2,growx");

    //---- submit ----
    submit.setText("Submit");
    contentPane.add(submit, "cell 0 3 2 1,alignx center,growx 0");
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  private JLabel label1;
  protected JTextField name;
  private JLabel label2;
  protected JTextField db;
  private JLabel label3;
  protected JComboBox dataType;
  protected JButton submit;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
