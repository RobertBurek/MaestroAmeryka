package pl.info.mojeakcje.maestroameryka.repository.data;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Log4j2
@Repository
public abstract class CsvToDatabaseImp implements AmSpRepository {

//    protected final Logger log = LoggerFactory.getLogger(getClass());
//
//    private Connection connection;
//    private static Statement statement;
//
////    @Override
//    public void createTables(String nameTable) {
//        log.info(ANSI_BLUE + "Tworzenie tabeli " + nameTable + ANSI_RESET);
//        try {
////            String dbURL = String.format("jdbc:h2:mem:notowania");
//            String dbURL = String.format("jdbc:mysql://77.55.210.93:3306/MaestroAmeryka");
////            connection = DriverManager.getConnection(dbURL, "sa", "");
//            connection = DriverManager.getConnection(dbURL, "robert", "Robert10");
//            statement = connection.createStatement();
//        } catch (SQLException e) {
//            log.info(ANSI_RED + "Problem z otwarciem połączenia" + e.getMessage() + ANSI_RESET);
//        }
//
//        String amerykaTable = String.format(
//                "CREATE TABLE MaestroAmeryka.%s" +
//                        "(id INTEGER NOT NULL AUTO_INCREMENT, " +
//                        "ticker varchar(10), " +
//                        "name varchar(100), " +
//                        "market varchar(100), " +
//                        "sector varchar(100), " +
//                        "industry varchar(100), " +
//                        "note varchar(255), " +
//                        "day1218 varchar(15), " +
//                        "day0119 varchar(15), " +
//                        "day0219 varchar(15), " +
//                        "day0319 varchar(15), " +
//                        "day0419 varchar(15), " +
//                        "day0519 varchar(15), " +
//                        "day0619 varchar(15), " +
//                        "day0719 varchar(15), " +
//                        "day0819 varchar(15), " +
//                        "day0919 varchar(15), " +
//                        "day1019 varchar(15), " +
//                        "day1119 varchar(15), " +
//                        "day1219 varchar(15), " +
//                        "day0120 varchar(15), " +
//                        "day0220 varchar(15), " +
//                        "day0320 varchar(15), " +
//                        "day0420 varchar(15), " +
//                        "day0520 varchar(15), " +
//                        "day0620 varchar(15), " +
//                        "day0720 varchar(15), " +
//                        "PRIMARY KEY (id));",
//                nameTable);
//        try {
//            statement.execute(String.format("DROP TABLE IF EXISTS MaestroAmeryka.%s;", nameTable));
//            log.info(ANSI_HIGHGREEN + "Usunięto tabelę: " + ANSI_BLUE + nameTable + ANSI_RESET);
//            statement.execute(amerykaTable);
//            log.info(ANSI_HIGHGREEN + "Utworzono tebelę: " + ANSI_BLUE + nameTable + ANSI_RESET);
//        } catch (SQLException e) {
//            log.error(ANSI_RED + "Błąd przy tworzeniu tabeli: " + nameTable + ANSI_RESET);
//            log.error(ANSI_RED + "Dokładniej: " + e.getMessage() + ANSI_RESET);
//        }
//    }
//
////    @Override
//    public void saveAllToTable(String nameTable, Iterable<AmerykaSpolka> amerykaSpolki) throws InterruptedException {
//        for (AmerykaSpolka amSp : amerykaSpolki) {
//            String insert = String.format(
//                    "INSERT INTO MaestroAmeryka.%s (" +
//                            "ticker, " +
//                            "name, " +
//                            "market, " +
//                            "sector, " +
//                            "industry, " +
//                            "note, " +
//                            "day1218, " +
//                            "day0119, " +
//                            "day0219, " +
//                            "day0319, " +
//                            "day0419, " +
//                            "day0519, " +
//                            "day0619, " +
//                            "day0719, " +
//                            "day0819, " +
//                            "day0919, " +
//                            "day1019, " +
//                            "day1119, " +
//                            "day1219, " +
//                            "day0120, " +
//                            "day0220, " +
//                            "day0320, " +
//                            "day0420, " +
//                            "day0520, " +
//                            "day0620, " +
//                            "day0720 " +
//                            ") VALUES ('%s','%s','%s','%s','%s'," +
//                            "'%s','%s','%s','%s','%s'," +
//                            "'%s','%s','%s','%s','%s'," +
//                            "'%s','%s','%s','%s','%s'," +
//                            "'%s','%s','%s','%s','%s','%s');",
//                    nameTable,
//                    amSp.getTicker(),
//                    amSp.getName(),
//                    amSp.getSector(),
//                    amSp.getIndustry(),
//                    amSp.getIndustry(),
//                    amSp.getNote(),
//                    amSp.getDay1218(),
//                    amSp.getDay0119(),
//                    amSp.getDay0219(),
//                    amSp.getDay0319(),
//                    amSp.getDay0419(),
//                    amSp.getDay0519(),
//                    amSp.getDay0619(),
//                    amSp.getDay0719(),
//                    amSp.getDay0819(),
//                    amSp.getDay0919(),
//                    amSp.getDay1019(),
//                    amSp.getDay1119(),
//                    amSp.getDay1219(),
//                    amSp.getDay0120(),
//                    amSp.getDay0220(),
//                    amSp.getDay0320(),
//                    amSp.getDay0420(),
//                    amSp.getDay0520(),
//                    amSp.getDay0620(),
//                    amSp.getDay0720()
//            );
//            try {
//                Thread.sleep(200);
//                statement.executeUpdate(insert);
//                log.info(ANSI_BLUE +"Dodano do tabeli "+ nameTable + " spółkę: "+ amSp.getTicker() + ANSI_RESET);
//            } catch (SQLException e) {
//                log.error(ANSI_RED + "Problem z zapisem danych do tablicy - " + nameTable + " !!!" + ANSI_RESET);
//                log.error(ANSI_RED + "Błąd: " + e.getMessage() + ANSI_RESET);
//                log.error(ANSI_GREEN_ +  insert + ANSI_GREEN_);
//            }
//        }
//    }
}


