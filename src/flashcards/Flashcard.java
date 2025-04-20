package flashcards;

public class Flashcard {
    private String title;
    private String answer;

    public Flashcard(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }
    
    @Override
    public String toString() {
        return "Title: " + title + ", Answer: " + answer;
    }
}
