
# SparkCards

A simple Java-based flashcard application built with object-oriented design principles. This project demonstrates clean code structure, class design, and integrated unit testing using JUnit 5. Ideal for educational or personal study tools.



## Project Structure

```
SparkCards/
├── src/
│   └── flashcards/
│       ├── Flashcard.java
│       ├── FlashcardDeck.java
│       ├── FlashcardManager.java
│       └── FlashcardGUI.java
├── tests/
│   └── tests/
│       ├── testFlashcard.java
│       ├── testFlashcardDeck.java
│       └── testFlashcardManager.java
└──
```

---



## Core Java Classes

### `Flashcard.java`
Represents a single flashcard containing:
- `title`: The prompt or question
- `answer`: The corresponding answer

### `FlashcardDeck.java`
Represents a collection of flashcards with:
- Validation for empty cards
- Add, remove, and shuffle operations
- Immutable access to the flashcard list

### `FlashcardManager.java`
Handles management of multiple flashcard decks:
- Create and delete decks by name
- Add and remove flashcards from specified decks
- Prevents duplicate deck names

### `FlashcardGUI.java`
Provides a graphical interface for:
- Viewing and navigating flashcards
- Marking cards correct or incorrect
- Creating new flashcards (optional extension)

---



## Unit Tests (JUnit 5)

### `testFlashcard.java`
Tests the `Flashcard` class:
- Getter methods
- `toString()` output

### `testFlashcardDeck.java`
Covers:
- Deck initialization and validation
- Adding/removing flashcards
- Card search and shuffle behavior

### `testFlashcardManager.java`
Validates manager logic:
- Deck creation and duplicate prevention
- Flashcard addition and deletion from decks
- Error handling for null or invalid inputs

---



## How to Run Tests

Ensure [JUnit 5](https://junit.org/junit5/) is on your classpath, then run the tests with your preferred IDE or:

```bash
javac -cp .:junit-platform-console-standalone-1.9.1.jar tests/tests/*.java
java -jar junit-platform-console-standalone-1.9.1.jar --class-path . --scan-class-path
```

---



## Future Enhancements
- Persistent storage (file or database)
- More interactive GUI using JavaFX or Swing

---



## License
This project is licensed for personal, non-commercial use only. Redistribution, resale, or modification is prohibited without written permission from the author.  
See the [LICENSE] file for full details.


---



## Author
**Trace Davis**  
- GitHub: [TraceTheDev](https://github.com/TraceTheDev)  
- LinkedIn: [Trace Davis](https://www.linkedin.com/in/trace-d-926380138/)
