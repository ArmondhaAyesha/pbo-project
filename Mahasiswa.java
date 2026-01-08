public class Mahasiswa extends Identitas {
    private String kelas;

    public Mahasiswa(String nim, String nama, String kelas) {
        super(nim, nama);
        this.kelas = kelas;
    }

    @Override
    public String getJenis() {
        return "Mahasiswa";
    }

    public String getKelas() {
        return kelas;
    }
}
