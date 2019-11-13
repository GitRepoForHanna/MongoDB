package cats;

import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.conversions.Bson;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.cats.filters.CatFilters.getColorFilter;
import static utils.cats.filters.CatFilters.getNameFilter;

public class DeleteDocument {

//    @Test
//    public void testDocumentDeleting() {
//        Collection collection = new Collection(DataBaseContext.getCollection("cats"));
//        Bson filter = Filter.getComplexFilter(getColorFilter("Green"), getNameFilter("Apple"));
//        collection.deleteDocument(Filters.and(filter));
//        List<JsonObject> result = collection.readDocuments(Filters.and(filter));
//        Assert.assertEquals(result.size(), 0);
//    }

    @Test
    public void testDocumentDeleting() {
        Collection collection = new Collection(DataBaseContext.getCollection("cats"));
        Bson filter = Filter.getComplexFilter(getColorFilter("Green"), getNameFilter("Apple"));
        collection.deleteDocument(Filters.and(filter));
        List<JsonObject> result = collection.readDocuments(Filters.and(filter));
        Assert.assertEquals(result.size(), 0);
    }
}
