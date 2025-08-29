package packed;
import java.sql.*;
import java.util.*;

public class JdbcLearning {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/COLLEGE";
        String user = "root";
        String password = "Root@321";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("✅ Connected to Database!");
            boolean val = true;

            while (val) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Insert student data");
                System.out.println("2. Update student data");
                System.out.println("3. Delete student data");
                System.out.println("4. Show all students");
                System.out.println("5. Exit");

                int ch = 0;
                try {
                    ch = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("⚠️ Please enter a valid number!");
                    sc.next(); // clear invalid input
                    continue;
                }

                switch (ch) {
                    case 1:
                        try (PreparedStatement stmt = conn.prepareStatement(
                                "INSERT INTO STUDENTS_DATA (ROLL_NO, NAME, GRADE) VALUES (?, ?, ?)")) {
                            System.out.print("Enter roll number: ");
                            int rollno = sc.nextInt();
                            System.out.print("Enter name: ");
                            String name = sc.next();
                            System.out.print("Enter grade: ");
                            String grade = sc.next();

                            stmt.setInt(1, rollno);
                            stmt.setString(2, name);
                            stmt.setString(3, grade);
                            stmt.executeUpdate();
                            System.out.println("✅ Student inserted successfully!");
                        } catch (SQLException e) {
                            System.out.println("⚠️ Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Enter roll number to update: ");
                        int rollUpdate = sc.nextInt();
                        System.out.print("Enter new name (or press Enter to skip): ");
                        String newName = sc.next();
                        System.out.print("Enter new grade (or press Enter to skip): ");
                        String newGrade = sc.next();

                        try (PreparedStatement stmt = conn.prepareStatement(
                                "UPDATE STUDENTS_DATA SET NAME=?, GRADE=? WHERE ROLL_NO=?")) {
                            stmt.setString(1, newName);
                            stmt.setString(2, newGrade);
                            stmt.setInt(3, rollUpdate);
                            int rows = stmt.executeUpdate();
                            if (rows > 0) System.out.println("✅ Student updated!");
                            else System.out.println("⚠️ No student found with this roll number.");
                        } catch (SQLException e) {
                            System.out.println("⚠️ Error: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter roll number to delete: ");
                        int rollDelete = sc.nextInt();
                        try (PreparedStatement stmt = conn.prepareStatement(
                                "DELETE FROM STUDENTS_DATA WHERE ROLL_NO=?")) {
                            stmt.setInt(1, rollDelete);
                            int rows = stmt.executeUpdate();
                            if (rows > 0) System.out.println("✅ Student deleted!");
                            else System.out.println("⚠️ No student found with this roll number.");
                        } catch (SQLException e) {
                            System.out.println("⚠️ Error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS_DATA")) {
                            System.out.println("\n===== Student Records =====");
                            System.out.printf("%-10s %-15s %-5s%n", "Roll_No", "Name", "Grade");
                            System.out.println("-----------------------------");
                            while (rs.next()) {
                                System.out.printf("%-10d %-15s %-5s%n",
                                        rs.getInt("ROLL_NO"),
                                        rs.getString("NAME"),
                                        rs.getString("GRADE"));
                            }
                        } catch (SQLException e) {
                            System.out.println("⚠️ Error: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("Exited from program ✅");
                        val = false;
                        break;

                    default:
                        System.out.println("⚠️ Invalid option! Choose between 1-5.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }
}
