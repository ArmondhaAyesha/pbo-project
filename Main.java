import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    JTextField txtNIM, txtNama, txtKelas, txtUTS, txtUAS, txtTugas;
    JLabel lblNama, lblNIM, lblKelas;
    JTable table;
    DefaultTableModel model;
    JComboBox<String> cbMK, cbMahasiswa;

    public Main() {
        setTitle("KHS Mahasiswa");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel("KARTU HASIL STUDI", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(67,160,71));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        JPanel identitas = new JPanel(new GridLayout(4,2,5,5));
        identitas.setBorder(BorderFactory.createTitledBorder("Identitas Mahasiswa"));

        cbMahasiswa = new JComboBox<>();
        lblNama = new JLabel("-");
        lblNIM = new JLabel("-");
        lblKelas = new JLabel("-");

        identitas.add(new JLabel("Pilih NIM"));
        identitas.add(cbMahasiswa);
        identitas.add(new JLabel("Nama"));
        identitas.add(lblNama);
        identitas.add(new JLabel("NIM"));
        identitas.add(lblNIM);
        identitas.add(new JLabel("Kelas"));
        identitas.add(lblKelas);

        model = new DefaultTableModel(
                new String[]{"ID","Kode MK","Mata Kuliah","UTS","UAS","Tugas","Rata","Status"},0
        );

        table = new JTable(model);
        table.setRowHeight(24);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean s, boolean f, int r, int c) {

                Component x = super.getTableCellRendererComponent(t,v,s,f,r,c);
                if (!s) x.setBackground(r%2==0?new Color(245,249,245):Color.WHITE);
                if (c==7) {
                    if (v.toString().equals("LULUS"))
                        x.setBackground(new Color(200,230,201));
                    else
                        x.setBackground(new Color(255,205,210));
                }
                return x;
            }
        });

        JScrollPane scroll = new JScrollPane(table);

        JPanel tengah = new JPanel(new BorderLayout(5,5));
        tengah.add(identitas, BorderLayout.NORTH);
        tengah.add(scroll, BorderLayout.CENTER);

        JPanel input = new JPanel(new GridLayout(9,2,5,5));
        input.setBorder(BorderFactory.createTitledBorder("Input / Update Nilai"));

        txtNIM = new JTextField();
        txtNama = new JTextField();
        txtKelas = new JTextField();
        txtUTS = new JTextField();
        txtUAS = new JTextField();
        txtTugas = new JTextField();

        cbMK = new JComboBox<>(new String[]{
                "IF101 - PBO","IF102 - Basis Data","IF103 - Struktur Data",
                "IF104 - Sistem Operasi","IF105 - Jaringan"
        });

        input.add(new JLabel("NIM")); input.add(txtNIM);
        input.add(new JLabel("Nama")); input.add(txtNama);
        input.add(new JLabel("Kelas")); input.add(txtKelas);
        input.add(new JLabel("Mata Kuliah")); input.add(cbMK);
        input.add(new JLabel("UTS")); input.add(txtUTS);
        input.add(new JLabel("UAS")); input.add(txtUAS);
        input.add(new JLabel("Tugas")); input.add(txtTugas);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus  = new JButton("Hapus");

        input.add(btnTambah);
        input.add(btnUpdate);
        input.add(new JLabel(""));
        input.add(btnHapus);

        add(input, BorderLayout.WEST);
        add(tengah, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> tambahNilai());
        btnUpdate.addActionListener(e -> updateNilai());
        btnHapus.addActionListener(e -> hapusNilai());

        cbMahasiswa.addActionListener(e -> {
            if (cbMahasiswa.getSelectedItem()!=null)
                tampilkanKHS(cbMahasiswa.getSelectedItem().toString());
        });

        loadMahasiswa();
    }


    void loadMahasiswa() {
        try (Connection c = Database.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT nim FROM mahasiswa")) {

            while (r.next()) cbMahasiswa.addItem(r.getString("nim"));

        } catch (Exception e) { e.printStackTrace(); }
    }

    void tambahNilai() {
        try (Connection c = Database.getConnection()) {

            String nim = txtNIM.getText();
            String nama = txtNama.getText();
            String kelas = txtKelas.getText();

            c.prepareStatement(
                    "INSERT OR IGNORE INTO mahasiswa VALUES ('"+nim+"','"+nama+"','"+kelas+"')"
            ).executeUpdate();

            String[] mk = cbMK.getSelectedItem().toString().split(" - ");
            double uts = Double.parseDouble(txtUTS.getText());
            double uas = Double.parseDouble(txtUAS.getText());
            double tugas = Double.parseDouble(txtTugas.getText());
            double rata = (uts+uas+tugas)/3;
            String status = rata>=70?"LULUS":"TIDAK LULUS";

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO nilai (nim,kode_mk,nama_mk,uts,uas,tugas,rata,status) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1,nim);
            ps.setString(2,mk[0]);
            ps.setString(3,mk[1]);
            ps.setDouble(4,uts);
            ps.setDouble(5,uas);
            ps.setDouble(6,tugas);
            ps.setDouble(7,rata);
            ps.setString(8,status);
            ps.executeUpdate();

            if (((DefaultComboBoxModel<?>)cbMahasiswa.getModel()).getIndexOf(nim)==-1)
                cbMahasiswa.addItem(nim);

            tampilkanKHS(nim);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Input salah!");
        }
    }

    void tampilkanKHS(String nim) {
        model.setRowCount(0);

        try (Connection c = Database.getConnection()) {

            ResultSet m = c.createStatement()
                    .executeQuery("SELECT * FROM mahasiswa WHERE nim='"+nim+"'");

            if (m.next()) {
                lblNama.setText(m.getString("nama"));
                lblNIM.setText(m.getString("nim"));
                lblKelas.setText(m.getString("kelas"));
            }

            ResultSet r = c.createStatement()
                    .executeQuery("SELECT * FROM nilai WHERE nim='"+nim+"'");

            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt("id"),
                        r.getString("kode_mk"),
                        r.getString("nama_mk"),
                        r.getDouble("uts"),
                        r.getDouble("uas"),
                        r.getDouble("tugas"),
                        String.format("%.2f", r.getDouble("rata")),
                        r.getString("status")
                });
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    void updateNilai() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = Integer.parseInt(model.getValueAt(row,0).toString());

        try (Connection c = Database.getConnection()) {

            double uts = Double.parseDouble(txtUTS.getText());
            double uas = Double.parseDouble(txtUAS.getText());
            double tugas = Double.parseDouble(txtTugas.getText());
            double rata = (uts+uas+tugas)/3;
            String status = rata>=70?"LULUS":"TIDAK LULUS";

            PreparedStatement ps = c.prepareStatement(
                    "UPDATE nilai SET uts=?,uas=?,tugas=?,rata=?,status=? WHERE id=?");
            ps.setDouble(1,uts);
            ps.setDouble(2,uas);
            ps.setDouble(3,tugas);
            ps.setDouble(4,rata);
            ps.setString(5,status);
            ps.setInt(6,id);
            ps.executeUpdate();

            tampilkanKHS(lblNIM.getText());

        } catch (Exception e) { e.printStackTrace(); }
    }

    void hapusNilai() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = Integer.parseInt(model.getValueAt(row,0).toString());

        try (Connection c = Database.getConnection()) {
            c.createStatement().executeUpdate(
                    "DELETE FROM nilai WHERE id="+id);
            tampilkanKHS(lblNIM.getText());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        Database.init();
        new LoginFrame();
    }
}
