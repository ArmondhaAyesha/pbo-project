public class Mahasiswa extends Identitas {

    private String kelas;  
    private Nilai nilai;    

    public Mahasiswa(String nim, String nama, String email, String kelas, Nilai nilai) {
        super(nim, nama, email);    
        this.kelas = kelas;
        this.nilai = nilai;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public Nilai getNilai() {
        return nilai;
    }

    public void setNilai(Nilai nilai) {
        this.nilai = nilai;
    }

    public void tampilMahasiswa() {
        super.tampilIdentitas(); 
        System.out.println("Kelas : " + kelas);

        if (nilai != null) {
            System.out.println("UTS   : " + nilai.getUts());
            System.out.println("UAS   : " + nilai.getUas());
            System.out.println("Tugas : " + nilai.getTugas());
            System.out.println("Rata-rata : " + nilai.hitungRataRata());
            System.out.println("Status    : " + nilai.statusKelulusan());
        }
    }

}

