package packed;
import java.sql.*;
import java.util.*;
public class JdbcConnect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/COLLEGE";
        String user = "root";
        String password = "Root@321";
        try(Connection conn = DriverManager.getConnection(url, user, password);
        		Scanner sc = new Scanner(System.in)) {
            System.out.println("Connected to Database!");
            PreparedStatement stmt = null;
            boolean val = true;
            while(val) {
            	System.out.println("Enter the option : \n1.Insert the data \n2.Update the data\n3.Delete the data\n4.Show Entire data\n5.Exit");
            	int ch = 0;
                try {
                    ch = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("enter a valid number!");
                    sc.next();
                    continue;
                }
            	switch(ch) {
            	case 1:
            		stmt = conn.prepareStatement("INSERT INTO STUDENTS_DATA (ROLL_NO,NAME,GRADE) VALUES(?,?,?)");
            		System.out.println("Enter roll no of student: ");
            		int rollno = sc.nextInt();
            		System.out.println("Enter name of Student:");
            		String name = sc.next();
            		System.out.println("Enter GRADE of Student: ");
            		String GRADE = sc.next();
            		stmt.setInt(1, rollno);
            		stmt.setString(2, name);
            		stmt.setString(3, GRADE);
            		stmt.executeUpdate();
            		System.out.println("Student data inserted successfully!");
            		System.out.println();
            		stmt.close();
            		break;
            	case 2:
            		int flag;
            		System.out.println("Enter 1 for update the name \n Enter 2 for update the GRADE");
            		flag = sc.nextInt();
            		if(flag == 1) {
            			System.out.println("Enter the roll number where you want to update: ");
            			int roll1 = sc.nextInt();
            			System.out.println("Enter Name to Update");
            			String name1 = sc.next();
            			stmt = conn.prepareStatement("UPDATE STUDENTS_DATA SET NAME=? WHERE ROLL_NO=?");
            			stmt.setString(1, name1);
            			stmt.setInt(2, roll1);
            			stmt.executeUpdate();
            			stmt.close();
            			break;
            		}else if(flag == 2) {
            			System.out.println("Enter the roll number where you want to update: ");
            			int roll2 = sc.nextInt();
            			System.out.println("Enter Grade to Update");
            			String GRADE2 = sc.next();
            			stmt = conn.prepareStatement("UPDATE STUDENTS_DATA SET GRADE=? WHERE ROLL_NO=?");
            			stmt.setString(1, GRADE2);
            			stmt.setInt(2, roll2);
            			stmt.executeUpdate();
            			stmt.close();
            			break;
            		}
            	case 3:
            		System.out.println("Enter the Roll number of student whom you want to delete:");
            		int roll3 = sc.nextInt();
            		stmt = conn.prepareStatement("DELETE FROM STUDENTS_DATA WHERE ROLL_NO=?");
            		stmt.setInt(1, roll3);
            		stmt.executeUpdate();
            		stmt.close();
            		System.out.println("Data deleted Successfully");
            		System.out.println();
            		break;
            	case 4:
            		Statement selectStmt = conn.createStatement();
            		ResultSet rs = selectStmt.executeQuery("SELECT * FROM STUDENTS_DATA");
            		System.out.println("\n===== Student Records =====");
            		System.out.printf("%-5s %-15s %-5s%n", "Roll_No", "Name", "GRADE");
            		System.out.println("------------------------------");
                    while(rs.next()) {
                    	 System.out.printf("%-10d %-15s %-5s%n", 
                                 rs.getInt("ROLL_NO"), 
                                 rs.getString("NAME"), 
                                 rs.getString("GRADE"));
                    }
                    System.out.println();
            		rs.close();
            		stmt.close();
            		break;
            	case 5:
            		System.out.println("Exited from the Code");
            		val = false;
            	}
            	
            }
            
            conn.close();
            sc.close();
        } catch (SQLException e) {
            System.out.println("Database Error :"+ e.getMessage());
        } 
        
    }
}
