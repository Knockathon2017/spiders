package utils;

import org.mongodb.morphia.mapping.DefaultCreator;
import play.Play;

public class PlayCreator extends DefaultCreator {

    @Override
    protected ClassLoader getClassLoaderForClass() {
        return Play.application().classloader();
    }
}
