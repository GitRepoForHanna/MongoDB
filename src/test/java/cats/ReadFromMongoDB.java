package cats;

import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.conversions.Bson;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.cats.CatConverter;

import java.util.ArrayList;
import java.util.List;

import static utils.cats.filters.CatFilters.*;


public class ReadFromMongoDB extends BaseTest{

    @Test
    public void testReadingFromDB() {
        String expectedName = "Druzhok";
        Collection collection = new Collection(DataBaseContext.getCollection("cats"));
        Bson filter = Filter.getComplexFilter(getAgeFilter(3), getNameFilter("Druzhok"));
        JsonObject result = collection.readDocument(Filters.and(filter));
        Assert.assertEquals(result.size(), 1);
        String actualName = CatConverter.readFromJson(result).getName();
        Assert.assertEquals(actualName, expectedName);
    }
}













//        result
//                .forEach(jsonObject -> {
//                    System.out.println(CatConverter.readFromJson(jsonObject).getName());
//                });