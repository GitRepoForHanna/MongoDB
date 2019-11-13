package data_base_context.filters;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter {

    public static Bson getComplexFilter(Map<String, Object> filtersMap) {
        List<Bson> filters = new ArrayList<>();
        filtersMap.forEach((key, value) -> filters.add(Filters.eq(key, value)));
        return Filters.and(filters);
    }

    public static Bson getEqualsFilter(String fieldName, String value) {
        return Filters.eq(fieldName, value);
    }

    public static Bson getComplexFilter(Bson... bsons) {
        List<Bson> filters = Stream.of(bsons).collect(Collectors.toList());
        return Filters.and(filters);
    }

}
