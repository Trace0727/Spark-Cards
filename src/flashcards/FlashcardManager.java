package flashcards;

import java.util.ArrayList;
import java.util.List;

public class FlashcardManager {
    private List<FlashcardDeck> decks;

    public FlashcardManager() {
        decks = new ArrayList<>();
    }

    public FlashcardDeck createDeck(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Deck name cannot be empty.");
        }

        if (decks.stream().anyMatch(deck -> deck.getName().equalsIgnoreCase(name.trim()))) {
            throw new IllegalArgumentException("A deck with this name already exists.");
        }

        FlashcardDeck newDeck = new FlashcardDeck(name.trim());
        decks.add(newDeck);
        return newDeck;
    }

    public boolean deleteDeck(String name) {
        FlashcardDeck deck = findDeckByName(name);
        if (deck != null) {
            decks.remove(deck);
            return true;
        }
        return false;
    }

    public boolean addFlashcardToDeck(String deckName, String title, String answer) {
        FlashcardDeck deck = findDeckByName(deckName);
        if (deck != null) {
            Flashcard flashcard = new Flashcard(title, answer);
            return deck.addFlashcard(flashcard);
        }
        return false;
    }

    public boolean removeFlashcardFromDeck(String deckName, int index) {
        FlashcardDeck deck = findDeckByName(deckName);
        if (deck != null) {
            return deck.removeFlashcard(index);
        }
        return false;
    }

    public List<FlashcardDeck> getAllDecks() {
        return new ArrayList<>(decks);
    }

    public FlashcardDeck findDeckByName(String name) {
        return decks.stream()
                .filter(deck -> deck.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
