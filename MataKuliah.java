public class MataKuliah {
    private String kodeMK;
    private String namaMK;
    private Dosen dosenPengampu;

    public MataKuliah(String kodeMK, String namaMK, Dosen dosenPengampu) {
        this.kodeMK = kodeMK;
        this.namaMK = namaMK;
        this.dosenPengampu = dosenPengampu;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public String getNamaMK() {
        return namaMK;
    }

    public Dosen getDosenPengampu() {
        return dosenPengampu;
    }

    @Override
    public String toString() {
        return kodeMK + " - " + namaMK;
    }
}
