package tests.articles;

import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import org.bson.conversions.Bson;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestNewsArticles extends ArticlesBaseTest {

    @Test(alwaysRun = true)
    public void testAuthorAge() {
        List<String> tags = Arrays.asList("folk", "flute", "instruments", "music tool");
        String articleTitle = "World Music Instrument: The Tin Whistle";
        Bson filter = Filters.in("tags", tags);
        Collection news = new Collection(DataBaseContext.getCollection(DB_NAME, NEWS_COLLECTION_NAME));
        List<JsonObject> articles = news.readObjects(filter);
        List<JsonObject> elements = articles.stream()
                .filter(art -> art.get("header").toString().contains(articleTitle))
                .collect(Collectors.toList());
        Assert.assertEquals(elements.size(), 1);
        Assert.assertEquals(elements.get(0).get("author").getAsJsonObject().get("age").getAsInt(), 29);
    }

    @Test(alwaysRun = true)
    public void checkArticlesWithAuthorUnder30() {
        Bson filter = Filters.lt("author.age", 30);
        Collection news = new Collection(DataBaseContext.getCollection(DB_NAME, NEWS_COLLECTION_NAME));
        List<JsonObject> articles = news.readObjects(filter);
        articles.stream()
                .map(art -> art.get("header").getAsString())
                .forEach(System.out::println);

        List<String> headers = articles.stream()
                .map(art -> art.get("header").getAsString())
                .collect(Collectors.toList());

        Assert.assertEquals(headers.size(), 2);
        Assert.assertTrue(headers.contains("World Music Instrument: The Tin Whistle"));
        Assert.assertTrue(headers.contains("Adapting Agile For Part-Time Teams"));
    }

    @Test(alwaysRun = true)
    public void checkAuthorNamesOver33() {
        Bson filter = Filters.gt("author.age", 33);
        Collection news = new Collection(DataBaseContext.getCollection(DB_NAME, NEWS_COLLECTION_NAME));
        List<JsonObject> articles = news.readObjects(filter);
        articles.stream()
                .map(art -> art.get("author").getAsJsonObject().get("name"))
                .forEach(System.out::println);

        List<String> names = articles.stream()
                .map(art -> art.get("author").getAsJsonObject().get("name").getAsString())
                .collect(Collectors.toList());

        Assert.assertEquals(names.size(), 2);
        Assert.assertTrue(names.contains("John Petters"));
        Assert.assertTrue(names.contains("Paul Krill"));
    }

    @Test(alwaysRun = true)
    public void test() {
        Bson filter = Filters.and(Filters.in("tags", Arrays.asList("software development", "programming")), Filters.not(Filters.in("tags", "agile")));
        Collection news = new Collection(DataBaseContext.getCollection(DB_NAME, NEWS_COLLECTION_NAME));
        List<JsonObject> articles = news.readObjects(filter);
        articles.stream()
                .map(art -> art.get("author").getAsJsonObject())
                .forEach(author -> {
                    System.out.print(author.get("name") + " ");
                    System.out.print(author.get("facebook").getAsJsonObject().get("link") + " ");
                    System.out.println(author.get("copyright") + " ");
                });

    }
}
