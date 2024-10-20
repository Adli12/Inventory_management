/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventory;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import datechooser.beans.DateChooserCombo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.UIManager;

/**
 *
 * @author Adli1
 */
public class Data_Barang1 extends javax.swing.JFrame {

    Connection conn;
    ResultSet rs;
    Statement stat;
    public Connection koneksi;
    String sql;

    /**
     * Creates new form Data_Barang
     */
    public Data_Barang1() {
        initComponents();
        koneksi_database();
        tampil();
        setLocationRelativeTo(this);
        dt_tgl.setLocale(Locale.ENGLISH);
    }

    public void koneksi_database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/"
                    + "db_barang23", "root", "");

            stat = koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void batal() {
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_merk.setText(null);
        txt_jumlah.setText(null);
        txt_harga.setText(null);
        txt_nama.requestFocus();
    }

    private void tampil() {
        DefaultTableModel tbl = new DefaultTableModel();

        tbl.addColumn("NO");
        tbl.addColumn("ID BARANG");
        tbl.addColumn("NAMA BARANG");
        tbl.addColumn("MERK");
        tbl.addColumn("STOK");
        tbl.addColumn("HARGA");
        tbl.addColumn("TGL MASUK");
        tbl.addColumn("EXP");

        tbl_barang.setModel(tbl);

        try {
            rs = stat.executeQuery("select * from tbl_barang ");
            int baris = 1;
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    baris,
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
                });
                baris++;
                tbl_barang.setModel(tbl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void simpan() {
        if (txt_id.getText().length() == 0
                || txt_nama.getText().length() == 0
                || txt_merk.getText().length() == 0
                || txt_jumlah.getText().length() == 0
                || txt_harga.getText().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Isi data dengan lengkap!",
                    "Dialog Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

                Calendar tglMasuk = dt_tgl.getSelectedDate();
                Calendar exp = dt_exp.getSelectedDate();

                String tgl = format.format(tglMasuk.getTime());
                Date dateMasuk = format.parse(tgl);
                Calendar calMasuk = Calendar.getInstance();
                calMasuk.setTime(dateMasuk);

                dt_tgl.setSelectedDate(calMasuk);

                String iiexp = format.format(exp.getTime());
                Date dateExp = format.parse(iiexp);
                Calendar calExp = Calendar.getInstance();
                calExp.setTime(dateExp);

                dt_exp.setSelectedDate(calExp);
                //end

                String sql = "insert into tbl_barang (id_barang , nama_barang , merk , stok , harga , tgl_masuk , exp) "
                        + "values(?,?,?,?,?,?,?)";
                PreparedStatement pst = koneksi.prepareStatement(sql);

                pst.setString(1, txt_id.getText());
                pst.setString(2, txt_nama.getText());
                pst.setString(3, txt_merk.getText());
                pst.setString(4, txt_jumlah.getText());
                pst.setString(5, txt_harga.getText());
                pst.setString(6, tgl);
                pst.setString(7, iiexp);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data barang " + txt_nama.getText() + " berhasil disimpan");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Keterangan Error: " + e);
            }
            tampil();
            batal();

        }
    }

    private void hapus() {
        if (JOptionPane.showConfirmDialog(this,
                "Apakah anda yakin akan menghapus data ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION) {
            try {
                stat.executeUpdate("delete from tbl_barang where id_barang='" + txt_id.getText() + "'");
                JOptionPane.showMessageDialog(null, "Data barang berhasil dihapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Keterangan Error :" + e);
            }
        }
        tampil();
        batal();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_merk = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        cb_pilihan = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();
        dt_exp = new datechooser.beans.DateChooserCombo();
        dt_tgl = new datechooser.beans.DateChooserCombo();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel1.setText("INVENTARIS BARANG");

        jLabel2.setText("ID BARANG");

        jLabel3.setText("NAMA BARANG");

        jLabel4.setText("MERK");

        jLabel5.setText("JUMLAH BARANG");

        jLabel6.setText("HARGA BARANG");

        jLabel7.setText("TANGGAL MASUK");

        jLabel8.setText("EXP");

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        cb_pilihan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID BARANG", "NAMA BARANG" }));

        jButton1.setText("SIMPAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("HAPUS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("BATAL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "ID BARANG", "NAMA BARANG", "MERK", "STOK", "HARGA", "TGL MASUK", "EXP"
            }
        ));
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_barang);

        dt_exp.setCalendarPreferredSize(new java.awt.Dimension(350, 200));

        dt_tgl.setCalendarPreferredSize(new java.awt.Dimension(350, 200));
        dt_tgl.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                dt_tglOnCommit(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_id)
                                    .addComponent(txt_nama)
                                    .addComponent(txt_merk)
                                    .addComponent(txt_jumlah)
                                    .addComponent(txt_harga)
                                    .addComponent(dt_exp, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(dt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(58, 58, 58)
                        .addComponent(cb_pilihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_pilihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dt_exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here
        batal();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        // TODO add your handling code here:
        try {
            int row = tbl_barang.rowAtPoint(evt.getPoint());

            String id = tbl_barang.getValueAt(row, 1).toString();
            String nama = tbl_barang.getValueAt(row, 2).toString();
            String merk = tbl_barang.getValueAt(row, 3).toString();
            String jumlah = tbl_barang.getValueAt(row, 4).toString();
            String harga = tbl_barang.getValueAt(row, 5).toString();
            String tgl = tbl_barang.getValueAt(row, 6).toString();
            String exp = tbl_barang.getValueAt(row, 7).toString();

            txt_id.setText(id);
            txt_nama.setText(nama);
            txt_merk.setText(merk);
            txt_jumlah.setText(jumlah);
            txt_harga.setText(harga);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateMasuk = inputFormat.parse(tgl);
            Date dateExp = inputFormat.parse(exp);

            Calendar calMasuk = Calendar.getInstance();
            calMasuk.setTime(dateMasuk);
            dt_tgl.setSelectedDate(calMasuk);

            Calendar calExp = Calendar.getInstance();
            calExp.setTime(dateExp);
            dt_exp.setSelectedDate(calExp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_tbl_barangMouseClicked

    private void dt_tglOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dt_tglOnCommit

    }//GEN-LAST:event_dt_tglOnCommit

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
            java.util.logging.Logger.getLogger(Data_Barang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Barang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Barang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Barang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data_Barang1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cb_pilihan;
    private datechooser.beans.DateChooserCombo dt_exp;
    private datechooser.beans.DateChooserCombo dt_tgl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_merk;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
