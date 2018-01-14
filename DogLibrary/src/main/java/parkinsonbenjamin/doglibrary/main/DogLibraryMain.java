package parkinsonbenjamin.doglibrary.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import parkinsonbenjamin.doglibrary.dal.DalDispatcher;
import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dal.DoggoDalImpl;
import parkinsonbenjamin.doglibrary.processor.DogProcessor;

import static spark.Spark.port;

public class DogLibraryMain {

    public static void main(String[] args) {
        port(4567);

        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(DogLibraryMain.class);

        DoggoDal doggoDal = new DoggoDalImpl("localhost", 3306,
                "doggos", "ben-s", "doggospword");
        DoggoDal dalDispatcher = new DalDispatcher(doggoDal);
        DogProcessor dogProcessor = new DogProcessor(dalDispatcher);

        Router router = new Router(dogProcessor, null, null, null);
        router.setUpRoutes();

    }
}
