import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.tools.ij;

public class DBCheck {
    public static void main(String[] args) throws SQLException, IOException {

        String dbURL = "jdbc:derby://10.156.0.3:1527/WM";
        String dbCreateURL = "jdbc:derby://10.156.0.3:1527/WM;create=true";
        String user = "WM";
        String password = "WM";
        System.out.println("Trying to access DB...");

        try(Connection conn = DriverManager.getConnection(dbURL, user, password)){
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Could not access DB. Attempting to create one...");
            try(Connection conn = DriverManager.getConnection(dbCreateURL, user, password);
                InputStream script1 = new FileInputStream(new File("/var/lib/jenkins/workspace/isdp/src/main/resources/createDB.sql"));
                InputStream script2 = new FileInputStream(new File("/var/lib/jenkins/workspace/isdp/src/main/resources/initDB.sql")))
            {
                ij.runScript(conn, script1,"UTF-8",System.out,"UTF-8");
                ij.runScript(conn, script2,"UTF-8",System.out,"UTF-8");

                System.out.println("New DB created successfully");
            }
        }
    }
}
