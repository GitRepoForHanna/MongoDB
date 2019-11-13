package data_base_context.crud_operations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private MongoCollection collection;

    public Collection(MongoCollection collection) {
        this.collection = collection;
    }

    public void insertDocument(Document document) {
        collection.insertOne(document);
    }

    public void insertDocuments(List<Document> documents) {
        collection.insertMany(documents);
    }

    public List<JsonObject> readDocuments(Bson filters) {
        MongoCursor cursor = collection.find(filters).cursor();
        List<JsonObject> result = new ArrayList<>();
        Gson converter = new Gson();
        while (cursor.hasNext()) {
            result.add(converter.toJsonTree(cursor.next()).getAsJsonObject());
        }
        return result;
    }

    public JsonObject readDocument(Bson filters) {
        // TODO: 11/12/2019  test that we got only 1 element
        Object object =  collection.find(filters).first();
        return new Gson().toJsonTree(object).getAsJsonObject();
    }

    public void updateDocument(Bson filter, Bson newDocument) {
        Bson updateDocumentOperation = new Document("$set", newDocument);
        collection.updateOne(filter, updateDocumentOperation);
    }

    public void updateDocuments(Bson filter, Bson newDocument) {
        Bson updateDocumentOperation = new Document("$set", newDocument);
        collection.updateMany(filter, updateDocumentOperation);
    }

    public void deleteDocument(Bson filter) {
        collection.deleteOne(filter);
    }

    public void deleteDocuments(Bson filter) {
        collection.deleteMany(filter);
    }
}
