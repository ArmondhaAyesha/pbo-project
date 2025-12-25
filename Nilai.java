public class Nilai {

    private double uts, uas, tugas;

    public Nilai(double uts, double uas, double tugas) {
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
    }

    public double hitungRataRata() {
        return (uts + uas + tugas) / 3;
    }

    public String statusKelulusan() {
        return hitungRataRata() >= 70 ? "Lulus" : "Tidak Lulus";
    }

    public double getUts() { return uts; }
    public double getUas() { return uas; }
    public double getTugas() { return tugas; }
}
