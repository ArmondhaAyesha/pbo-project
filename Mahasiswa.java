import java.util.ArrayList;
import java.util.List;

public class Mahasiswa extends Identitas {

    private String kelas;
    private List<Nilai> daftarNilai; // KOMPOSISI

    public Mahasiswa(String nim, String nama, String kelas) {
        super(nim, nama);
        this.kelas = kelas;
        this.daftarNilai = new ArrayList<>();
    }

    @Override
    public String getJenis() {
        return "Mahasiswa";
    }

    public String getKelas() {
        return kelas;
    }

    // bagian komposisi
    public void tambahNilai(Nilai nilai) {
        daftarNilai.add(nilai);
    }

    public List<Nilai> getDaftarNilai() {
        return daftarNilai;
    }
}
