import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    private final String role; // DOSEN / MAHASISWA

    JTextField txtNIM, txtNama, txtKelas, txtUTS, txtUAS, txtTugas;
    JLabel lblNama, lblNIM, lblKelas, lblRole;
    JTable table;
    DefaultTableModel model;
    JComboBox<String> cbMK, cbMahasiswa;

    JButton btnTambah, btnUpdate, btnHapus, btnClear;

    public Main(String role) {
        this.role = role;

        setTitle("Sistem Manajemen Nilai");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        /* ================= HEADER ================= */
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("KARTU HASIL STUDI", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setOpaque(true);
        title.setBackground(new Color(67, 160, 71));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        header.add(title, BorderLayout.CENTER);

        lblRole = new JLabel("Mode: " + role, JLabel.RIGHT);
        lblRole.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        header.add(lblRole, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);

        /* ================= MENU BAR ================= */
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAkun = new JMenu("Akun");

        JMenuItem miGantiRole = new JMenuItem("Ganti Peran");
        JMenuItem miLogout = new JMenuItem("Logout");

        menuAkun.add(miGantiRole);
        menuAkun.addSeparator();
        menuAkun.add(miLogout);

        menuBar.add(menuAkun);
        setJMenuBar(menuBar);

        miGantiRole.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Kembali ke menu pilih peran?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );
            if (ok == JOptionPane.YES_OPTION) {
                dispose();
                new RoleFrame("User");
            }
        });

        miLogout.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Logout dan kembali ke login?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (ok == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        /* ================= PANEL IDENTITAS ================= */
        JPanel identitas = new JPanel(new GridLayout(4, 2, 8, 8));
        identitas.setBorder(BorderFactory.createTitledBorder("Identitas Mahasiswa"));

        cbMahasiswa = new JComboBox<>();
        lblNama = new JLabel("-");
        lblNIM = new JLabel("-");
        lblKelas = new JLabel("-");

        identitas.add(new JLabel("Pilih Mahasiswa"));
        identitas.add(cbMahasiswa);
        identitas.add(new JLabel("Nama"));
        identitas.add(lblNama);
        identitas.add(new JLabel("NIM"));
        identitas.add(lblNIM);
        identitas.add(new JLabel("Kelas"));
        identitas.add(lblKelas);

        /* ================= TABEL ================= */
        model = new DefaultTableModel(
                new String[]{"ID", "Kode MK", "Mata Kuliah", "UTS", "UAS", "Tugas", "Rata", "Status"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                Component x = super.getTableCellRendererComponent(t, v, s, f, r, c);
                if (!s) x.setBackground(r % 2 == 0 ? new Color(245, 249, 245) : Color.WHITE);
                if (c == 7 && v != null) {
                    x.setBackground(v.toString().equals("LULUS")
                            ? new Color(200, 230, 201)
                            : new Color(255, 205, 210));
                }
                return x;
            }
        });

        JScrollPane scroll = new JScrollPane(table);

        JPanel kanan = new JPanel(new BorderLayout(8, 8));
        kanan.add(identitas, BorderLayout.NORTH);
        kanan.add(scroll, BorderLayout.CENTER);

        /* ================= PANEL INPUT ================= */
        JPanel input = new JPanel(new GridLayout(10, 2, 8, 8));
        input.setBorder(BorderFactory.createTitledBorder("Input / Update Nilai"));

        txtNIM = new JTextField();
        txtNama = new JTextField();
        txtKelas = new JTextField();
        txtUTS = new JTextField();
        txtUAS = new JTextField();
        txtTugas = new JTextField();

        cbMK = new JComboBox<>(new String[]{
                "IF101 - PBO", "IF102 - Basis Data",
                "IF103 - Struktur Data", "IF104 - Sistem Operasi",
                "IF105 - Jaringan"
        });

        input.add(new JLabel("NIM")); input.add(txtNIM);
        input.add(new JLabel("Nama")); input.add(txtNama);
        input.add(new JLabel("Kelas")); input.add(txtKelas);
        input.add(new JLabel("Mata Kuliah")); input.add(cbMK);
        input.add(new JLabel("UTS")); input.add(txtUTS);
        input.add(new JLabel("UAS")); input.add(txtUAS);
        input.add(new JLabel("Tugas")); input.add(txtTugas);

        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus  = new JButton("Hapus");
        btnClear  = new JButton("Clear");

        input.add(btnTambah); input.add(btnUpdate);
        input.add(btnHapus);  input.add(btnClear);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, input, kanan);
        split.setDividerLocation(360);
        add(split, BorderLayout.CENTER);

        /* ================= ACTION ================= */
        btnTambah.addActionListener(e -> tambahNilai());
        btnUpdate.addActionListener(e -> updateNilai());
        btnHapus.addActionListener(e -> hapusNilai());
        btnClear.addActionListener(e -> clearForm());

        cbMahasiswa.addActionListener(e -> {
            if (cbMahasiswa.getSelectedItem() == null) return;
            tampilkanKHS(extractNim(cbMahasiswa.getSelectedItem().toString()));
        });

        applyRoleUI();
        loadMahasiswa();
    }

    /* ================= ROLE ================= */
    void applyRoleUI() {
        boolean dosen = role.equals("DOSEN");
        txtNIM.setEditable(dosen);
        txtNama.setEditable(dosen);
        txtKelas.setEditable(dosen);
        txtUTS.setEditable(dosen);
        txtUAS.setEditable(dosen);
        txtTugas.setEditable(dosen);
        cbMK.setEnabled(dosen);
        btnTambah.setEnabled(dosen);
        btnUpdate.setEnabled(dosen);
        btnHapus.setEnabled(dosen);
    }

    /* ================= DB LOGIC ================= */
    void loadMahasiswa() {
        cbMahasiswa.removeAllItems();
        try (Connection c = Database.getConnection();
             ResultSet r = c.createStatement().executeQuery(
                     "SELECT DISTINCT m.nim, m.nama, m.kelas FROM mahasiswa m JOIN nilai n ON m.nim=n.nim")) {
            while (r.next())
                cbMahasiswa.addItem(r.getString("nim")+" - "+r.getString("nama")+" ("+r.getString("kelas")+")");
        } catch (Exception ignored) {}
    }

    void tampilkanKHS(String nim) {
        model.setRowCount(0);
        try (Connection c = Database.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM nilai WHERE nim=?");
            ps.setString(1, nim);
            ResultSet r = ps.executeQuery();
            while (r.next())
                model.addRow(new Object[]{
                        r.getInt("id"), r.getString("kode_mk"), r.getString("nama_mk"),
                        r.getDouble("uts"), r.getDouble("uas"), r.getDouble("tugas"),
                        r.getDouble("rata"), r.getString("status")
                });
        } catch (Exception ignored) {}
    }

    void tambahNilai() {
        try (Connection c = Database.getConnection()) {

            String nim = txtNIM.getText();
            String nama = txtNama.getText();
            String kelas = txtKelas.getText();

            String mk = cbMK.getSelectedItem().toString();
            String kodeMK = mk.split(" - ")[0];
            String namaMK = mk.split(" - ")[1];

            double uts = Double.parseDouble(txtUTS.getText());
            double uas = Double.parseDouble(txtUAS.getText());
            double tugas = Double.parseDouble(txtTugas.getText());

            double rata = (uts + uas + tugas) / 3;
            String status = rata >= 70 ? "LULUS" : "TIDAK LULUS";

            // simpan mahasiswa (jika belum ada)
            PreparedStatement psMhs = c.prepareStatement(
                    "INSERT OR IGNORE INTO mahasiswa VALUES (?,?,?)");
            psMhs.setString(1, nim);
            psMhs.setString(2, nama);
            psMhs.setString(3, kelas);
            psMhs.executeUpdate();

            // simpan nilai
            PreparedStatement psNilai = c.prepareStatement(
                    "INSERT INTO nilai (nim,kode_mk,nama_mk,uts,uas,tugas,rata,status) VALUES (?,?,?,?,?,?,?,?)");
            psNilai.setString(1, nim);
            psNilai.setString(2, kodeMK);
            psNilai.setString(3, namaMK);
            psNilai.setDouble(4, uts);
            psNilai.setDouble(5, uas);
            psNilai.setDouble(6, tugas);
            psNilai.setDouble(7, rata);
            psNilai.setString(8, status);
            psNilai.executeUpdate();

            JOptionPane.showMessageDialog(this, "Nilai berhasil ditambahkan");
            loadMahasiswa();
            tampilkanKHS(nim);
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid");
        }
    }

    void updateNilai() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel");
            return;
        }

        try (Connection c = Database.getConnection()) {

            int id = (int) model.getValueAt(row, 0);

            double uts = Double.parseDouble(txtUTS.getText());
            double uas = Double.parseDouble(txtUAS.getText());
            double tugas = Double.parseDouble(txtTugas.getText());

            double rata = (uts + uas + tugas) / 3;
            String status = rata >= 70 ? "LULUS" : "TIDAK LULUS";

            PreparedStatement ps = c.prepareStatement(
                    "UPDATE nilai SET uts=?, uas=?, tugas=?, rata=?, status=? WHERE id=?");
            ps.setDouble(1, uts);
            ps.setDouble(2, uas);
            ps.setDouble(3, tugas);
            ps.setDouble(4, rata);
            ps.setString(5, status);
            ps.setInt(6, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Nilai berhasil diupdate");
            tampilkanKHS(lblNIM.getText());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update nilai");
        }
    }

    void hapusNilai() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(
                this, "Hapus nilai ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (ok != JOptionPane.YES_OPTION) return;

        try (Connection c = Database.getConnection()) {

            int id = (int) model.getValueAt(row, 0);

            PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM nilai WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Nilai berhasil dihapus");
            tampilkanKHS(lblNIM.getText());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus nilai");
        }
    }

    void clearForm() {
        txtNIM.setText(""); txtNama.setText(""); txtKelas.setText("");
        txtUTS.setText(""); txtUAS.setText(""); txtTugas.setText("");
    }

    String extractNim(String s) { return s.split(" - ")[0]; }

    public static void main(String[] args) {
        Database.init();
        new LoginFrame();
    }
}
