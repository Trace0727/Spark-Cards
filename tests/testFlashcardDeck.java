package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.Test;

import flashcards.Flashcard;
import flashcards.FlashcardDeck;

public class testFlashcardDeck {
	private FlashcardDeck deck;
	private Flashcard flashcard;
	private Flashcard flashcard2;

	@Test
	public void testGetName() {
		deck = new FlashcardDeck("Test Deck");

	    Exception exception = assertThrows(IllegalArgumentException.class, () -> new FlashcardDeck(null));
	    assertEquals("Deck name cannot be null or empty.", exception.getMessage(), 
	                 "Deck constructor should throw an exception for a null name.");
	    
	    Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new FlashcardDeck(""));
	    assertEquals("Deck name cannot be null or empty.", exception2.getMessage(), 
                "Deck constructor should throw an exception for an empty name.");
	    
	    assertEquals(deck.getName(), "Test Deck", "Deck names should match.");
	}


	@Test
	public void testRemoveFlashcard() {
		deck = new FlashcardDeck("Test Deck");
		flashcard = new Flashcard("1", "2");
		
		deck.addFlashcard(flashcard);
		
		boolean removed = deck.removeFlashcard(0);
		assertTrue(removed, "Card removed from index 0 should return true");
		
		boolean removedInvalidIndex = deck.removeFlashcard(-1);
		assertFalse(removedInvalidIndex, "Removing from a negative index should return false.");
		
		boolean removedOutOfBounds = deck.removeFlashcard(1);
	    assertFalse(removedOutOfBounds, "Removing from an out-of-bounds index should return false.");
	}

	@Test
	public void testContainsFlashcard() {
		deck = new FlashcardDeck("Test Deck");
		flashcard = new Flashcard("1", "2");
		
		deck.addFlashcard(flashcard);
		
		boolean result = deck.containsFlashcard(flashcard);
		assertTrue(result, "Deck should contain this flashcard.");
		
	    Flashcard matchingTitle = new Flashcard("1", "DifferentAnswer");
	    assertTrue(deck.containsFlashcard(matchingTitle),
	               "Deck should contain the flashcard because the title matches.");

	    Flashcard matchingAnswer = new Flashcard("DifferentTitle", "2");
	    assertTrue(deck.containsFlashcard(matchingAnswer),
	               "Deck should contain the flashcard because the answer matches.");

	    Flashcard noMatch = new Flashcard("DifferentTitle", "DifferentAnswer");
	    assertFalse(deck.containsFlashcard(noMatch),
	                "Deck should not contain the flashcard because neither title nor answer matches.");
	}

	@Test 
	public void testAddFlashcard() {
		deck = new FlashcardDeck("Test Deck");
		flashcard = new Flashcard("1", "");
		flashcard2 = new Flashcard("", "2");

		boolean result = deck.addFlashcard(flashcard);
		assertFalse(result, "Adding a flashcard with empty answer should return false.");

		boolean result2 = deck.addFlashcard(flashcard2);
		assertFalse(result2, "Adding a flashcard with empty title should return false.");
	}

	@Test
	public void testShuffle() {
		deck = new FlashcardDeck("Test Deck");

		List<Flashcard> originalNoCardsOrder = deck.getFlashcards();
		deck.shuffle();
		List<Flashcard> shuffledNoCardsOrder = deck.getFlashcards();

		assertEquals(originalNoCardsOrder, shuffledNoCardsOrder, "The order should remain the same for an empty deck.");

		flashcard = new Flashcard("1", "2");
		deck.addFlashcard(flashcard);

		List<Flashcard> originalSingleCardOrder = deck.getFlashcards();
		deck.shuffle();
		List<Flashcard> shuffledSingleCardOrder = deck.getFlashcards();

		assertEquals(originalSingleCardOrder, shuffledSingleCardOrder, "The order should remain the same for a single card.");

		flashcard2 = new Flashcard("2", "1");
		deck.addFlashcard(flashcard2);

		List<Flashcard> originalOrder = deck.getFlashcards();
		deck.shuffle();
		List<Flashcard> shuffledOrder = deck.getFlashcards();

		assertTrue(originalOrder.containsAll(shuffledOrder),
				"The shuffled deck should contain all the same flashcards as the original.");
	}

	@Test
	public void testGetFlashcard() {
		deck = new FlashcardDeck("Test Deck");

		flashcard = new Flashcard("1", "2");
		deck.addFlashcard(flashcard);

		Flashcard result = deck.getFlashcard(0);
		assertEquals(flashcard, result, "Flashcard matches.");
	}

	@Test
	public void testGetFlashcardCount() {
		deck = new FlashcardDeck("Test Deck");

		flashcard = new Flashcard("1", "2");
		deck.addFlashcard(flashcard);
		
		int result = deck.getFlashcardCount();
		assertEquals(result, 1, "Flashcard count should equal 1.");
	}
}
