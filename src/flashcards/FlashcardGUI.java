package flashcards;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlashcardGUI {

    private FlashcardManager manager = new FlashcardManager();
    private FlashcardDeck currentDeck = null;
    private int currentIndex = -1;
    private boolean isShowingTitle = true;

    private JComboBox<String> createPanelDropdown = new JComboBox<>();
    private JComboBox<String> viewPanelDropdown = new JComboBox<>();

    private JButton flipButton, removeCardButton, shuffleButton, incorrectButton, moveCardButton;
    private JLabel flashcardDisplay, flashcardCountLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flashcard App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        FlashcardGUI gui = new FlashcardGUI();

        tabbedPane.addTab("Create Deck", gui.createDeckPanel(frame));
        tabbedPane.addTab("Create Card", gui.createCardPanel(frame));
        tabbedPane.addTab("View Deck", gui.viewDeckPanel(frame));

        tabbedPane.addChangeListener(e -> gui.updateDeckDropdown());

        frame.add(tabbedPane);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createDeckPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel deckNameLabel = new JLabel("Enter Deck Name:");
        JTextField deckNameField = new JTextField(20);
        JButton createDeckButton = new JButton("Create Deck");
        JButton deleteDeckButton = new JButton("Delete Deck");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(deckNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(deckNameField, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(createDeckButton, gbc);

        gbc.gridy = 2;
        panel.add(deleteDeckButton, gbc);

        createDeckButton.addActionListener(e -> createDeck(frame, deckNameField));
        deleteDeckButton.addActionListener(e -> deleteDeck(frame, deckNameField));

        return panel;
    }

    private JPanel createCardPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel deckSelectLabel = new JLabel("Select Deck:");
        JLabel cardCountLabel = new JLabel("Cards: 0");
        JTextField titleText = new JTextField(20);
        JTextField answerText = new JTextField(20);
        JButton createButton = new JButton("Create Flashcard");
        createButton.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(deckSelectLabel, gbc);

        gbc.gridx = 1;
        panel.add(createPanelDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        panel.add(titleText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Answer:"), gbc);

        gbc.gridx = 1;
        panel.add(answerText, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        gbc.gridy = 4;
        panel.add(cardCountLabel, gbc);

        setupCreateCardListeners(createPanelDropdown, createButton, frame, titleText, answerText, cardCountLabel);

        return panel;
    }

    private JPanel viewDeckPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel deckSelectLabel = new JLabel("Select Deck:");
        viewPanelDropdown = new JComboBox<>();

        JPanel flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setBorder(BorderFactory.createTitledBorder("Flashcard"));
        flashcardPanel.setPreferredSize(new Dimension(300, 150));

        flashcardDisplay = new JLabel("No flashcards available.", SwingConstants.CENTER);
        flashcardDisplay.setOpaque(true);
        flashcardDisplay.setBackground(Color.WHITE);

        JLabel prevArrow = new JLabel("<", SwingConstants.CENTER);
        JLabel nextArrow = new JLabel(">", SwingConstants.CENTER);

        prevArrow.setFont(new Font("Arial", Font.BOLD, 18));
        nextArrow.setFont(new Font("Arial", Font.BOLD, 18));
        prevArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        flashcardPanel.add(prevArrow, BorderLayout.WEST);
        flashcardPanel.add(flashcardDisplay, BorderLayout.CENTER);
        flashcardPanel.add(nextArrow, BorderLayout.EAST);

        flipButton = new JButton("Flip");
        incorrectButton = new JButton("Mark as Incorrect");
        removeCardButton = new JButton("Remove Card");
        moveCardButton = new JButton("Move Card");
        shuffleButton = new JButton("Shuffle");
        flashcardCountLabel = new JLabel("0 / 0", SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(deckSelectLabel, gbc);

        gbc.gridx = 1;
        panel.add(viewPanelDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(flashcardPanel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;

        gbc.gridx = 0;
        panel.add(removeCardButton, gbc);

        gbc.gridx = 1;
        panel.add(flipButton, gbc);

        gbc.gridx = 2;
        panel.add(moveCardButton, gbc);

        gbc.gridy = 3;

        gbc.gridx = 0;
        panel.add(shuffleButton, gbc);

        gbc.gridx = 1;
        panel.add(flashcardCountLabel, gbc);

        gbc.gridx = 2;
        panel.add(incorrectButton, gbc);

        setupArrowListeners(prevArrow, nextArrow);
        setupViewDeckListeners();

        return panel;
    }

    private void setupArrowListeners(JLabel prevArrow, JLabel nextArrow) {
        prevArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navigateFlashcard(-1);
            }
        });

        nextArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navigateFlashcard(1);
            }
        });
    }

    private void setupViewDeckListeners() {
        viewPanelDropdown.addActionListener(e -> {
            String selectedDeck = (String) viewPanelDropdown.getSelectedItem();
            if (selectedDeck != null && !selectedDeck.equals("Select Deck")) {
                currentDeck = manager.findDeckByName(selectedDeck);
                currentIndex = 0;
                updateFlashcardDisplay();
            }
        });

        flipButton.addActionListener(e -> {
            isShowingTitle = !isShowingTitle;
            updateFlashcardDisplay();
        });

        incorrectButton.addActionListener(e -> {
            if (currentDeck == null || currentDeck.getFlashcardCount() == 0) {
                JOptionPane.showMessageDialog(null, "No card available to mark as incorrect.");
                return;
            }

            Flashcard currentCard = currentDeck.getFlashcard(currentIndex);
            FlashcardDeck incorrectDeck = manager.findDeckByName("Incorrect Deck");

            if (incorrectDeck == null) {
                incorrectDeck = manager.createDeck("Incorrect Deck");
                JOptionPane.showMessageDialog(null, "'Incorrect Deck' created");
            }

            if (incorrectDeck.addFlashcard(new Flashcard(currentCard.getTitle(), currentCard.getAnswer()))) {
                JOptionPane.showMessageDialog(null, "Card was added to 'Incorrect Deck'");
            } else {
                JOptionPane.showMessageDialog(null, "Card already exists in 'Incorrect Deck'");
            }

            updateDeckDropdown();
        });

        removeCardButton.addActionListener(e -> {
            if (currentDeck != null && currentDeck.getFlashcardCount() > 0) {
                currentDeck.removeFlashcard(currentIndex);
                currentIndex = Math.min(currentIndex, currentDeck.getFlashcardCount() - 1);
                updateFlashcardDisplay();
                JOptionPane.showMessageDialog(null, "Card removed from deck.");
            }
        });

        moveCardButton.addActionListener(e -> {
            if (currentDeck == null || currentDeck.getFlashcardCount() == 0) {
                JOptionPane.showMessageDialog(null, "No card available to move.");
                return;
            }

            JDialog dialog = new JDialog((Frame) null, "Move Card", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel promptLabel = new JLabel("Move card to deck:");
            JComboBox<String> deckDropdown = new JComboBox<>();

            for (FlashcardDeck deck : manager.getAllDecks()) {
                if (!deck.getName().equals(currentDeck.getName())) {
                    deckDropdown.addItem(deck.getName());
                }
            }

            if (deckDropdown.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No other decks available to move the card.");
                return;
            }

            JButton moveButton = new JButton("Move");
            JButton cancelButton = new JButton("Cancel");

            gbc.gridx = 0;
            gbc.gridy = 0;
            dialog.add(promptLabel, gbc);

            gbc.gridx = 1;
            dialog.add(deckDropdown, gbc);

            gbc.gridy = 1;
            gbc.gridx = 0;
            dialog.add(moveButton, gbc);

            gbc.gridx = 1;
            dialog.add(cancelButton, gbc);

            dialog.pack();
            dialog.setLocationRelativeTo(null);

            moveButton.addActionListener(actionEvent -> {
                String selectedDeckName = (String) deckDropdown.getSelectedItem();
                if (selectedDeckName == null || selectedDeckName.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please select a valid deck.");
                    return;
                }

                FlashcardDeck targetDeck = manager.findDeckByName(selectedDeckName);
                if (targetDeck == null) {
                    JOptionPane.showMessageDialog(dialog, "Selected deck does not exist.");
                    return;
                }

                Flashcard currentCard = currentDeck.getFlashcard(currentIndex);
                if (targetDeck.addFlashcard(new Flashcard(currentCard.getTitle(), currentCard.getAnswer()))) {
                    currentDeck.removeFlashcard(currentIndex);
                    currentIndex = Math.min(currentIndex, currentDeck.getFlashcardCount() - 1);
                    JOptionPane.showMessageDialog(dialog, "Card moved to '" + selectedDeckName + "'.");
                    updateFlashcardDisplay();
                    updateDeckDropdown();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Card already exists in the target deck.");
                }

                dialog.dispose();
            });

            cancelButton.addActionListener(actionEvent -> dialog.dispose());

            dialog.setVisible(true);
        });

        shuffleButton.addActionListener(e -> {
            if (currentDeck != null && currentDeck.getFlashcardCount() > 1) {
                currentDeck.shuffle();
                currentIndex = 0;
                updateFlashcardDisplay();
            }
        });
    }

    private void setupCreateCardListeners(JComboBox<String> dropdown, JButton createButton, JFrame frame,
                                          JTextField titleText, JTextField answerText, JLabel cardCountLabel) {
        dropdown.addActionListener(e -> {
            String selectedDeck = (String) dropdown.getSelectedItem();
            boolean isValidDeck = selectedDeck != null && !selectedDeck.equals("No decks available");

            createButton.setEnabled(isValidDeck);
            FlashcardDeck deck = findDeckByName(selectedDeck);
            cardCountLabel.setText(deck != null ? "Cards: " + deck.getFlashcardCount() : "Cards: 0");
        });

        createButton.addActionListener(e -> {
            String selectedDeck = (String) dropdown.getSelectedItem();
            if (selectedDeck == null || selectedDeck.equals("No decks available")) return;

            String title = titleText.getText().trim();
            String answer = answerText.getText().trim();

            if (title.isEmpty() || answer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both title and answer.");
                return;
            }

            boolean success = manager.addFlashcardToDeck(selectedDeck, title, answer);
            if (!success) {
                JOptionPane.showMessageDialog(frame, "This card already exists in the deck or you have not selected a deck to add to.");
                return;
            }

            titleText.setText("");
            answerText.setText("");
            FlashcardDeck deck = manager.findDeckByName(selectedDeck);
            cardCountLabel.setText("Cards: " + deck.getFlashcardCount());
        });
    }

    private FlashcardDeck findDeckByName(String name) {
        return manager.getAllDecks().stream()
                .filter(deck -> deck.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void updateDeckDropdown() {
        List<String> deckNames = manager.getAllDecks().stream()
                .map(FlashcardDeck::getName)
                .toList();

        for (JComboBox<String> dropdown : List.of(createPanelDropdown, viewPanelDropdown)) {
            dropdown.removeAllItems();
            dropdown.addItem("Select Deck");
            deckNames.forEach(dropdown::addItem);
        }
    }

    private void updateFlashcardDisplay() {
        if (currentDeck != null && currentDeck.getFlashcardCount() > 0) {
            Flashcard card = currentDeck.getFlashcard(currentIndex);
            flashcardDisplay.setText(isShowingTitle ? card.getTitle() : card.getAnswer());
            flashcardCountLabel.setText((currentIndex + 1) + " / " + currentDeck.getFlashcardCount());
        } else {
            flashcardDisplay.setText("No flashcards available.");
            flashcardCountLabel.setText("0 / 0");
        }
    }

    private void navigateFlashcard(int direction) {
        if (currentDeck != null && currentDeck.getFlashcardCount() > 0) {
            currentIndex += direction;
            currentIndex = Math.max(0, Math.min(currentIndex, currentDeck.getFlashcardCount() - 1));
            updateFlashcardDisplay();
        }
    }

    private void createDeck(JFrame frame, JTextField deckNameField) {
        String deckName = deckNameField.getText().trim();

        if (deckName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a deck name.");
            return;
        }

        if (manager.findDeckByName(deckName) != null) {
            JOptionPane.showMessageDialog(frame, "This deck name already exists.");
            return;
        }

        manager.createDeck(deckName);
        deckNameField.setText("");
        updateDeckDropdown();
        JOptionPane.showMessageDialog(frame, "Deck '" + deckName + "' created!");
    }

    private void deleteDeck(JFrame frame, JTextField deckNameField) {
        String deckName = deckNameField.getText().trim();

        if (deckName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a deck name.");
            return;
        }

        boolean success = manager.deleteDeck(deckName);
        if (!success) {
            JOptionPane.showMessageDialog(frame, "No deck found with the name '" + deckName + "'.");
            return;
        }

        updateDeckDropdown();
        deckNameField.setText("");
        JOptionPane.showMessageDialog(frame, "Deck '" + deckName + "' deleted!");
    }
}