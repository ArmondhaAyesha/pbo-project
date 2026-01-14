import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:khs.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {

            /* ===== TABEL MAHASISWA ===== */
            s.execute("""
                CREATE TABLE IF NOT EXISTS mahasiswa (
                    nim   TEXT PRIMARY KEY,
                    nama  TEXT NOT NULL,
                    kelas TEXT NOT NULL
                )
            """);

            /* ===== TABEL NILAI (KHS) ===== */
            s.execute("""
                CREATE TABLE IF NOT EXISTS nilai (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nim TEXT NOT NULL,
                    kode_mk TEXT NOT NULL,
                    nama_mk TEXT NOT NULL,
                    uts REAL NOT NULL,
                    uas REAL NOT NULL,
                    tugas REAL NOT NULL,
                    rata REAL NOT NULL,
                    status TEXT NOT NULL,
                    FOREIGN KEY (nim) REFERENCES mahasiswa(nim)
                        ON DELETE CASCADE
                        ON UPDATE CASCADE
                )
            """);
            s.execute("""
            CREATE TABLE IF NOT EXISTS user (
                 username TEXT PRIMARY KEY,
                 password TEXT NOT NULL
               )
           """);

            System.out.println("Database & tabel siap ✔");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
