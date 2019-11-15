package tests.cats;

import business_objects.cat.Cat;
import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static utils.cats.CatConverter.*;
import static utils.cats.filters.CatFilters.getColorFilter;
import static utils.cats.filters.CatFilters.getNameFilter;

public class InsertDocument {

    @Test
    public void testInsertDocument() {
        Cat cat = new Cat("Red", 1, "Rizhik");
        Document document = convertToBsonDocument(convertToJsonObject(cat));
        Collection collection = new Collection(DataBaseContext.getCollection("kittens", "tests/cats"));
        collection.insertDocument(document);
        Bson filter = Filter.getComplexFilter(getColorFilter("Red"), getNameFilter("Rizhik"));
        List<JsonObject> result = collection.readObjects(Filters.and(filter));
        Assert.assertEquals(result.size(), 1);
    }


    @Test
    public void testInsertTestDocument() {
        Cat cat = new Cat("Green", 1, "Apple");
        Document document = convertToBsonDocument(cat);
        Collection collection = new Collection(DataBaseContext.getCollection("kittens", "tests/cats"));
        collection.insertDocument(document);
        Bson filter = Filter.getComplexFilter(getColorFilter("Green"), getNameFilter("Apple"));
        List<JsonObject> result = collection.readObjects(Filters.and(filter));
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void testInsertManyDocuments() {
        Cat cat1 = new Cat("Green", 1, "Apple");
        Cat cat2 = new Cat("Green", 2, "Apple2");
        List<Document> documents = convertToBsonDocuments(Arrays.asList(cat1,cat2));
        Collection collection = new Collection(DataBaseContext.getCollection("kittens", "tests/cats"));
        collection.insertDocuments(documents);
//        Bson filter = Filter.getComplexFilter(getColorFilter("Green"), getNameFilter("Apple"));
//        List<JsonObject> result = collection.readDocuments(Filters.and(filter));
//        Assert.assertEquals(result.size(), 1);
    }
}
