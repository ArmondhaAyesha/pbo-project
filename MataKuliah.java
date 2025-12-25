public class MataKuliah {

    private String kodeMatkul;
    private String namaMatkul;
    private int sks;
    private Dosen dosenPengampu;

    public MataKuliah(String kodeMatkul, String namaMatkul, int sks, Dosen dosenPengampu) {
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
        this.dosenPengampu = dosenPengampu;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public String getNamaDosen() {
        return dosenPengampu.getNama();
    }
}

