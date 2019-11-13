package utils.dependency_injection;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ConfigurationContext extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("DataBase"))
                .toInstance("kittens");
        bind(String.class).annotatedWith(Names.named("Collection"))
                .toInstance("cats");
    }
}
