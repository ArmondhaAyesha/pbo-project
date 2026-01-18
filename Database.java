import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:khs.db";

    public static Connection getConnection() {
        System.out.println("Working dir: " + new java.io.File(".").getAbsolutePath());
        System.out.println("DB absolute: " + new java.io.File("khs.db").getAbsolutePath());

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {

            // reset tabel user biar struktur sesuai
            s.execute("DROP TABLE IF EXISTS user");

            // ✅ tabel untuk login/register
            s.execute("""
                CREATE TABLE IF NOT EXISTS user (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL
                )
            """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS mahasiswa (
                    nim TEXT PRIMARY KEY,
                    nama TEXT NOT NULL,
                    kelas TEXT NOT NULL
                )
            """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS nilai (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nim TEXT NOT NULL,
                    kode_mk TEXT NOT NULL,
                    nama_mk TEXT NOT NULL,
                    uts REAL,
                    uas REAL,
                    tugas REAL,
                    rata REAL,
                    status TEXT
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
