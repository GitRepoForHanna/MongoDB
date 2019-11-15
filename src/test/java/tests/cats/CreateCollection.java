package tests.cats;

import business_objects.cat.Cat;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import data_base_context.filters.Filter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.testng.annotations.Test;
import utils.cats.CatConverter;

import java.util.Arrays;
import java.util.List;

import static utils.cats.filters.CatFilters.*;

public class CreateCollection {

    public Collection preparationForMovingDocument() {
        Collection collection = new Collection(DataBaseContext.getCollection("kittens", "red_cats"));
        Cat murka = new Cat("Red", 2, "Murka");
        collection.insertDocument(CatConverter.convertToBsonDocument(murka));
        return collection;
    }

    @Test
    public void testMovingDocumentToAnotherCollection() {
        Collection collection = preparationForMovingDocument();
        Collection newCollection = new Collection(DataBaseContext.getCollection("kittens","red_cats_copy"));
        Bson filter = Filter.getComplexFilter(getColorFilter("Red"), getNameFilter("Murka"));
        Document document = collection.readDocument(filter);
        newCollection.insertDocument(document);
        collection.deleteDocument(filter);
        collection.drop();
        newCollection.drop();
    }

    public Collection preparationForMovingDocuments() {
        Collection collection = new Collection(DataBaseContext.getCollection("kittens","start_collection"));
        Cat belka = new Cat("White", 4, "Belka");
        Cat nochka = new Cat("Black", 4, "Nochka");
        collection.insertDocuments(CatConverter.convertToBsonDocuments(Arrays.asList(belka, nochka)));
        return collection;
    }

    @Test
    public void testMovingDocumentsToAnotherCollection() {
        Collection collection = preparationForMovingDocuments();
        Collection newCollection = new Collection(DataBaseContext.getCollection("kittens","target_collection"));
        Bson filter = getAgeFilter(4);
        List<Document> documents = collection.readDocuments(filter);
        newCollection.insertDocuments(documents);
        collection.deleteDocuments(filter);
        collection.drop();
        newCollection.drop();
    }
}
