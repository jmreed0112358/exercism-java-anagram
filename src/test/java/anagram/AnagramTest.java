package anagram;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import anagram.Anagram;

public class AnagramTest {

	@Test
	public void test_constructor_EmptyString_ThrowsException() {
		try {
			Anagram detector = new Anagram("");
			fail("Supposed to catch InvalidParameterException");
		} catch ( InvalidParameterException ipe ) {
			
		} catch ( Exception e ) {
			fail("Expected to catch InvalidParameterException");
		}
	}
	
	@Test
	public void test_constructor_NullString_ThrowsException() {
		try {
			Anagram detector = new Anagram( null );
			fail("Supposed to catch NullPointerException");
		} catch ( NullPointerException ipe ) {
			
		} catch ( Exception e ) {
			fail("Expected to catch NullPointerException");
		}
	}
	
	@Test
    public void test_constructor_ValidString_UpperCaseCharacters_ReturnsExpectedResult() {
        Anagram detector = new Anagram("MASTER");
        List<String> anagrams = detector.match(Arrays.asList("stream", "pigeon", "maters"));
        assertThat(anagrams).contains("maters", "stream");
    }
	
	@Test
	public void test_constructor_ValidString_NonLetterCharacters_ReturnsExpectedResult() {
		Anagram detector = new Anagram("m333as::ter383");
        List<String> anagrams = detector.match(Arrays.asList("stream", "pigeon", "maters"));
        assertThat(anagrams).contains("maters", "stream");
	}
	
	@Test
	public void test_match_ValidString_UpperCaseCharacters_ReturnsExpectedResult() {
		Anagram detector = new Anagram("master");
		List<String> anagrams = detector.match(Arrays.asList("STREAM", "PIGEON", "maters"));
		assertThat(anagrams).contains("maters", "STREAM");
	}
	
	@Test
	public void test_match_ValidInput_NonLetterCharacters_ReturnsExpectedResult() {
		Anagram detector = new Anagram("master");
        List<String> anagrams = detector.match(Arrays.asList("st34543re&*&(*am", "pi3453ge&*(&on", "m^*&^*&at56356ers"));
        assertThat(anagrams).contains("maters", "stream");
	}
	
    @Test
    public void testNoMatches() {
        Anagram detector = new Anagram("diaper");
        assertThat(detector.match(Arrays.asList("hello", "world", "zombies", "pants"))).isEmpty();
    }

    @Test
    public void testSimpleAnagram() {
        Anagram detector = new Anagram("ant");
        List<String> anagram = detector.match(Arrays.asList("tan", "stand", "at"));
        assertThat(anagram).containsExactly("tan");
    }

    @Test
    public void testDetectMultipleAnagrams() {
        Anagram detector = new Anagram("master");
        List<String> anagrams = detector.match(Arrays.asList("stream", "pigeon", "maters"));
        assertThat(anagrams).contains("maters", "stream");
    }

    @Test
    public void testDoesNotConfuseDifferentDuplicates() {
        Anagram detector = new Anagram("galea");
        List<String> anagrams = detector.match(Arrays.asList("eagle"));
        assertThat(anagrams).isEmpty();
    }

    @Test
    public void testIdenticalWordIsNotAnagram() {
        Anagram detector = new Anagram("corn");
        List<String> anagrams = detector.match(Arrays.asList("corn", "dark", "Corn", "rank", "CORN", "cron", "park"));
        assertThat(anagrams).containsExactly("cron");
    }

    @Test
    public void testEliminateAnagramsWithSameChecksum() {
        Anagram detector = new Anagram("mass");
        assertThat(detector.match(Arrays.asList("last")).isEmpty());
    }

    @Test
    public void testEliminateAnagramSubsets() {
        Anagram detector = new Anagram("good");
        assertThat(detector.match(Arrays.asList("dog", "goody"))).isEmpty();
    }

    @Test
    public void testDetectAnagrams() {
        Anagram detector = new Anagram("listen");
        List<String> anagrams = detector.match(Arrays.asList("enlists", "google", "inlets", "banana"));
        assertThat(anagrams).contains("inlets");
    }

    @Test
    public void testMultipleAnagrams() {
        Anagram detector = new Anagram("allergy");
        List<String> anagrams = detector.match(Arrays.asList("gallery", "ballerina", "regally", "clergy", "largely", "leading"));
        assertThat(anagrams).contains("gallery", "largely", "regally");
    }

    @Test
    public void testAnagramsAreCaseInsensitive() {
        Anagram detector = new Anagram("Orchestra");
        List<String> anagrams = detector.match(Arrays.asList("cashregister", "Carthorse", "radishes"));
        assertThat(anagrams).contains("Carthorse");
    }

}
