/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Desain;

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
 * @author user
 */

public class FORM_DATA extends javax.swing.JFrame {

    Connection conn = koneksi.koneksi.koneksi_database();
    ResultSet rs;
    Statement stat;
    String sql;

    /**
     * Creates new form DATA
     */
    public FORM_DATA() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        tampil();
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
        tbl.addColumn("TGL EXP");

        tbl_barang.setModel(tbl);

        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from tbl_barang ");
            int baris = 1;
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    baris,
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
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
                PreparedStatement pst = conn.prepareStatement(sql);

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

    private void ubah() {
        if (txt_id.getText().length() == 0
                || txt_nama.getText().length() == 0
                || txt_merk.getText().length() == 0
                || txt_jumlah.getText().length() == 0
                || txt_harga.getText().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Isi data dengan lengkap!",
                    "Dialog Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                // Format tanggal menggunakan SimpleDateFormat
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

                // Ambil tanggal dari DateChooser
                Calendar tglMasuk = dt_tgl.getSelectedDate();
                Calendar exp = dt_exp.getSelectedDate();

                // Format dan set tanggal masuk dan tanggal kadaluarsa
                String tgl = format.format(tglMasuk.getTime());
                String iiexp = format.format(exp.getTime());

                // Query update menggunakan PreparedStatement
                String sql = "UPDATE tbl_barang SET nama_barang = ?, merk = ?, stok = ?, harga = ?, tgl_masuk = ?, exp = ? WHERE id_barang = ?";
                PreparedStatement pst = conn.prepareStatement(sql);

                // Mengisi parameter PreparedStatement sesuai urutan
                pst.setString(1, txt_nama.getText());
                pst.setString(2, txt_merk.getText());
                pst.setString(3, txt_jumlah.getText());
                pst.setString(4, txt_harga.getText());
                pst.setString(5, tgl);
                pst.setString(6, iiexp);
                pst.setString(7, txt_id.getText());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data barang " + txt_nama.getText() + " berhasil diupdate");
                } else {
                    JOptionPane.showMessageDialog(null, "ID Barang tidak ditemukan. Data tidak diupdate.");
                }
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

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        logo1 = new gambar.logo();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        txt_merk = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        btn_hapus = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        txt_harga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dt_tgl = new datechooser.beans.DateChooserCombo();
        dt_exp = new datechooser.beans.DateChooserCombo();
        btn_edit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Showcard Gothic", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(203, 0, 81));
        jLabel10.setText(" SUMMER  mART");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 390, 40));

        jLabel11.setText("jl.rawabutun rt 01/03 prapatan mbeh");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        javax.swing.GroupLayout logo1Layout = new javax.swing.GroupLayout(logo1);
        logo1.setLayout(logo1Layout);
        logo1Layout.setHorizontalGroup(
            logo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        logo1Layout.setVerticalGroup(
            logo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 89, Short.MAX_VALUE)
        );

        jPanel2.add(logo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, -30, -1, -1));

        jPanel1.setBackground(new java.awt.Color(252, 132, 192));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NAMA BARANG");

        txt_id.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 0), null, null));

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "ID BARANG", "NAMA BARANG", "MERK", "JUMLAH", "HARGA", "TGL MASUK", "TGL EXP"
            }
        ));
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_barang);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("HARGA");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("JUMLAH");

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MERK");

        txt_merk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_merkActionPerformed(evt);
            }
        });

        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_batal.setText("BATAL");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID BARANG");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id Barang", "Nama Barang", " " }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TANGGAL");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("EXP.");

        dt_tgl.setCalendarPreferredSize(new java.awt.Dimension(350, 200));

        dt_exp.setCalendarPreferredSize(new java.awt.Dimension(350, 200));

        btn_edit.setText("EDIT");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(38, 38, 38)
                                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(12, 12, 12)
                                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(75, 75, 75)
                                .addComponent(txt_merk, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(57, 57, 57)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(65, 65, 65)
                                .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(dt_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(214, 214, 214)
                                        .addComponent(btn_simpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4))
                    .addComponent(txt_merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_simpan)
                        .addComponent(btn_hapus)
                        .addComponent(btn_batal)
                        .addComponent(btn_edit))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dt_exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 121, 845, 540));

        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adli1\\Downloads\\mart (2).png")); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 66, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        batal();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void txt_merkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_merkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_merkActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
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

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        simpan();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        hapus();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        ubah();
    }//GEN-LAST:event_btn_editActionPerformed

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
            java.util.logging.Logger.getLogger(FORM_DATA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FORM_DATA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FORM_DATA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FORM_DATA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            FORM_UTAMA utama = new FORM_UTAMA(); // Membuat instance FormLain
            utama.setVisible(true); // Menampilkan form lain
        });
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FORM_DATA().setVisible(true);
//            }
//            
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private datechooser.beans.DateChooserCombo dt_exp;
    private datechooser.beans.DateChooserCombo dt_tgl;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private gambar.logo logo1;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_merk;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
