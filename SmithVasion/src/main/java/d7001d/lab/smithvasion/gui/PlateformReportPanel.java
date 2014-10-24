/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.gui;

import d7001d.lab.smithvasion.models.PlateformReport;

/**
 *
 * @author leojpod
 */
public class PlateformReportPanel extends javax.swing.JPanel implements PlateformReport.AgentChangeEventListener{
  PlateformReport report;
  /**
   * Creates new form PlateformReportPanel
   * @param report
   */
  public PlateformReportPanel(PlateformReport report) {
    this.report = report;
    initComponents();
    this.plateformLabel.setText(this.report.name);
    this.updateAgentCount();
    this.report.addAgentChangeEventListener(this);
  }

  /**
   * This method is called from within the constructor to initialize the
   * form. WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    plateformLabel = new javax.swing.JLabel();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
    filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
    runningAgentsLabel = new javax.swing.JLabel();
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
    filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
    jSpinner1 = new javax.swing.JSpinner();
    addAgentsButton = new javax.swing.JButton();
    removeAgentsButton = new javax.swing.JButton();

    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

    plateformLabel.setText("P #1");
    add(plateformLabel);
    add(filler1);
    add(filler3);

    runningAgentsLabel.setText("jLabel1");
    add(runningAgentsLabel);
    add(filler2);
    add(filler4);

    jSpinner1.setMaximumSize(new java.awt.Dimension(32767, 25));
    add(jSpinner1);

    addAgentsButton.setText("+");
    add(addAgentsButton);

    removeAgentsButton.setText("-");
    add(removeAgentsButton);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addAgentsButton;
  private javax.swing.Box.Filler filler1;
  private javax.swing.Box.Filler filler2;
  private javax.swing.Box.Filler filler3;
  private javax.swing.Box.Filler filler4;
  private javax.swing.JSpinner jSpinner1;
  private javax.swing.JLabel plateformLabel;
  private javax.swing.JButton removeAgentsButton;
  private javax.swing.JLabel runningAgentsLabel;
  // End of variables declaration//GEN-END:variables

  @Override
  public void agentChangeEventOccurred(PlateformReport.AgentChangeEvent evt) {
    this.updateAgentCount();
  }

  private void updateAgentCount() {
    this.runningAgentsLabel.setText(this.report.getNumAgents().toString());
  }
}
