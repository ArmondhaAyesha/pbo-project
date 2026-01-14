public class Nilai implements Penilaian {

    private Mahasiswa mahasiswa;   // asosiasi
    private MataKuliah mataKuliah; // agregasi
    private double uts, uas, tugas;

    public Nilai(Mahasiswa mahasiswa, MataKuliah mataKuliah,
                 double uts, double uas, double tugas) {
        this.mahasiswa = mahasiswa;
        this.mataKuliah = mataKuliah;
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
    }

    // overriding
    @Override
    public double hitungRata() {
        return (uts + uas + tugas) / 3;
    }

    // overloading
    public double hitungRata(double uts, double uas, double tugas) {
        return (uts + uas + tugas) / 3;
    }

    // overriding
    @Override
    public String getStatus() {
        return hitungRata() >= 70 ? "LULUS" : "TIDAK LULUS";
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }
}
