package org.learn;

import org.learn.models.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                query1(connection);
                query2(connection);
                query3(connection);
                query4(connection);
                query5(connection);
                query6(connection);
                query7(connection);
                query8(connection);
                query9(connection);
                query10(connection);
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection failed.");
        }
    }

    public static void query1(Connection conn) {
        String sql = "SELECT DISTINCT NameShip FROM Ships UNION SELECT DISTINCT Ship FROM Outcomes";
        executeAndPrintQuery(conn, sql, "1. Напишіть запит, що перераховує назви головних кораблів, наявних в базі даних (врахувати кораблі в Outcomes).");
    }

    public static void query2(Connection conn) {
        String sql = "SELECT s.Class, s.NameShip, c.Country FROM Ships s JOIN Classes c ON s.Class = c.Class WHERE c.NumGuns >= 10";
        executeAndPrintQuery(conn, sql, "2. Напишіть запит, що знаходить клас, ім'я та країну для кораблів з таблиці Ships, які мають не менше 10 знарядь.");
    }

    public static void query3(Connection conn) {
        String sql = "SELECT NameBattle FROM Battles WHERE strftime('%Y', DateBattle) NOT IN (SELECT strftime('%Y', Launched) FROM Ships)";
        executeAndPrintQuery(conn, sql, "3. Напишіть запит, що знаходить битви, які відбулися в роки, що не збігаються ні з одним з років спуску кораблів на воду.");
    }

    public static void query4(Connection conn) {
        String sql = "SELECT DISTINCT s.NameShip FROM Ships s JOIN Classes c ON s.Class = c.Class WHERE c.Bore = 16 UNION SELECT DISTINCT o.Ship FROM Outcomes o JOIN Classes c ON o.Ship = c.Class WHERE c.Bore = 16";
        executeAndPrintQuery(conn, sql, "4. Напишіть запит, що знаходить назви кораблів зі знаряддями калібру 16 дюймів (врахувати кораблі з таблиці Outcomes).");
    }

    public static void query5(Connection conn) {
        String sql = "SELECT DISTINCT o.Battle FROM Outcomes o JOIN Ships s ON o.Ship = s.NameShip WHERE s.Class = 'Kongo'";
        executeAndPrintQuery(conn, sql, "5. Напишіть запит, що знаходить битви, в яких брали участь кораблі класу Kongo з таблиці Ships.");
    }

    public static void query6(Connection conn) {
        String sql = "SELECT ROUND(AVG(NumGuns), 2) AS AvgNumGuns FROM Classes WHERE Type = 'bb'";
        executeAndPrintQuery(conn, sql, "6. Напишіть запит, що визначає середнє число знарядь для класів лінійних кораблів. Отримати результат з точністю до 2-х десяткових знаків.");
    }

    public static void query7(Connection conn) {
        String sql = "SELECT Ship FROM Outcomes WHERE Battle LIKE '%North Atlantic%' AND Result = 'sunk'";
        executeAndPrintQuery(conn, sql, "7. Напишіть запит, що знаходить кораблі, потоплені в боях в Північній Атлантиці (North Atlantic).");
    }

    public static void query8(Connection conn) {
        String sql = "SELECT DISTINCT s.Class FROM Ships s JOIN Outcomes o ON s.NameShip = o.Ship WHERE o.Result = 'sunk'";
        executeAndPrintQuery(conn, sql, "8. Напишіть запит, що знаходить класи кораблів, в яких хоча б один корабель був потоплений в битві.");
    }

    public static void query9(Connection conn) {
        String sql = "SELECT s.Class, COUNT(*) AS SunkShips FROM Ships s JOIN Outcomes o ON s.NameShip = o.Ship WHERE o.Result = 'sunk' GROUP BY s.Class";
        executeAndPrintQuery(conn, sql, "9. Напишіть запит, що знаходить для кожного класу число кораблів цього класу, потоплених в боях. В результаті повинні бути виведені наступні поля: Class і число потоплених кораблів.");
    }

    public static void query10(Connection conn) {
        String sql = "SELECT s.NameShip, c.Displacement, c.NumGuns FROM Ships s JOIN Classes c ON s.Class = c.Class JOIN Outcomes o ON s.NameShip = o.Ship WHERE o.Battle = 'Guadalcanal'";
        executeAndPrintQuery(conn, sql, "10. Напишіть запит, що виводить назву, водотоннажність і число знарядь для кожного корабля, який брав участь в битві при Гвадалканале (Guadalcanal).");
    }

    private static void executeAndPrintQuery(Connection conn, String sql, String description) {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println(description);
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}