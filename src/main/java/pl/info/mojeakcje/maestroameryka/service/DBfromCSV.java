package pl.info.mojeakcje.maestroameryka.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_YELLOW;

@Log4j2
@Service
public class DBfromCSV {

    protected final Logger log = LoggerFactory.getLogger(getClass());

//    //    CsvToDatabase csvToDatabase;
//    AmSpRepository csvToDatabase;
//
//    public DBfromCSV() {
//    }
//
//    public DBfromCSV(AmSpRepository csvToDatabaseImp) {
//        this.csvToDatabase = csvToDatabaseImp;
//    }

//    @Value("${data.folder.csv}")
//    String myPath;
//
//    public void toDBfromCSVmetod() throws InterruptedException {
//
////        Path path = Paths.get("D:\\MaestroMM");
//        Path path = Paths.get(myPath);
//        log.info("Folder z danymi: " + path);
//
//        String nameTable = "ameryka_spolki";
//        long millisActualTime = System.currentTimeMillis();
//            try {
//                csvToDatabase.createTables(nameTable);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
////            csvToDatabase.saveAllToTable(nameTable, DBfromCSV.this.readFromFile(path + "\\AmerykaSpolki.csv"));
////            csvToDatabase.saveAllToTable(nameTable, amerykaSpolki);
//        amerykaSpolki.forEach(amerykaSpolka -> {
//            log.info(amerykaSpolka.toString());
//            log.info(csvToDatabase.getClass().toString());
//            csvToDatabase.save(amerykaSpolka);
//        });
////        amerykaSpolki.forEach(amerykaSpolka -> csvToDatabase.save(amerykaSpolka));
////        log.info(amerykaSpolki.iterator().next().toString());
////        csvToDatabase.save(amerykaSpolki.iterator().next());
////        csvToDatabase.saveAll(amerykaSpolki);
//        long executionTime = System.currentTimeMillis() - millisActualTime;
//        log.info("Czas wykonania wątku: " + (executionTime) + ". Wątek: " + Thread.currentThread().getName());
//    }


    public Iterable<AmerykaSpolka> readFromFile(String nameFile) {
        List<AmerykaSpolka> amerykaSpolki = new ArrayList<>();
        AmerykaSpolka amerykaSpolka;
        Scanner scanner = null;
        File text = new File(nameFile);
        try {
            scanner = new Scanner(text);
            String tickerNew = "";
            String nameNew = "";
            String marketNew = "";
            String sectorNew = "";
            String industryNew = "";
            String noteNew = "";
            String day1218New = "";
            String day0119New = "";
            String day0219New = "";
            String day0319New = "";
            String day0419New = "";
            String day0519New = "";
            String day0619New = "";
            String day0719New = "";
            String day0819New = "";
            String day0919New = "";
            String day1019New = "";
            String day1119New = "";
            String day1219New = "";
            String day0120New = "";
            String day0220New = "";
            String day0320New = "";
            String day0420New = "";
            String day0520New = "";
            String day0620New = "";
            String day0720New = "";
            String day0820New = "";
            String day0920New = "";
            String day1020New = "";
            String day1120New = "";
            String day1220New = "";
            String yTDNew = "";
            String m1TDNew = "";
            String m2TDNew = "";
            long i = 1L;
            while (scanner.hasNextLine()) {
                Scanner lineScanner = new Scanner(scanner.nextLine());
                lineScanner.useDelimiter(";");
                if (lineScanner.hasNextLine()) tickerNew = lineScanner.next();
                if (lineScanner.hasNextLine()) nameNew = lineScanner.next();
                if (lineScanner.hasNextLine()) marketNew = lineScanner.next();
                if (lineScanner.hasNextLine()) sectorNew = lineScanner.next();
                if (lineScanner.hasNextLine()) industryNew = lineScanner.next();
                if (lineScanner.hasNextLine()) noteNew = lineScanner.next();
                if (lineScanner.hasNextLine()) day1218New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0119New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0219New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0319New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0419New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0519New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0619New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0719New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0819New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0919New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1019New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1119New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1219New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0120New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0220New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0320New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0420New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0520New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0620New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0720New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0820New = lineScanner.next();
                if (lineScanner.hasNextLine()) day0920New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1020New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1120New = lineScanner.next();
                if (lineScanner.hasNextLine()) day1220New = lineScanner.next();
                if (lineScanner.hasNextLine()) yTDNew = lineScanner.next();
                if (lineScanner.hasNextLine()) m1TDNew = lineScanner.next();
                if (lineScanner.hasNextLine()) m2TDNew = lineScanner.next();
                amerykaSpolka = new AmerykaSpolka(tickerNew, nameNew, marketNew, sectorNew, industryNew);
                amerykaSpolka.setId(i);
                i++;
                amerykaSpolka.setNote(noteNew);
                amerykaSpolka.setDay1218(day1218New);
                amerykaSpolka.setDay0119(day0119New);
                amerykaSpolka.setDay0219(day0219New);
                amerykaSpolka.setDay0319(day0319New);
                amerykaSpolka.setDay0419(day0419New);
                amerykaSpolka.setDay0519(day0519New);
                amerykaSpolka.setDay0619(day0619New);
                amerykaSpolka.setDay0719(day0719New);
                amerykaSpolka.setDay0819(day0819New);
                amerykaSpolka.setDay0919(day0919New);
                amerykaSpolka.setDay1019(day1019New);
                amerykaSpolka.setDay1119(day1119New);
                amerykaSpolka.setDay1219(day1219New);
                amerykaSpolka.setDay0120(day0120New);
                amerykaSpolka.setDay0220(day0220New);
                amerykaSpolka.setDay0320(day0320New);
                amerykaSpolka.setDay0420(day0420New);
                amerykaSpolka.setDay0520(day0520New);
                amerykaSpolka.setDay0620(day0620New);
                amerykaSpolka.setDay0720(day0720New);
                amerykaSpolka.setDay0820(day0820New);
                amerykaSpolka.setDay0920(day0920New);
                amerykaSpolka.setDay1020(day1020New);
                amerykaSpolka.setDay1120(day1120New);
                amerykaSpolka.setDay1220(day1220New);
                amerykaSpolka.setyTD(yTDNew);
                amerykaSpolka.setM1TD(m1TDNew);
                amerykaSpolka.setM2TD(m2TDNew);
                amerykaSpolki.add(amerykaSpolka);
                lineScanner.close();
            }
        } catch (FileNotFoundException e) {
            log.info("Brak możliwości odczytu z pliku: " + nameFile);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        log.info(ANSI_YELLOW + "Ścieżka i plik: " + nameFile + "  -> ilość wierszy: " + amerykaSpolki.size() + ANSI_RESET);
        return amerykaSpolki;
    }
}
