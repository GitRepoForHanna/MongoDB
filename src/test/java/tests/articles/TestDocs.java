package tests.articles;

import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import org.bson.Document;
import org.testng.annotations.Test;
import utils.document.DocumentUtils;

import java.util.List;

public class TestDocs extends ArticlesBaseTest {



    @Test
    public void testDocsSupplementation() {
        prepareCollection(DB_NAME, "docs", "src\\test\\resources\\docs.json");
        prepareCollection(DB_NAME, "auth", "src\\test\\resources\\auth.json");
        prepareCollection(DB_NAME, "photo", "src\\test\\resources\\photo.json");

        Collection docs = new Collection(DataBaseContext.getCollection(DB_NAME, "docs"));
        Collection updated_docs = new Collection(DataBaseContext.getCollection(DB_NAME, "updated_docs", true));
        List<Document> articles = docs.readAllDocuments();

        articles.forEach(article -> {
            DocumentUtils.checkAndUpdate(article);
            System.out.println(article);
        });

        updated_docs.insertDocuments(articles);

//        DataBaseContext.dropCollection(DB_NAME, "docs");
//        DataBaseContext.dropCollection(DB_NAME, "auth");
//        DataBaseContext.dropCollection(DB_NAME, "photo");
    }
}
