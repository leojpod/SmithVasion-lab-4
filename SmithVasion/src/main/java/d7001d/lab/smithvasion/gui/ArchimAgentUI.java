/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.gui;

import d7001d.lab.smithvasion.gui.events.ArchimEvent;
import d7001d.lab.smithvasion.models.PlatformReport;
import d7001d.lab.smithvasion.models.PlatformListModel;
import jade.gui.GuiAgent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author leojpod
 */
public class ArchimAgentUI extends javax.swing.JFrame implements ListDataListener{
  private static final Logger logger = Logger.getLogger(ArchimAgentUI.class.getName());
  private GuiAgent uiAgent;
  public final PlatformListModel plateformsModel;
  private final PlatformReport totalReport;
  /**
   * Creates new form ArchimAgentUI
   */
  public ArchimAgentUI() {
    totalReport = new PlatformReport("Total", 0);
    initComponents();
    this.jPanel2.add(Box.createHorizontalGlue());
    this.plateformsModel = new PlatformListModel();
    this.plateformsModel.addListDataListener(this);
    
    this.updatePlateformReport(null);
    
  }

  /**
   * This method is called from within the constructor to initialize the
   * form. WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    controlPanel = new javax.swing.JPanel();
    attackLabel = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    addressInput = new javax.swing.JTextField();
    portInput = new javax.swing.JTextField();
    attackButton = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JSeparator();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
    jPanel2 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    monitoringPanel = new javax.swing.JPanel();
    plateformReportPanel = new javax.swing.JPanel();
    jSeparator2 = new javax.swing.JSeparator();
    totalActivityReportPanel = new d7001d.lab.smithvasion.gui.PlatformReportPanel(totalReport);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

    controlPanel.setLayout(new javax.swing.BoxLayout(controlPanel, javax.swing.BoxLayout.PAGE_AXIS));

    attackLabel.setText("Attack Parameters");
    controlPanel.add(attackLabel);

    jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

    addressInput.setText("IP");
    addressInput.setMaximumSize(new java.awt.Dimension(214, 25));
    addressInput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addressInputActionPerformed(evt);
      }
    });
    jPanel1.add(addressInput);

    portInput.setText("port");
    portInput.setMaximumSize(new java.awt.Dimension(45, 25));
    portInput.setPreferredSize(new java.awt.Dimension(40, 19));
    portInput.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        portInputActionPerformed(evt);
      }
    });
    jPanel1.add(portInput);

    controlPanel.add(jPanel1);

    attackButton.setText("Attack");
    attackButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    attackButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        attackButtonActionPerformed(evt);
      }
    });
    controlPanel.add(attackButton);

    jSeparator1.setMaximumSize(new java.awt.Dimension(32767, 10));
    controlPanel.add(jSeparator1);
    controlPanel.add(filler1);

    getContentPane().add(controlPanel);

    jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

    jLabel1.setText("Agent monitoring ");
    jPanel2.add(jLabel1);

    monitoringPanel.setLayout(new javax.swing.BoxLayout(monitoringPanel, javax.swing.BoxLayout.PAGE_AXIS));

    plateformReportPanel.setLayout(new javax.swing.BoxLayout(plateformReportPanel, javax.swing.BoxLayout.PAGE_AXIS));
    monitoringPanel.add(plateformReportPanel);
    monitoringPanel.add(jSeparator2);

    totalActivityReportPanel.setLayout(new javax.swing.BoxLayout(totalActivityReportPanel, javax.swing.BoxLayout.LINE_AXIS));
    monitoringPanel.add(totalActivityReportPanel);

    jScrollPane1.setViewportView(monitoringPanel);

    jPanel2.add(jScrollPane1);

    getContentPane().add(jPanel2);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void addressInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressInputActionPerformed
    this.newTargetSelected();
  }//GEN-LAST:event_addressInputActionPerformed

  private void portInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portInputActionPerformed
    this.newTargetSelected();
  }//GEN-LAST:event_portInputActionPerformed

  private void attackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attackButtonActionPerformed
    this.newTargetSelected();
  }//GEN-LAST:event_attackButtonActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(ArchimAgentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(ArchimAgentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(ArchimAgentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ArchimAgentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        new ArchimAgentUI().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField addressInput;
  private javax.swing.JButton attackButton;
  private javax.swing.JLabel attackLabel;
  private javax.swing.JPanel controlPanel;
  private javax.swing.Box.Filler filler1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JPanel monitoringPanel;
  private javax.swing.JPanel plateformReportPanel;
  private javax.swing.JTextField portInput;
  private javax.swing.JPanel totalActivityReportPanel;
  // End of variables declaration//GEN-END:variables

  @Override
  public void intervalAdded(ListDataEvent e) {
    this.updatePlateformReport(e);
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    this.updatePlateformReport(e);
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    this.updatePlateformReport(e);
  }

  private void updatePlateformReport(ListDataEvent e) {
    int before = this.plateformReportPanel.getComponents().length;
    this.plateformReportPanel.removeAll();
    int totalAgentCount = 0;
    for (int i = 0; i < this.plateformsModel.getSize(); i += 1) {
      final PlatformReport report = this.plateformsModel.getElementAt(i);
      this.plateformReportPanel.add(new PlatformReportPanel(report));
      totalAgentCount += report.getNumAgents();
    }
    this.totalReport.setNumAgents(totalAgentCount);
    logger.log(Level.INFO, "updatePlateformReport -> {0} --> {1}",
            new Object[]{before, this.plateformReportPanel.getComponents().length});
    this.plateformReportPanel.validate();
    this.plateformReportPanel.repaint();
  }

  private void newTargetSelected() {
    String address = this.addressInput.getText();
    int port;
    try {
      port = Integer.parseInt(this.portInput.getText());
    } catch (NumberFormatException ex) {
      this.portInput.setText("");
      return;
    }
    //create a UI event and send it
    this.uiAgent.postGuiEvent(new ArchimEvent.NewTargetEvent(this, address, port));
    
  }
  public void setAgent(GuiAgent agent) {
    this.uiAgent = agent;
    this.pack();
  }
}
