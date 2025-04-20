package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import flashcards.Flashcard;

public class testFlashcard {
	
	@Test
    public void testGetTitle() {
        Flashcard flashcard = new Flashcard("Title", "Answer");
        
        assertEquals(flashcard.getTitle(), "Title");
    }
	
	@Test
	public void testGetAnswer() {
		Flashcard flashcard = new Flashcard("Title", "Answer");
		
		assertEquals(flashcard.getAnswer(), "Answer");
	}
	
	@Test
	public void testToString() {
		Flashcard flashcard = new Flashcard("Title", "Answer");
		
		assertEquals(flashcard.toString(), "Title: " + flashcard.getTitle() + ", Answer: " + flashcard.getAnswer());
	}
}
