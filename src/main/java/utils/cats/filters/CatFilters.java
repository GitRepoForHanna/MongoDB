package utils.cats.filters;

import business_objects.cat.CatProperties;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import static business_objects.cat.CatProperties.*;

public class CatFilters {

    private static Bson getEqualsFilter(CatProperties property, Object value) {
        return Filters.eq(property.getValue(), value);
    }

    public static Bson getColorFilter(String color) {
        return getEqualsFilter(COLOR, color);
    }

    public static Bson getNameFilter(String name) {
        return getEqualsFilter(NAME, name);
    }

    public static Bson getAgeFilter(int age) {
        return getEqualsFilter(AGE, age);
    }
}
