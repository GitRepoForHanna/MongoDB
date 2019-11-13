package cats;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;

public class BaseTest {

    @Inject
    protected MongoCollection collection;

    public BaseTest() {

    }
}
