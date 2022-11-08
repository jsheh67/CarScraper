import CarScraper.Car;
import CarScraper.Scraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        Scraper s= new Scraper();

        s.setUp();
        s.search("minneapolis","electric-cars");
        s.scrape();

//        for(Car c: s.getCars()){
//            System.out.println(c.toString());
//        }
        File file = new File("ElectricCars.csv");
        try {
            if (file.createNewFile()) {
                System.out.println("ElectricCars.csv created.");
            } else {
                System.out.println("ElectricCars.csv already exists.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try(PrintWriter writer = new PrintWriter("ElectricCars.csv")){
            writer.println("price,year,make,model");
            for(Car c: s.getCars()){
                writer.println(c.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
