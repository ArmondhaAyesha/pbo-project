import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Nilai mahasiswa yang ingin diinput? : ");
        int jumlah = in.nextInt();
        in.nextLine(); // buang enter

        Mahasiswa[] data = new Mahasiswa[jumlah];

        for (int i = 0; i < jumlah; i++) {
            System.out.println("\n=== Input Mahasiswa ke-" + (i + 1) + " ===");

            System.out.print("Masukkan NIM         : ");
            String nim = in.nextLine();

            System.out.print("Masukkan Nama        : ");
            String nama = in.nextLine();

            System.out.print("Masukkan Email       : ");
            String email = in.nextLine();

            System.out.print("Masukkan Kelas       : ");
            String kelas = in.nextLine();

            System.out.print("Masukkan Nilai Tugas : ");
            double tugas = in.nextDouble();

            System.out.print("Masukkan Nilai UTS   : ");
            double uts = in.nextDouble();

            System.out.print("Masukkan Nilai UAS   : ");
            double uas = in.nextDouble();
            in.nextLine(); // buang enter

            Nilai nilai = new Nilai(tugas, uts, uas);

            Mahasiswa mhs = new Mahasiswa(nim, nama, email, kelas, nilai);

            data[i] = mhs;
        }

        // OUTPUT
        System.out.println("\n===== DATA SELURUH MAHASISWA =====");
        for (int i = 0; i < jumlah; i++) {
            System.out.println("\nMahasiswa ke-" + (i + 1));
            data[i].tampilMahasiswa();
        }
    }
}
