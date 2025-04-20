package tests;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import flashcards.Flashcard;
import flashcards.FlashcardDeck;
import flashcards.FlashcardManager;

public class testFlashcardManager {
	private FlashcardDeck deck;
	private Flashcard flashcard;
	private FlashcardManager manager;

	@Test
	public void testCreateDeck() {
		manager = new FlashcardManager();
		deck = manager.createDeck("Test Deck");

		assertThrows(IllegalArgumentException.class, () -> manager.createDeck(null));
		assertThrows(IllegalArgumentException.class, () -> manager.createDeck(""));
		assertThrows(IllegalArgumentException.class, () -> manager.createDeck("Test Deck"));
	}

	@Test
	public void testDeleteDeck() {
		manager = new FlashcardManager();
		manager.createDeck("Test Deck");
		assertEquals(1, manager.getAllDecks().size(), "Manager should contain one deck before deletion.");

		boolean deleted = manager.deleteDeck("Test Deck");
		assertTrue(deleted, "Deck should be deleted successfully.");
		assertEquals(0, manager.getAllDecks().size(), "Manager should contain no decks after deletion.");

		boolean result = manager.deleteDeck(null);
		assertFalse(result, "Deleting a null deck name should return false.");
	}

	@Test
	public void testAddFlashcardToDeck() {
		manager = new FlashcardManager();
		deck = manager.createDeck("Test Deck");
		flashcard = new Flashcard("1", "2");
		manager.addFlashcardToDeck("Test Deck", "1", "2");

		assertTrue(deck.containsFlashcard(flashcard));

		boolean result = manager.addFlashcardToDeck(null, "1", "2");
		assertFalse(result, "Cannot add a card to a null deck.");
	}

	@Test
	public void testRemoveFlashcardFromDeck() {
		manager = new FlashcardManager();
		deck = manager.createDeck("Test Deck");
		flashcard = new Flashcard("1", "2");
		manager.addFlashcardToDeck("Test Deck", "1", "2");

		boolean removed = manager.removeFlashcardFromDeck("Test Deck", 0);
		assertTrue(removed, "Flashcard should be removed successfully.");

		boolean result = manager.removeFlashcardFromDeck(null, 0);
		assertFalse(result, "Flashcard cannot be removed from a null Deck.");
	}
}
