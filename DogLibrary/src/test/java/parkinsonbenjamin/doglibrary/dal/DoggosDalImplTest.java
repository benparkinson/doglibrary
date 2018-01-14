package parkinsonbenjamin.doglibrary.dal;

import org.junit.Test;
import parkinsonbenjamin.doglibrary.exceptions.DogException;

public class DoggosDalImplTest {

   @Test
   public void setupTestData() throws DogException {
      DoggoDal doggoDal = new DoggoDalImpl("localhost", 3306,
              "doggos", "ben-s", "doggospword");
      DoggoDal dalDispatcher = new DalDispatcher(doggoDal);

      dalDispatcher.addDog("Scoobert Doobert", "Great Dane");
      dalDispatcher.addDog("Butt", "Corgi");
      dalDispatcher.addDog("Cutie Pootie", "Shibe");
   }

}
