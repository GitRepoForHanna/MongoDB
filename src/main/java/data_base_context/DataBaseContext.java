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

    private static void initDataBase(String dataBase) {
        database = getClient().getDatabase(dataBase);
    }

    private static MongoDatabase getDataBase(String dataBase) {
        if (null == database) {
            initDataBase(dataBase);
        }
        return database;
    }

    public static MongoCollection getCollection(String dataBase, String collection, boolean createIfNotExists) {
        boolean isCollectionExists = false;
        for (String name : getDataBase(dataBase).listCollectionNames()) {
            if (name.equals(collection)) {
                isCollectionExists = true;
                break;
            }
        }
        if (createIfNotExists) {
            return getDataBase(dataBase).getCollection(collection);
        }
        return isCollectionExists ? getDataBase(dataBase).getCollection(collection) : null;
    }

    public static MongoCollection getCollection(String dataBaseName, String collection) {
        return getCollection(dataBaseName, collection, true);
    }

    public static void dropCollection(String dataBase, String collection) {
        MongoCollection mongoCollection = getCollection(dataBase, collection, false);
        if(null != mongoCollection){
            mongoCollection.drop();
        }
    }
}
