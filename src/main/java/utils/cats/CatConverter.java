package utils.cats;

import business_objects.cat.Cat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bson.Document;

public class CatConverter {

    public static Cat readFromJson(JsonObject object) {
        return new Gson().fromJson(object, Cat.class);
    }

    public static String convertToJsonObject(Cat cat) {
        return new Gson().toJson(cat);
    }

    public static Document convertToBsonDocument(Cat cat) {
        Document document = new Document();
        document.append("color", cat.getColor())
                .append("age", cat.getAge())
                .append("name", cat.getName());
        return document;
    }
}
