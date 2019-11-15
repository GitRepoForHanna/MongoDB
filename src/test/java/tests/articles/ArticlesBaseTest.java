package tests.articles;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static tests.TestConstants.UTF_ENCODING;

public class ArticlesBaseTest {

    protected static final String DB_NAME = "news_articles";
    protected static final String NEWS_COLLECTION_NAME = "news";
    protected static final String AUTHORS_COLLECTION_NAME = "authors";
    protected static final String NEWS_FILE_PATH = "src\\test\\resources\\news.json";
    protected static final String AUTHORS_FILE_PATH = "src\\test\\resources\\authors.json";

    public void prepareCollection(String dbName, String collectionName, String filePath) {
        Collection collection = new Collection(DataBaseContext.getCollection(dbName, collectionName));
        String jsonStr = null;
        try {
            jsonStr = FileUtils.readFileToString(new File(filePath), UTF_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File is not found");
        }
        List<JsonObject> list = Arrays.asList(new Gson().fromJson(jsonStr, JsonObject[].class));
        list
                .forEach(object -> collection.insertDocument(Document.parse(object.toString())));
    }

    public void prepareNewsCollection() {
        prepareCollection(DB_NAME, NEWS_COLLECTION_NAME, NEWS_FILE_PATH);
    }

    public void prepareAuthorsCollection() {
        prepareCollection(DB_NAME, AUTHORS_COLLECTION_NAME, AUTHORS_FILE_PATH);
    }

    @BeforeMethod
    public void prepareTestCollections() {
        prepareNewsCollection();
        prepareAuthorsCollection();
    }

    @BeforeMethod
    public void dropTestCollections() {
//        DataBaseContext.dropCollection(DB_NAME, NEWS_COLLECTION_NAME);
//        DataBaseContext.dropCollection(DB_NAME, AUTHORS_COLLECTION_NAME);
    }
}
