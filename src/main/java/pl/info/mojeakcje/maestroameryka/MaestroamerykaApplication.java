package pl.info.mojeakcje.maestroameryka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;
import pl.info.mojeakcje.maestroameryka.model.Industry;
import pl.info.mojeakcje.maestroameryka.model.Sector;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MaestroamerykaApplication {

    public static List<AmerykaSpolka> amerykaSpolki;
    public static List<AmerykaSpolkaStrategia> amerykaSpolkiStretegie;
    public static List<Industry> industryList = new ArrayList<>();
    public static List<Sector> sectorList = new ArrayList<>();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_HIGHGREEN = "\u001B[92m";
    public static final String ANSI_GREEN_ = "\u001B[32;4m";
    public static final String ANSI_VIOLET = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[94m";
    public static final String ANSI_YELLOW = "\u001B[33;1m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws InterruptedException {
//        final ConfigurableApplicationContext ctx =
        SpringApplication.run(MaestroamerykaApplication.class, args);
//        final DBfromCSV bean = ctx.getBean(DBfromCSV.class);
//        bean.toDBfromCSVmetod();
    }

    static {
        industryList.add(new Industry(1,"Advertising Agencies"));
        industryList.add(new Industry(2,"Aerospace & Defense"));
        industryList.add(new Industry(3,"Agricultural Inputs"));
        industryList.add(new Industry(4,"Airlines "));
        industryList.add(new Industry(5,"Aluminum"));
        industryList.add(new Industry(6,"Apparel Manufacturing"));
        industryList.add(new Industry(7,"Apparel Retail"));
        industryList.add(new Industry(8,"Asset Management"));
        industryList.add(new Industry(9,"Auto & Truck Dealerships"));
        industryList.add(new Industry(10,"Auto Manufacturers"));
        industryList.add(new Industry(11,"Auto Parts"));
        industryList.add(new Industry(12,"Banks - Diversified"));
        industryList.add(new Industry(13,"Banks - Regional"));
        industryList.add(new Industry(14,"Beverages - Brewers"));
        industryList.add(new Industry(15,"Beverages - Non-Alcoholic"));
        industryList.add(new Industry(16,"Beverages - Wineries & Distilleries"));
        industryList.add(new Industry(17,"Biotechnology"));
        industryList.add(new Industry(18,"Broadcasting"));
        industryList.add(new Industry(19,"Building Materials"));
        industryList.add(new Industry(20,"Building Products & Equipment"));
        industryList.add(new Industry(21,"Business Equipment & Supplies"));
        industryList.add(new Industry(22,"Capital Markets"));
        industryList.add(new Industry(23,"Chemicals"));
        industryList.add(new Industry(24,"Communication Equipment"));
        industryList.add(new Industry(25,"Computer Hardware"));
        industryList.add(new Industry(26,"Confectioners"));
        industryList.add(new Industry(27,"Consulting Services"));
        industryList.add(new Industry(28,"Consumer Electronics"));
        industryList.add(new Industry(29,"Copper"));
        industryList.add(new Industry(30,"Credit Services"));
        industryList.add(new Industry(31,"Department Stores"));
        industryList.add(new Industry(32,"Diagnostics & Research"));
        industryList.add(new Industry(33,"Discount Stores"));
        industryList.add(new Industry(34,"Drug Manufacturers - General"));
        industryList.add(new Industry(35,"Drug Manufacturers - Specialty & Generic"));
        industryList.add(new Industry(36,"Education & Training Services"));
        industryList.add(new Industry(37,"Electrical Equipment & Parts"));
        industryList.add(new Industry(38,"Electronic Components"));
        industryList.add(new Industry(39,"Electronic Gaming & Multimedia"));
        industryList.add(new Industry(40,"Engineering & Construction"));
        industryList.add(new Industry(41,"Entertainment"));
        industryList.add(new Industry(42,"Farm & Heavy Construction Machinery"));
        industryList.add(new Industry(43,"Farm Products"));
        industryList.add(new Industry(44,"Financial Conglomerates"));
        industryList.add(new Industry(45,"Financial Data & Stock Exchanges"));
        industryList.add(new Industry(46,"Food Distribution"));
        industryList.add(new Industry(47,"Footwear & Accessories"));
        industryList.add(new Industry(48,"Furnishings, Fixtures & Appliances"));
        industryList.add(new Industry(49,"Gambling"));
        industryList.add(new Industry(50,"Gold"));
        industryList.add(new Industry(51,"Grocery Stores"));
        industryList.add(new Industry(52,"Health Information Services"));
        industryList.add(new Industry(53,"Healthcare Plans"));
        industryList.add(new Industry(54,"Home Improvement Retail"));
        industryList.add(new Industry(55,"Household & Personal Products"));
        industryList.add(new Industry(56,"Industrial Distribution"));
        industryList.add(new Industry(57,"Information Technology Services"));
        industryList.add(new Industry(58,"Insurance - Diversified"));
        industryList.add(new Industry(59,"Insurance - Life"));
        industryList.add(new Industry(60,"Insurance - Property & Casualty"));
        industryList.add(new Industry(61,"Insurance - Specialty"));
        industryList.add(new Industry(62,"Insurance Brokers"));
        industryList.add(new Industry(63,"Integrated Freight & Logistics"));
        industryList.add(new Industry(64,"Internet Content & Information"));
        industryList.add(new Industry(65,"Internet Retail"));
        industryList.add(new Industry(66,"Leisure "));
        industryList.add(new Industry(67,"Lodging"));
        industryList.add(new Industry(68,"Luxury Goods"));
        industryList.add(new Industry(69,"Medical Care Facilities"));
        industryList.add(new Industry(70,"Medical Devices"));
        industryList.add(new Industry(71,"Medical Distribution"));
        industryList.add(new Industry(72,"Medical Instruments & Supplies"));
        industryList.add(new Industry(73,"Oil & Gas Drilling"));
        industryList.add(new Industry(74,"Oil & Gas E&P"));
        industryList.add(new Industry(75,"Oil & Gas Equipment & Services"));
        industryList.add(new Industry(76,"Oil & Gas Integrated"));
        industryList.add(new Industry(77,"Oil & Gas Midstream"));
        industryList.add(new Industry(78,"Oil & Gas Refining & Marketing"));
        industryList.add(new Industry(79,"Other Industrial Metals & Mining"));
        industryList.add(new Industry(80,"Other Precious Metals & Mining"));
        industryList.add(new Industry(81,"Packaged Foods"));
        industryList.add(new Industry(82,"Packaging & Containers"));
        industryList.add(new Industry(83,"Personal Services"));
        industryList.add(new Industry(84,"Pharmaceutical Retailers"));
        industryList.add(new Industry(85,"Publishing"));
        industryList.add(new Industry(86,"Railroads"));
        industryList.add(new Industry(87,"Real Estate Services"));
        industryList.add(new Industry(88,"Recreational Vehicles"));
        industryList.add(new Industry(89,"REIT - Diversified"));
        industryList.add(new Industry(90,"REIT - Healthcare Facilities"));
        industryList.add(new Industry(91,"REIT - Hotel & Motel"));
        industryList.add(new Industry(92,"REIT - Industrial"));
        industryList.add(new Industry(93,"REIT - Mortgage"));
        industryList.add(new Industry(94,"REIT - Office"));
        industryList.add(new Industry(95,"REIT - Residential"));
        industryList.add(new Industry(96,"REIT - Retail"));
        industryList.add(new Industry(97,"REIT - Specialty"));
        industryList.add(new Industry(98,"Rental & Leasing Services"));
        industryList.add(new Industry(99,"Residential Construction"));
        industryList.add(new Industry(100,"Resorts & Casinos"));
        industryList.add(new Industry(101,"Restaurants"));
        industryList.add(new Industry(102,"Scientific & Technical Instruments"));
        industryList.add(new Industry(103,"Semiconductor Equipment & Materials"));
        industryList.add(new Industry(104,"Semiconductors"));
        industryList.add(new Industry(105,"Silver"));
        industryList.add(new Industry(106,"Software - Application"));
        industryList.add(new Industry(107,"Software - Infrastructure"));
        industryList.add(new Industry(108,"Solar"));
        industryList.add(new Industry(109,"Specialty Business Services"));
        industryList.add(new Industry(110,"Specialty Chemicals"));
        industryList.add(new Industry(111,"Specialty Industrial Machinery"));
        industryList.add(new Industry(112,"Specialty Retail"));
        industryList.add(new Industry(113,"Staffing & Employment Services"));
        industryList.add(new Industry(114,"Steel"));
        industryList.add(new Industry(115,"Telecom Services"));
        industryList.add(new Industry(116,"Tobacco"));
        industryList.add(new Industry(117,"Tools & Accessories"));
        industryList.add(new Industry(118,"Travel Services"));
        industryList.add(new Industry(119,"Trucking"));
        industryList.add(new Industry(120,"Utilities - Diversified"));
        industryList.add(new Industry(121,"Utilities - Independent Power Producers"));
        industryList.add(new Industry(122,"Utilities - Regulated Electric"));
        industryList.add(new Industry(123,"Utilities - Regulated Gas"));
        industryList.add(new Industry(124,"Utilities - Regulated Water"));
        industryList.add(new Industry(125,"Utilities - Renewable"));
        industryList.add(new Industry(126,"Waste Management"));
        industryList.add(new Industry(127,"Puste"));

        sectorList.add(new Sector(1, "Basic Materials"));
        sectorList.add(new Sector(2, "Communication Services"));
        sectorList.add(new Sector(3, "Consumer Cyclical"));
        sectorList.add(new Sector(4, "Consumer CyclicalIndustry"));
        sectorList.add(new Sector(5, "Consumer Defensive"));
        sectorList.add(new Sector(6, "Consumer Goods"));
        sectorList.add(new Sector(7, "Energy"));
        sectorList.add(new Sector(8, "Financial Services"));
        sectorList.add(new Sector(9, "Healthcare"));
        sectorList.add(new Sector(10,"Industrials"));
        sectorList.add(new Sector(11,"Real Estate"));
        sectorList.add(new Sector(12,"Technology"));
        sectorList.add(new Sector(13,"Utilities"));
        sectorList.add(new Sector(14,"Puste"));
    }



}
