package org.learn;

import org.learn.models.ConnectDB;
import org.learn.models.Queries;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            try {

                Queries.getPCsUnder500(connection);  // 1

                Queries.getPrinterManufacturers(connection);  // 2

                Queries.getLaptopsOver1000(connection);  // 3

                Queries.getColorPrinters(connection);  // 4

                Queries.getPCsWith12xOr24xCDAndPriceUnder600(connection);  // 5

                Queries.getLaptopSpeedsByManufacturerWithMin10GbHd(connection);  // 6

                Queries.getMostExpensivePrinterModel(connection); // 7

                Queries.getAverageLaptopSpeedOver1000(connection); // 8

                Queries.getAveragePCSpeedByManufacturerA(connection); // 9

                Queries.getManufacturersWithSingleTypeAndMultipleModels(connection); // 10

                Queries.getDuplicateHardDriveSizes(connection); // 11

                Queries.getCheapestColorPrinterManufacturer(connection); // 12

                Queries.getManufacturersWithAtLeastThreePCModels(connection); // 13

                Queries.getMaxPriceOfPCsByManufacturer(connection); // 14

                Queries.getAveragePriceByPCSpeedOver600(connection); // 15

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection failed.");
        }
    }
}
