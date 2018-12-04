/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vtsantana.buildingautomation.view;

import br.com.vtsantana.buildingautomation.control.ControlConnArduino;
import br.com.vtsantana.buildingautomation.model.ModelDadosArduino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import jssc.SerialPortException;

/**
 *
 * @author vinicius
 */
public final class Start extends javax.swing.JFrame {

    ControlConnArduino connArduino;
    
    /**
     * Creates new form Start
     * @throws jssc.SerialPortException
     */
    public Start() throws SerialPortException {
        this.connArduino = new ControlConnArduino();
        initComponents();
        //====================================================================
        ActionListener classeCabulosaQueVaiResolverMeuProblema = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ModelDadosArduino modelDadArdui = new ModelDadosArduino();
                
                modelDadArdui = connArduino.getDadosArduino();
                
                if (modelDadArdui == null) {
                    System.out.println("classe nula");
                } else {
                    jLabelDadosTemperatura.setText(String.valueOf(modelDadArdui.getTemperatura()));
                    jLabelDadosSensorPresenca.setText(String.valueOf(modelDadArdui.isPresenca()));
                }
            }
        };
        new Timer(1000, classeCabulosaQueVaiResolverMeuProblema).start();
        //====================================================================    
        System.out.println("Start");
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelTituloDaJanela = new javax.swing.JLabel();
        jLabelTempFixo = new javax.swing.JLabel();
        jLabelSensorPresencaFixo = new javax.swing.JLabel();
        jLabelDadosTemperatura = new javax.swing.JLabel();
        jLabelDadosSensorPresenca = new javax.swing.JLabel();
        jButtonLigar = new javax.swing.JButton();
        jButtonDesligar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTextFieldDataInicioEvento = new javax.swing.JTextField();
        jTextFieldDataFinalEvento = new javax.swing.JTextField();
        jButtonNovo = new javax.swing.JButton();
        jTextFieldHoraFinalEvento = new javax.swing.JTextField();
        jTextFieldHoraInicioEvento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTituloDaJanela.setText("BuildingAutomation");

        jLabelTempFixo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabelTempFixo.setText("Temperatura(°C):");

        jLabelSensorPresencaFixo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabelSensorPresencaFixo.setText("Sensor de presença:");

        jLabelDadosTemperatura.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabelDadosTemperatura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDadosTemperatura.setText("0");

        jLabelDadosSensorPresenca.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabelDadosSensorPresenca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDadosSensorPresenca.setText("On");

        jButtonLigar.setText("Ligar");
        jButtonLigar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLigarActionPerformed(evt);
            }
        });

        jButtonDesligar.setText("Desligar");
        jButtonDesligar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDesligarActionPerformed(evt);
            }
        });

        jTextFieldDataInicioEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data início evento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N

        jTextFieldDataFinalEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data final evento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        jTextFieldDataFinalEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDataFinalEventoActionPerformed(evt);
            }
        });

        jButtonNovo.setText("Novo");

        jTextFieldHoraFinalEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hora final evento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        jTextFieldHoraFinalEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHoraFinalEventoActionPerformed(evt);
            }
        });

        jTextFieldHoraInicioEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hora início evento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        jTextFieldHoraInicioEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHoraInicioEventoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelTituloDaJanela)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelSensorPresencaFixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelTempFixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDadosTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDadosSensorPresenca, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonLigar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDesligar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 314, Short.MAX_VALUE)
                        .addComponent(jButtonNovo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldDataFinalEvento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(jTextFieldDataInicioEvento, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldHoraInicioEvento)
                            .addComponent(jTextFieldHoraFinalEvento))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelTituloDaJanela)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTempFixo)
                            .addComponent(jLabelDadosTemperatura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSensorPresencaFixo)
                            .addComponent(jLabelDadosSensorPresenca))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonLigar)
                            .addComponent(jButtonDesligar))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDataInicioEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldHoraInicioEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDataFinalEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldHoraFinalEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonNovo)
                                .addGap(0, 23, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLigarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLigarActionPerformed
        try {
            connArduino.enviarLigarParaArduino();
        } catch (SerialPortException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLigarActionPerformed

    private void jTextFieldDataFinalEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDataFinalEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDataFinalEventoActionPerformed

    private void jTextFieldHoraFinalEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHoraFinalEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHoraFinalEventoActionPerformed

    private void jTextFieldHoraInicioEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHoraInicioEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHoraInicioEventoActionPerformed

    private void jButtonDesligarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDesligarActionPerformed
        try {
            connArduino.enviarDesligarParaArduino();
        } catch (SerialPortException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonDesligarActionPerformed

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
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Start().setVisible(true);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDesligar;
    private javax.swing.JButton jButtonLigar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JLabel jLabelDadosSensorPresenca;
    private javax.swing.JLabel jLabelDadosTemperatura;
    private javax.swing.JLabel jLabelSensorPresencaFixo;
    private javax.swing.JLabel jLabelTempFixo;
    private javax.swing.JLabel jLabelTituloDaJanela;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldDataFinalEvento;
    private javax.swing.JTextField jTextFieldDataInicioEvento;
    private javax.swing.JTextField jTextFieldHoraFinalEvento;
    private javax.swing.JTextField jTextFieldHoraInicioEvento;
    // End of variables declaration//GEN-END:variables
}
