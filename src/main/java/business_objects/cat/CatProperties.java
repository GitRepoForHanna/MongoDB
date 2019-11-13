package business_objects.cat;

public enum CatProperties {
    ID("_id"),
    COLOR("color"),
    AGE("age"),
    NAME("name");

    private String value;

    CatProperties(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
