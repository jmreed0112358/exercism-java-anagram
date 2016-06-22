package anagram;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.NotImplementedException;

public class Anagram {
	
	private String word;
	private Map<Character, Integer> dictionary;
	
	public Anagram(String input) {
		if ( input.isEmpty( ) ) {
			throw new InvalidParameterException();
		}
		
		this.dictionary = new HashMap<Character, Integer>();
		
		// Clean up input.
		input = this.sanitize( input ).toLowerCase( );
		this.word = input;
		
		// Process test string.
		for ( int i = 0 ; i < input.length( ) ; i++ ) {
			Integer value = this.dictionary.get( input.charAt( i ) );
			if ( value == null ) {
				this.dictionary.put( input.charAt( i ), 1 );
			} else {
				value += 1;
				this.dictionary.replace( input.charAt( i ), value );
			}
		}
	}
	
	public List<String> match(List<String> words) {
		
		List<String> results = new ArrayList<String>();
		
		for ( int i = 0 ; i < words.size( ) ; i++ ) {
			String testString = this.sanitize( words.get( i ) ).toLowerCase( );
			
			if ( testString.equals( this.word ) ) {
				// Identical words are not anagrams.  Skip to the next word.
				continue;
			}
			
			Map<Character, Integer> testDictionary = new HashMap<Character, Integer>();
			
			for ( int j = 0 ; j < testString.length( ) ; j++ ) {
				Integer value = testDictionary.get( testString.charAt( j ) );
				if ( value == null ) {
					testDictionary.put( testString.charAt( j ), 1 );
				} else {
					value += 1;
					testDictionary.replace( testString.charAt( j ), value );
				}
			}
			if ( this.dictionary.equals( testDictionary ) ) {
				// Preserve capitalization in the results.
				results.add( this.sanitize( words.get( i ) ) );
			}
		}
		
		return results;
	}
	
	private String sanitize(String input) {
		String[] tokens = input.split( "\\s+" );
		String dirtyString = tokens[0];
		
		StringBuilder sb = new StringBuilder();
		for ( int i = 0 ; i < dirtyString.length( ) ; i++ ) {
			if ( Character.isLetter( dirtyString.charAt( i ) ) ) {
				sb.append( dirtyString.charAt( i ) );
			}
		}
		return sb.toString( );
	}
}
