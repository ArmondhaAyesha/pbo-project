import java.util.ArrayList;
import java.util.List;
//inheritance
public class Mahasiswa extends Identitas {

    private String kelas;

    //KOMPOSISI
    private List<Nilai> daftarNilai = new ArrayList<>();

    public Mahasiswa(String nim, String nama, String kelas) {
        super(nim, nama);
        this.kelas = kelas;
    }
    
    public void tambahNilai(Nilai nilai) {
        daftarNilai.add(nilai);
    }

    public List<Nilai> getDaftarNilai() {
        return daftarNilai;
    }

    public String getKelas() {
        return kelas;
    }

    //OVERRIDING
    @Override
    public String getJenis() {
        return "Mahasiswa";
    }
}
