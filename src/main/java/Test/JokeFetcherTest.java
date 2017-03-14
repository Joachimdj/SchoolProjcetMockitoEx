package Test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import testex.*;
import testex.jokeFetching.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    @Mock private IDateFormatter dateFormatter;
    @Mock private FetcherFactory fetcherFactory;
    @Mock private EduJoke eduJoke;
    @Mock private ChuckNorris chuckNorrisJoke;
    @Mock private Moma momaJoke;
    @Mock private Tambal tambalJoke;

    private JokeFetcher fetcher;
    private Joke mockJoke;

    @Before
    public void setUp() throws Exception {
        mockJoke = new Joke("mock", "joke");
        fetcher = new JokeFetcher(dateFormatter, fetcherFactory);

        List<IJokeFetcher> fetchers = Arrays.asList(eduJoke, chuckNorrisJoke, momaJoke, tambalJoke);
        when(fetcherFactory.getJokeFetchers("eduprog,chucknorris,moma,tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(fetcherFactory.getAvailableTypes()).thenReturn(types);

        given(eduJoke.getJoke()).willReturn(mockJoke);
        given(chuckNorrisJoke.getJoke()).willReturn(mockJoke);
        given(momaJoke.getJoke()).willReturn(mockJoke);
        given(tambalJoke.getJoke()).willReturn(mockJoke);
    }

    @Test
    public void testGetAvailableTypes() {
        List<String> types = fetcher.getAvailableTypes();
        assertThat(types, containsInAnyOrder("eduprog", "chucknorris", "moma", "tambal"));
    }

    @Test
    public void checkIfValidToken() {
        boolean valid = fetcher.isStringValid("eduprog,chucknorris,moma,tambal");
        boolean invalid = fetcher.isStringValid("one,two,three,four");
        assertThat(valid, is(true));
        assertThat(invalid, is(false));
    }

    @Test
    public void testGetJokes() throws Exception {
        given(dateFormatter.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).willReturn("17 feb. 2017 10:56 AM");
        Jokes jokes = fetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen");
        assertThat(jokes.getTimeZoneString(), is("17 feb. 2017 10:56 AM"));
        verify(dateFormatter, times(1)).getFormattedDate(anyObject(), anyObject());

        List<Joke> list = jokes.getJokes();
        assertThat(list, hasSize(4));
        assertThat(jokes.getTimeZoneString(), is("17 feb. 2017 10:56 AM"));
        verify(dateFormatter, times(1)).getFormattedDate(anyObject(), anyObject());
        verify(fetcherFactory, times(1)).getJokeFetchers(anyString());

        for(Joke joke : list) {
            assertThat(joke.getJoke(), equalTo(mockJoke.getJoke()));
            assertThat(joke.getReference(), equalTo(mockJoke.getReference()));
        }

        verify(eduJoke, times(1)).getJoke();
        verify(chuckNorrisJoke, times(1)).getJoke();
        verify(momaJoke, times(1)).getJoke();
        verify(tambalJoke, times(1)).getJoke();
    }

    @Test
    public void testFetcher() {
        List<IJokeFetcher> result = fetcherFactory.getJokeFetchers("eduprog,chucknorris,moma,tambal");
        assertThat(result, hasSize(4));
        result.forEach((fetch) -> assertThat(fetch, instanceOf(IJokeFetcher.class)));
    }

}