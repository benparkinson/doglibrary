package parkinsonbenjamin.doglibrary.main;

import org.apache.log4j.Logger;
import parkinsonbenjamin.doglibrary.Constants;
import parkinsonbenjamin.doglibrary.processor.FavouritesProcessor;
import parkinsonbenjamin.doglibrary.processor.LoginProcessor;
import parkinsonbenjamin.doglibrary.processor.WithdrawalProcessor;

import static spark.Spark.get;

public class Router {

    private static final Logger logger = Logger.getLogger(Router.class);

    private final FavouritesProcessor favouritesProcessor;
    private final LoginProcessor loginProcessor;
    private final WithdrawalProcessor withdrawalProcessor;

    public Router(FavouritesProcessor favouritesProcessor,
                  LoginProcessor loginProcessor, WithdrawalProcessor withdrawalProcessor) {

        this.favouritesProcessor = favouritesProcessor;
        this.loginProcessor = loginProcessor;
        this.withdrawalProcessor = withdrawalProcessor;
    }

    public void setUpRoutes() {
        get(Constants.DOGS, (request, response) -> {
            try {
                return withdrawalProcessor.getAllDogs();
            } catch (Exception e) {
                logger.error("Error getting dogs!", e);
                return null;
            }
        });
    }

}
