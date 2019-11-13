package data_base_context;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DataBaseContext {
    private static MongoClient client;
    private static MongoDatabase database;
    private static final String DATA_BASE_NAME = "kittens";

    private DataBaseContext() {
        initClient();
    }

    private static void initClient() {
        client = new MongoClient("localhost", 27017);
    }

    private static MongoClient getClient() {
        if (null == client) {
            initClient();
        }
        return client;
    }

    private static void initDataBase() {
        database = getClient().getDatabase(DATA_BASE_NAME);
    }

    private static MongoDatabase getDataBase() {
        if (null == database) {
            initDataBase();
        }
        return database;
    }

    public static MongoCollection getCollection(String collection, boolean createIfNotExists) {
        boolean isCollectionExists = false;
        for (String name : getDataBase().listCollectionNames()) {
            if (name.equals(collection)) {
                isCollectionExists = true;
                break;
            }
        }
        if (createIfNotExists) {
            return getDataBase().getCollection(collection);
        }
        return isCollectionExists ? getDataBase().getCollection(collection) : null;
    }

    public static MongoCollection getCollection(String collection) {
        return getCollection(collection, true);
    }
}
