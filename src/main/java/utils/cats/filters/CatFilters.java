package utils.cats.filters;

import business_objects.cat.CatProperties;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import static business_objects.cat.CatProperties.COLOR;
import static business_objects.cat.CatProperties.NAME;

public class CatFilters {

    private static Bson getFilter(CatProperties property, Object value) {
        return Filters.eq(property.getValue(), value);
    }

    public static Bson getColorFilter(String color) {
        return getFilter(COLOR, color);
    }

    public static Bson getNameFilter(String name) {
        return getFilter(NAME, name);
    }

    public static Bson getAgeFilter(int age) {
        return getFilter(NAME, age);
    }
}
