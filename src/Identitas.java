// Parent class

//apakah diperlukan class mahasiswa satu lagi (mahasiswa_internasional)untuk menerapkan inheritance
class Identitas {
    protected String nim;
    protected String nama;
    protected String email;

    public Identitas(String nim, String nama, String email) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
    }

    public void tampilIdentitas() {
        System.out.println("NIM   : " + nim);
        System.out.println("Nama  : " + nama);
        System.out.println("Email : " + email);
    }
}