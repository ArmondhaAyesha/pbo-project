public abstract class Identitas {
    protected String id;
    protected String nama;

    public Identitas(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public abstract String getJenis();

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
}
