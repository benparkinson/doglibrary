package parkinsonbenjamin.doglibrary.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import parkinsonbenjamin.doglibrary.dal.DalDispatcher;
import parkinsonbenjamin.doglibrary.dal.DoggoDal;
import parkinsonbenjamin.doglibrary.dal.DoggoDalImpl;
import parkinsonbenjamin.doglibrary.exceptions.DogException;
import parkinsonbenjamin.doglibrary.processor.FavouritesProcessor;
import parkinsonbenjamin.doglibrary.processor.LoginProcessor;
import parkinsonbenjamin.doglibrary.processor.WithdrawalProcessor;

import static spark.Spark.port;

public class DogLibraryMain {

    public static void main(String[] args) {
        Logger logger = null;
        try {
            port(4567);

            BasicConfigurator.configure();
            logger = Logger.getLogger(DogLibraryMain.class);

            DoggoDal doggoDal = new DoggoDalImpl("localhost", 3306,
                    "doggos", "ben-s", "doggospword");
            DoggoDal dalDispatcher = new DalDispatcher(doggoDal);

            WithdrawalProcessor withdrawalProcessor = new WithdrawalProcessor();
            withdrawalProcessor.initialise(dalDispatcher);

            FavouritesProcessor favouritesProcessor = new FavouritesProcessor();
            favouritesProcessor.initialise(dalDispatcher);

            LoginProcessor loginProcessor = LoginProcessor.getInstance();
            loginProcessor.initialise(dalDispatcher);

            Router router = new Router(favouritesProcessor, loginProcessor, withdrawalProcessor);
            router.setUpRoutes();
        } catch (DogException e) {
            if (logger != null)
                logger.error("Error caught during startup!", e);

            System.exit(1);
        }
    }
}
