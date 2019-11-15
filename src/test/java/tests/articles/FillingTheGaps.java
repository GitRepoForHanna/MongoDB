package tests.articles;

import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import org.bson.Document;
import org.testng.annotations.Test;
import utils.document.DocumentUtils;

import java.util.List;

public class FillingTheGaps extends ArticlesBaseTest {

    @Test
    public void testNewsSupplementation() {
        Collection news = new Collection(DataBaseContext.getCollection(DB_NAME, NEWS_COLLECTION_NAME));
        Collection updated_news = new Collection(DataBaseContext.getCollection(DB_NAME, "updated_news"));
        List<Document> articles = news.readAllDocuments();

        articles.forEach(article -> {
            DocumentUtils.checkAndUpdate(article);
            System.out.println(article);
        });
        updated_news.insertDocuments(articles);
    }
}
