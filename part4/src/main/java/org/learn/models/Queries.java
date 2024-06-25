package org.learn.models;

import java.sql.*;
import java.util.Scanner;

public class Queries {
    public static void getPCsUnder500(Connection connection) throws SQLException {
        System.out.println("Номери моделей, швидкість і розмір жорсткого диска для всіх ПК вартістю менш 500$: ");

        String query = "SELECT Model, Speed, Hd FROM PC WHERE Price < 500";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Модель: " + rs.getString("Model") +
                        ", Швидкість: " + rs.getInt("Speed") +
                        ", HD: " + rs.getInt("Hd"));
            }
        }
        System.out.println();
    }

    public static void getPrinterManufacturers(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ведіть код принтеру: ");
        String printerCode = scanner.nextLine();

        System.out.println("Виробник принтеру: ");

        String query = "SELECT DISTINCT Product.Maker " +
                "FROM Product " +
                "JOIN Printer ON Product.Model = Printer.Model " +
                "WHERE Printer.Code = '" + printerCode + "'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Виробник: " + rs.getString("Maker"));
            }
        }
        System.out.println();
    }

    public static void getLaptopsOver1000(Connection connection) throws SQLException {
        System.out.println("Номер моделі, обсяг пам'яті і розміри екранів ПК-блокнотів, ціна яких перевищує 1000$: ");

        String query = "SELECT Model, Ram, Screen FROM Laptop WHERE Price > 1000";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Модель: " + rs.getString("Model") +
                        ", RAM: " + rs.getInt("Ram") +
                        ", Екран: " + rs.getInt("Screen"));
            }
        }
        System.out.println();
    }

    public static void getColorPrinters(Connection connection) throws SQLException {
        System.out.println("Записи таблиці Printer для кольорових принтерів: ");

        String query = "SELECT * FROM Printer WHERE Color = TRUE";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Модель: " + rs.getString("Model") +
                        ", Color: " + rs.getBoolean("Color") +
                        ", Ціна: " + rs.getDouble("Price"));
            }
        }
        System.out.println();
    }

    public static void getPCsWith12xOr24xCDAndPriceUnder600(Connection connection) throws SQLException {
        System.out.println("Номери моделей, швидкість і розмір жорсткого диска ПК, що мають 12x або 24x CD і ціну менш 600$: ");

        String query = "SELECT Model, Speed, Hd FROM PC WHERE (Cd = '12x' OR Cd = '24x') AND Price < 600";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Модель: " + rs.getString("Model") +
                        ", Швидкість: " + rs.getInt("Speed") +
                        ", HD: " + rs.getInt("Hd"));
            }
        }
        System.out.println();
    }

    public static void getLaptopSpeedsByManufacturerWithMin10GbHd(Connection connection) throws SQLException {
        System.out.println("Швидкість ПК-блокнотів для кожного виробника, що випускає ПК-блокноти c об'ємом жорсткого диска не менше 10 Гбайт: ");

        String query = "SELECT DISTINCT Product.Maker, Laptop.Speed FROM Product JOIN Laptop ON Product.Model = Laptop.Model WHERE Laptop.Hd >= 10";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Виробник: " + rs.getString("Maker") +
                        ", Швидкість: " + rs.getInt("Speed"));
            }
        }
        System.out.println();
    }

    public static void getMostExpensivePrinterModel(Connection connection) throws SQLException {
        String query = "SELECT AVG(Speed) AS Avg_Speed FROM Laptop WHERE Price > 1000";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Середня швидкість ПК-блокнотів, ціна яких перевищує 1000$: " + rs.getDouble("Avg_Speed"));
            }
        }
        System.out.println();
    }

    public static void getAverageLaptopSpeedOver1000(Connection connection) throws SQLException {
        String query = "SELECT AVG(Speed) AS Avg_Speed FROM Laptop WHERE Price > 1000";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Середня швидкість ПК-блокнотів, ціна яких перевищує 1000$: " + rs.getDouble("Avg_Speed"));
            }
        }
        System.out.println();
    }

    public static void getAveragePCSpeedByManufacturerA(Connection connection) throws SQLException {
        String query = "SELECT AVG(PC.Speed) AS Avg_Speed FROM PC JOIN Product ON PC.Model = Product.Model WHERE Product.Maker = 'Acer'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Середня швидкість ПК, випущених виробником Acer: " + rs.getDouble("Avg_Speed"));
            }
        }
        System.out.println();
    }

    public static void getManufacturersWithSingleTypeAndMultipleModels(Connection connection) throws SQLException {
        System.out.println("Виробники, які випускають більше однієї моделі, при цьому всі моделі, що випускаються цим виробником є продуктами одного типу: ");

        String query = "SELECT Maker, Type FROM Product GROUP BY Maker, Type HAVING COUNT(DISTINCT Model) > 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Виробник: " + rs.getString("Maker") +
                        ", Тип: " + rs.getString("Type"));
            }
        }
        System.out.println();
    }

    public static void getDuplicateHardDriveSizes(Connection connection) throws SQLException {
        System.out.println("Розміри жорстких дисків, які збігаються у двох і більше PC: ");

        String query = "SELECT Hd FROM PC GROUP BY Hd HAVING COUNT(*) >= 2";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Розмір жорсткого диску: " + rs.getInt("Hd"));
            }
        }
        System.out.println();
    }

    public static void getCheapestColorPrinterManufacturer(Connection connection) throws SQLException {
        String query = "SELECT Product.Maker, Printer.Price FROM Printer JOIN Product ON Printer.Model = Product.Model WHERE Printer.Color = TRUE ORDER BY Printer.Price ASC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Виробник найдешевших кольоровихпринтерів: " + rs.getString("Maker") +
                        ", Ціна: " + rs.getDouble("Price"));
            }
        }
        System.out.println();
    }

    public static void getManufacturersWithAtLeastThreePCModels(Connection connection) throws SQLException {
        System.out.println("Виробники, що випускають щонайменше три різних моделі ПК: ");

        String query = "SELECT Product.Maker, COUNT(DISTINCT PC.Model) AS Num_Models FROM Product JOIN PC ON Product.Model = PC.Model GROUP BY Product.Maker HAVING COUNT(DISTINCT PC.Model) >= 3";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Виробник: " + rs.getString("Maker") +
                        ", Модель: " + rs.getInt("Num_Models"));
            }
        }
        System.out.println();
    }

    public static void getMaxPriceOfPCsByManufacturer(Connection connection) throws SQLException {
        System.out.println("Максимальна ціна ПК, що випускаються кожним виробником, у якого є моделі в таблиці PC: ");

        String query = "SELECT Product.Maker, MAX(PC.Price) AS Max_Price FROM Product JOIN PC ON Product.Model = PC.Model GROUP BY Product.Maker";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Виробник: " + rs.getString("Maker") +
                        ", Максимальна ціна: " + rs.getDouble("Max_Price"));
            }
        }
        System.out.println();
    }

    public static void getAveragePriceByPCSpeedOver600(Connection connection) throws SQLException {
        System.out.println("Середня ціна ПК для кожного значення швидкості ПК, що перевищує 600 МГц: ");

        String query = "SELECT Speed, AVG(Price) AS Avg_Price FROM PC WHERE Speed > 600 GROUP BY Speed;";
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Швидкість: " + rs.getInt("Speed") +
                        ", Середня ціна: " + rs.getDouble("Avg_Price"));
            }
        }
        System.out.println();
    }
}
