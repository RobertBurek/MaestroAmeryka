package pl.info.mojeakcje.maestroameryka;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_YELLOW;

@Log4j2
@Service
public class DBfromCSV_D {

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


    public Iterable<AmerykaSpolkaD> readFromFile(String nameFile) {
        List<AmerykaSpolkaD> amerykaSpolki = new ArrayList<>();
        AmerykaSpolkaD amerykaSpolkaD;
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
            Double day1218New = 0.0;
            Double day0119New = 0.0;
            Double day0219New = 0.0;
            Double day0319New = 0.0;
            Double day0419New = 0.0;
            Double day0519New = 0.0;
            Double day0619New = 0.0;
            Double day0719New = 0.0;
            Double day0819New = 0.0;
            Double day0919New = 0.0;
            Double day1019New = 0.0;
            Double day1119New = 0.0;
            Double day1219New = 0.0;
            Double day0120New = 0.0;
            Double day0220New = 0.0;
            Double day0320New = 0.0;
            Double day0420New = 0.0;
            Double day0520New = 0.0;
            Double day0620New = 0.0;
            Double day0720New = 0.0;
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
                if (lineScanner.hasNextLine()) day1218New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0119New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0219New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0319New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0419New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0519New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0619New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0719New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0819New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0919New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day1019New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day1119New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day1219New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0120New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0220New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0320New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0420New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0520New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0620New = Double.parseDouble(lineScanner.next().replace(" ",""));
                if (lineScanner.hasNextLine()) day0720New = Double.parseDouble(lineScanner.next().replace(" ",""));
                amerykaSpolkaD = new AmerykaSpolkaD(tickerNew, nameNew, marketNew, sectorNew, industryNew);
                amerykaSpolkaD.setId(i);
                i++;
                amerykaSpolkaD.setNote(noteNew);
                amerykaSpolkaD.setDay1218(day1218New);
                amerykaSpolkaD.setDay0119(day0119New);
                amerykaSpolkaD.setDay0219(day0219New);
                amerykaSpolkaD.setDay0319(day0319New);
                amerykaSpolkaD.setDay0419(day0419New);
                amerykaSpolkaD.setDay0519(day0519New);
                amerykaSpolkaD.setDay0619(day0619New);
                amerykaSpolkaD.setDay0719(day0719New);
                amerykaSpolkaD.setDay0819(day0819New);
                amerykaSpolkaD.setDay0919(day0919New);
                amerykaSpolkaD.setDay1019(day1019New);
                amerykaSpolkaD.setDay1119(day1119New);
                amerykaSpolkaD.setDay1219(day1219New);
                amerykaSpolkaD.setDay0120(day0120New);
                amerykaSpolkaD.setDay0220(day0220New);
                amerykaSpolkaD.setDay0320(day0320New);
                amerykaSpolkaD.setDay0420(day0420New);
                amerykaSpolkaD.setDay0520(day0520New);
                amerykaSpolkaD.setDay0620(day0620New);
                amerykaSpolkaD.setDay0720(day0720New);
                amerykaSpolki.add(amerykaSpolkaD);
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
