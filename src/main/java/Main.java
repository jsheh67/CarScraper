import CarScraper.Car;
import CarScraper.Scraper;

public class Main {

    public static void main(String[] args) {
        Scraper s= new Scraper();

        s.setUp();
        s.search("minneapolis","electric-cars");
        s.scrape();

        for(Car c: s.getCars()){
            System.out.println(c.toString());
        }
    }

}
