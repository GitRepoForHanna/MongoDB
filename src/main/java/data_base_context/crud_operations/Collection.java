package data_base_context.crud_operations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Collection {

    private MongoCollection collection;

    public Collection(MongoCollection collection) {
        this.collection = collection;
    }

    public void insertDocument(Document document) {
        collection.insertOne(document);
    }

    public void insertDocuments(List<Document> documents) {
        if (null != collection) {
            collection.insertMany(documents);
        }
    }

    public List<JsonObject> readObjects(Bson filters) {
        MongoCursor cursor = collection.find(filters).cursor();
        List<JsonObject> result = new ArrayList<>();
        Gson converter = new Gson();
        while (cursor.hasNext()) {
            result.add(converter.toJsonTree(cursor.next()).getAsJsonObject());
        }
        return result;
    }

    public List<Document> readAllDocuments() {
        return (List<Document>) collection.find().into(new ArrayList<Document>());
    }

    public List<Document> readDocuments(Bson filters) {
        return (List<Document>) collection.find(filters).into(new ArrayList<Document>());
    }

    public JsonObject readObject(Bson filters) {
        if (collection.countDocuments(filters) == 1) {
            Object object = collection.find(filters).first();
            return new Gson().toJsonTree(object).getAsJsonObject();
        } else {
            throw new RuntimeException("It is expected to find only 1 document, but there are more ");
        }
    }

    public Document readDocument(Bson filters) {
        if (collection.countDocuments(filters) == 1) {
            return (Document) collection.find(filters).first();
        } else {
            throw new RuntimeException("It is expected to find only 1 document, but there are more ");
        }
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

    public void drop() {
        collection.drop();
    }
}
