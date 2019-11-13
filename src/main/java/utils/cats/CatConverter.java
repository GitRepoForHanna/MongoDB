package utils.cats;

import business_objects.cat.Cat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static business_objects.cat.CatProperties.*;

public class CatConverter {

    public static Cat readFromJson(JsonObject object) {
        return new Gson().fromJson(object, Cat.class);
    }

    public static String convertToJsonObject(Cat cat) {
        return new Gson().toJson(cat);
    }

    public static Document convertToBsonDocument(String jsonString) {
        return Document.parse(jsonString);
    }

    public static Document convertToBsonDocument(Cat cat) {
        Document document = new Document();
        document.append(COLOR.getValue(), cat.getColor())
                .append(AGE.getValue(), cat.getAge())
                .append(NAME.getValue(), cat.getName());
        return document;
    }

    public static List<Document> convertToBsonDocuments(List<Cat> cats) {
        List<Document> documents = new ArrayList<>();
        cats.forEach(cat ->
                documents.add(convertToBsonDocument(cat))
        );
        return documents;
    }

    public static String convertToJsonString(List<Cat> cats) {
        return new Gson().toJson(cats);
    }
}
