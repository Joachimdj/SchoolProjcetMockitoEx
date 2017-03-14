package testex.jokeFetching;

import java.util.Arrays;
import java.util.List;

public class FetcherFactory implements IFetcherFactory {

    private final List<String> availableTypes =
            Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {
        //This is for you to do, but wait
        return null;
    }
}