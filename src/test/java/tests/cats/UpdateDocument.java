package tests.cats;

import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.cats.filters.CatFilters.getAgeFilter;
import static utils.cats.filters.CatFilters.getNameFilter;

public class UpdateDocument {

    @Test
    public void updateDocument() {
        Collection collection = new Collection(DataBaseContext.getCollection("kittens", "tests/cats"));
        Document newDocument = new Document("age", 0);
        collection.updateDocument(getNameFilter("Rizhik"), newDocument);
        Bson filter = Filter.getComplexFilter(getAgeFilter(0), getNameFilter("Rizhik"));
        List<JsonObject> result = collection.readObjects(Filters.and(filter));
        Assert.assertEquals(result.size(), 1);
    }
}
