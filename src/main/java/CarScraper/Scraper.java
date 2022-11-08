package CarScraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private List<Car> cars= new ArrayList<>();
    private WebDriver driver = new ChromeDriver();

    public List<Car> getCars() {
        return cars;
    }

    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "chromedriver");
    }

    public void search(String city, String searchTerm){
        String url="https://".concat(city)
                .concat(".craigslist.org/search/")
                .concat(searchTerm);
        driver.get(url);
    }

    public void scrape(){
        boolean next=true;
        while(next){
            WebElement resultsList = driver.findElement(By.id("search-results"));
            List<WebElement> results= resultsList.findElements(By.className("result-row"));

            for(WebElement r: results) {
                BigDecimal price = new BigDecimal(r.findElement(By.className("result-price")).
                        getText().substring(1)
                        .replace(",",""));
                WebElement title = r.findElement(By.className("result-info")).
                        findElement(By.className("result-title"));

                String listingTitle = (title.getText());
                String[] split = listingTitle.split("\s");
                String make;
                String model;
                int year;
                try {
                    year = Integer.parseInt(split[0]);
                    make = split[1];
                    model = split[2].concat("\s").concat(split[3]);
                } catch (Exception e) {
                    continue;
                }
                Car c = new Car(price, make, model, year);
                cars.add(c);
            }
            try {
                driver.findElement(By.xpath("/html/body/section/form/div[5]/div[3]/span[2]/a[3]"))
                        .click();
            }catch (Exception e){
                //stop loop
                next=false;
            }
        }
        driver.quit();
    }
}
