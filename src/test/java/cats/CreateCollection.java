package cats;

import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.conversions.Bson;
import org.testng.annotations.Test;

import static utils.cats.filters.CatFilters.getColorFilter;
import static utils.cats.filters.CatFilters.getNameFilter;

public class CreateCollection {

    @Test
    public void testCollectionCreation() {
        Collection collection = new Collection(DataBaseContext.getCollection("cats"));
        Collection newCollection = new Collection(DataBaseContext.getCollection("my_cats"));
        Bson filter = Filter.getComplexFilter(getColorFilter("White"), getNameFilter("Druzhok"));
        collection.replaceDocument(filter, newCollection);
    }

    @Test
    public void testNewCollection() {
        Collection collection = new Collection(DataBaseContext.getCollection("cats"));
        Collection newCollection = new Collection(DataBaseContext.getCollection("my_cats", false));
        Bson filter = Filter.getComplexFilter(getColorFilter("White"), getNameFilter("Druzhok"));
        newCollection.replaceDocument(filter, collection);
    }
}
