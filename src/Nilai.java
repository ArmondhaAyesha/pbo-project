public class Nilai {
    private double uts;
    private double uas;
    private double tugas;

    public Nilai(double uts, double uas, double tugas) {
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
    }

    public double hitungRataRata() {
        return (uts + uas + tugas) / 3.0;
    }

    public String statusKelulusan() {
        double r = hitungRataRata();
        return (r > 70) ? "Lulus" : "Tidak Lulus";
    }

    // Getter-setter jika dibutuhkan
    public double getUts() { return uts; }
    public double getUas() { return uas; }
    public double getTugas() { return tugas; }
}
