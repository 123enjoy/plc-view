/*
 * Created by JFormDesigner on Tue Feb 20 16:11:17 CST 2024
 */

package cn.yycampus.plc.view.ui.dialog;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author admin
 */
public class NewWorkspaceDialog extends JDialog {
  public NewWorkspaceDialog(Window owner) {
    super(owner);
    initComponents();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
    label = new JLabel();
    name = new JTextField();
    label4 = new JLabel();
    plcType = new JComboBox();
    label2 = new JLabel();
    ip = new JTextField();
    label3 = new JLabel();
    port = new JTextField();
    param = new JPanel();
    panel1 = new JPanel();
    test = new JButton();
    comfirm = new JButton();

    //======== this ========
    setPreferredSize(new Dimension(400, 250));
    setVisible(true);
    setShape(null);
    var contentPane = getContentPane();
    contentPane.setLayout(new MigLayout(
      "insets 10 20 0 20,hidemode 3",
      // columns
      "[fill,fill]" +
      "[grow,fill]",
      // rows
      "[]" +
      "[]" +
      "[fill]" +
      "[]" +
      "[]" +
      "[]"));

    //---- label ----
    label.setText("name:");
    contentPane.add(label, "cell 0 0");
    contentPane.add(name, "cell 1 0");

    //---- label4 ----
    label4.setText("PLC type:");
    contentPane.add(label4, "cell 0 1");
    contentPane.add(plcType, "cell 1 1");

    //---- label2 ----
    label2.setText("IP:");
    contentPane.add(label2, "cell 0 2");
    contentPane.add(ip, "cell 1 2");

    //---- label3 ----
    label3.setText("port:");
    contentPane.add(label3, "cell 0 3");
    contentPane.add(port, "cell 1 3");

    //======== param ========
    {
      param.setLayout(new BorderLayout());
    }
    contentPane.add(param, "cell 0 4 2 1");

    //======== panel1 ========
    {
      panel1.setLayout(new FlowLayout());

      //---- test ----
      test.setText("test");
      panel1.add(test);

      //---- comfirm ----
      comfirm.setText("comfirm");
      panel1.add(comfirm);
    }
    contentPane.add(panel1, "cell 0 5 2 1");
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  private JLabel label;
  protected JTextField name;
  private JLabel label4;
  protected JComboBox plcType;
  private JLabel label2;
  protected JTextField ip;
  private JLabel label3;
  protected JTextField port;
  protected JPanel param;
  private JPanel panel1;
  protected JButton test;
  protected JButton comfirm;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
