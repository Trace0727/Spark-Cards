package flashcards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashcardDeck {
    private String name;
    private List<Flashcard> flashcards;

    public FlashcardDeck(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Deck name cannot be null or empty.");
        }
        this.name = name.trim();
        this.flashcards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    // include command for partition
    public boolean removeFlashcard(int index) {
        if (index >= 0 && index < flashcards.size()) {
            flashcards.remove(index);
            return true; 
        }
        return false; 
    }
    

    public boolean containsFlashcard(Flashcard card) {
        return flashcards.stream()
                .anyMatch(f -> f.getTitle().equals(card.getTitle()) || f.getAnswer().equals(card.getAnswer()));
    }

    public boolean addFlashcard(Flashcard card) {
        if (card.getTitle().isEmpty() || card.getAnswer().isEmpty()) {
            return false;
        }
        return flashcards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(flashcards);
    }

    public Flashcard getFlashcard(int index) {
        return flashcards.get(index);
    }

    public int getFlashcardCount() {
        return flashcards.size();
    }

    public List<Flashcard> getFlashcards() {
        return Collections.unmodifiableList(flashcards);
    }
}