import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Main  extends JFrame {

    private JTextField tfNim, tfNama, tfEmail, tfKelas, tfUTS, tfUAS, tfTugas;
    private DefaultTableModel tableModel;

    // Menyimpan objek Mahasiswa (OOP dipakai)
    private ArrayList<Mahasiswa> dataMahasiswa = new ArrayList<>();

    public Main() {
        setTitle("Sistem Input Nilai Mahasiswa");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JLabel header = new JLabel("SISTEM INPUT DATA MAHASISWA", JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(33,150,243));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(header, BorderLayout.NORTH);

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(7,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Form Mahasiswa"));

        tfNim = new JTextField();
        tfNama = new JTextField();
        tfEmail = new JTextField();
        tfKelas = new JTextField();
        tfUTS = new JTextField();
        tfUAS = new JTextField();
        tfTugas = new JTextField();

        form.add(new JLabel("NIM"));   form.add(tfNim);
        form.add(new JLabel("Nama"));  form.add(tfNama);
        form.add(new JLabel("Email")); form.add(tfEmail);
        form.add(new JLabel("Kelas")); form.add(tfKelas);
        form.add(new JLabel("Nilai UTS"));   form.add(tfUTS);
        form.add(new JLabel("Nilai UAS"));   form.add(tfUAS);
        form.add(new JLabel("Nilai Tugas")); form.add(tfTugas);

        // ===== BUTTON =====
        JButton btnTambah = new JButton("Tambah Data");
        JButton btnReset  = new JButton("Reset");

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnTambah);
        panelBtn.add(btnReset);

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.setPreferredSize(new Dimension(320,0));
        kiri.add(form, BorderLayout.CENTER);
        kiri.add(panelBtn, BorderLayout.SOUTH);

        // ===== TABLE =====
        String[] kolom = {
                "NIM", "Nama", "Email", "Kelas",
                "UTS", "UAS", "Tugas", "Rata-rata", "Status"
        };

        tableModel = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                kiri,
                scrollPane
        );
        splitPane.setDividerLocation(320);

        add(splitPane, BorderLayout.CENTER);

        // ===== EVENT =====
        btnTambah.addActionListener(e -> tambahData());
        btnReset.addActionListener(e -> resetForm());

        setVisible(true);
    }

    // ===== IMPLEMENTASI CLASS =====
    private void tambahData() {
        try {
            // Buat objek Nilai
            Nilai nilai = new Nilai(
                    Double.parseDouble(tfUTS.getText()),
                    Double.parseDouble(tfUAS.getText()),
                    Double.parseDouble(tfTugas.getText())
            );

            // Buat objek Mahasiswa (extends Identitas)
            Mahasiswa mhs = new Mahasiswa(
                    tfNim.getText(),
                    tfNama.getText(),
                    tfEmail.getText(),
                    tfKelas.getText(),
                    nilai
            );

            // Simpan ke ArrayList (OOP)
            dataMahasiswa.add(mhs);

            // Tampilkan ke JTable
            tableModel.addRow(new Object[]{
                    mhs.nim,
                    mhs.nama,
                    mhs.email,
                    mhs.getKelas(),
                    nilai.getUts(),
                    nilai.getUas(),
                    nilai.getTugas(),
                    String.format("%.2f", nilai.hitungRataRata()),
                    nilai.statusKelulusan()
            });

            resetForm();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Nilai harus berupa angka!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        tfNim.setText("");
        tfNama.setText("");
        tfEmail.setText("");
        tfKelas.setText("");
        tfUTS.setText("");
        tfUAS.setText("");
        tfTugas.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}