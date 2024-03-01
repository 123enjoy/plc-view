/*
 * Created by JFormDesigner on Thu Feb 29 14:58:13 CST 2024
 */

package cn.yycampus.plc.view.ui.panel;

import java.awt.*;
import javax.swing.*;

/**
 * @author admin
 */
public class TabPanelContainer extends JPanel {
  public TabPanelContainer() {
    initComponents();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
    tabbedPane = new JTabbedPane();

    //======== this ========
    setLayout(new BorderLayout());
    add(tabbedPane, BorderLayout.CENTER);
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  public JTabbedPane tabbedPane;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
