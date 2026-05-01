/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurantdatabasejava;
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author hasib
 */

public class RestaurantDatabaseJava {

    static Connection conn;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Connection c = null;
//        try {
//        Class.forName("org.sqlite.JDBC");
//        c = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
//        }
//        catch ( Exception e ) {
//        System.err.println("Problem Encountered");
//        }
//        System.out.println("Opened database successfully");
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/hasib/Documents/Coe848 - Labs_Databases/restaurant.db");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Restaurant Database Menu ---");
                System.out.println("1. Restaurants with total capacity > 80");
                System.out.println("2. Restaurants with Chicken based menu items");
                System.out.println("3. Tables available for a group of 4 people");
                System.out.println("4. Highest priced menu item in each restaurant respectively");
                System.out.println("5. Count Japanese restaurants");
                System.out.println("6. Highest paid employee in a Mexican Restaurant and a Indian Restaurant");
                System.out.println("7. Operating hours of Villa Grill");
                System.out.println("8. Restaurant with highest capacity");
                System.out.println("9. Items in Villa Grill that are less than $10");
                System.out.println("10. Check for table accomodating for 6-8 people in Villa Grill");
                System.out.println("11. Add Customer");
                System.out.println("12. Delete OrderHandledBy entry");
                System.out.println("13. Update Customer phone");
                System.out.println("14. Exit");
//                runQuery("SELECT * FROM Customer;");
//                PreparedStatement ps = conn.prepareStatement(
//                "DELETE FROM Customer WHERE customer_id=11");
//                ps.executeUpdate();
                
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        runQuery("SELECT name, total_capacity FROM Restaurant WHERE total_capacity > 80;");
                        break;

                    case 2:
                        runQuery("SELECT DISTINCT r.name, m.item_name FROM Restaurant r JOIN Staff s ON r.restaurant_id = s.restaurant_id JOIN MenuItem m ON s.staff_id = m.staff_id WHERE m.item_name LIKE '%Chicken%';");
                        break;

                    case 3:
                        runQuery("SELECT r.name AS restaurant_name, dt.table_number, dt.seating_capacity FROM Restaurant r JOIN DiningTable dt ON r.restaurant_id = dt.restaurant_id WHERE dt.seating_capacity >= 4;");
                        break;

                    case 4:
                        runQuery("SELECT DISTINCT t.item_name, t.price, r.name AS restaurant_name FROM MenuItem t JOIN Staff s ON t.staff_id = s.staff_id JOIN Restaurant r ON s.restaurant_id = r.restaurant_id LEFT JOIN MenuItem b ON t.price < b.price LEFT JOIN Staff s2 ON b.staff_id = s2.staff_id AND s.restaurant_id = s2.restaurant_id WHERE s2.staff_id IS NULL;");
                        break;

                    case 5:
                        runQuery("SELECT COUNT(*) AS JapaneseRestaurant_Count FROM Restaurant WHERE cuisine_type='Japanese';");
                        break;

                    case 6:
                        runQuery("SELECT s1.first_name, s1.last_name, s1.hourly_wage, r.name AS restaurant_name FROM Staff s1 JOIN Restaurant r ON s1.restaurant_id = r.restaurant_id LEFT JOIN Staff s2 ON s1.restaurant_id = s2.restaurant_id AND s1.hourly_wage < s2.hourly_wage WHERE r.cuisine_type='Mexican' OR r.cuisine_type='Indian' AND s2.staff_id IS NULL;");
                        break;

                    case 7:
                        runQuery("SELECT name, opening_hours, Closing_hours FROM Restaurant WHERE name='Villa Grill';");
                        break;

                    case 8:
                        runQuery("SELECT r1.name, r1.total_capacity FROM Restaurant r1 LEFT JOIN Restaurant r2 ON r1.total_capacity < r2.total_capacity WHERE r2.restaurant_id IS NULL;");
                        break;

                    case 9:
                        runQuery("SELECT r.name AS restaurant_name, m.item_name, m.price FROM MenuItem m JOIN Staff s ON m.staff_id=s.staff_id JOIN Restaurant r ON s.restaurant_id=r.restaurant_id WHERE r.name='Villa Grill' AND m.price<10;");
                        break;

                    case 10:
                        runQuery("SELECT r.name, dt.table_number FROM Restaurant r JOIN DiningTable dt ON r.restaurant_id=dt.restaurant_id WHERE r.name='Villa Grill' AND dt.seating_capacity BETWEEN 6 AND 8;");
                        break;

                    case 11:
                        addCustomer(sc);
                        break;

                    case 12:
                        deleteOrderHandled(sc);
                        break;

                    case 13:
                        updateCustomer(sc);
                        break;

                    case 14:
                        conn.close();
                        System.out.println("Goodbye!");
                        return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void runQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        System.out.println("\n--- RESULTS ---");
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                String colName = md.getColumnName(i);
                String value = rs.getString(i);
                System.out.print(colName + " : " + value + " | ");
            }
        System.out.println("\n----------------------------------");
        }
    }

    static void addCustomer(Scanner sc) throws SQLException {
        System.out.println("\n--- ADDING CUTOMER ---");
        System.out.println("First name:");
        String fn = sc.nextLine();
        System.out.println("Last name:");
        String ln = sc.nextLine();
        System.out.println("Email:");
        String email = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Customer(first_name,last_name,email) VALUES(?,?,?)");

        ps.setString(1, fn);
        ps.setString(2, ln);
        ps.setString(3, email);

        ps.executeUpdate();
        System.out.println("Customer added.");
    }

    static void deleteOrderHandled(Scanner sc) throws SQLException {
        System.out.println("\n--- DELETING ORDER ---");
        System.out.println("Enter Order ID:");
        int oid = sc.nextInt();
        System.out.println("Enter Staff ID:");
        int sid = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM OrderHandledBy WHERE order_id=? AND staff_id=?");

        ps.setInt(1, oid);
        ps.setInt(2, sid);

        ps.executeUpdate();
        System.out.println("Deleted.");
    }

    static void updateCustomer(Scanner sc) throws SQLException {
        System.out.println("\n--- UPDATING CUSTOMER PHONE ---");
        System.out.println("Enter Customer ID:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("New phone number:");
        String phone = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE Customer SET phone_number=? WHERE customer_id=?");

        ps.setString(1, phone);
        ps.setInt(2, id);

        ps.executeUpdate();
        System.out.println("Updated.");
    }
}