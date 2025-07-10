import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.retriever.ContentRetriever;
import dev.langchain4j.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.InMemoryEmbeddingStore;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Chatbot extends JPanel {

    private JTextArea chatArea;
    private JTextField inputField;
    private ChatLanguageModel ollamaModel;
    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private ContentRetriever retriever;

    public Chatbot() {
        

        // Set up the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Set up the input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Action listener for the send button and input field
        ActionListener sendActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        };
        sendButton.addActionListener(sendActionListener);
        inputField.addActionListener(sendActionListener);

        // Initialize Ollama and RAG components
        try {
            ollamaModel = OllamaChatModel.builder()
                    .baseUrl("http://localhost:11434/") // Adjust if your Ollama instance is elsewhere
                    .modelName("llama2") // Or any other model you have pulled, e.g., "mistral"
                    .build();

            embeddingModel = OllamaEmbeddingModel.builder()
                    .baseUrl("http://localhost:11434/")
                    .modelName("nomic-embed-text") // Ensure you have this model pulled: ollama pull nomic-embed-text
                    .build();

            embeddingStore = new InMemoryEmbeddingStore<>();

            // Load and process training data
            java.nio.file.Path trainingDataPath = Paths.get("src/main/resources/training_data.txt");
            Document document = FileSystemDocumentLoader.loadDocument(trainingDataPath);

            DocumentSplitter splitter = DocumentSplitters.recursive(500, 0); // Adjust chunk size and overlap as needed
            List<TextSegment> segments = splitter.split(document);

            embeddingStore.add(embeddingModel.embedAll(segments).embeddings());

            retriever = new EmbeddingStoreContentRetriever(embeddingStore, embeddingModel);

            chatArea.append("Chatbot initialized. Type your questions!\n");
        } catch (Exception e) {
            chatArea.append("Error initializing Chatbot: " + e.getMessage() + "\n");
            chatArea.append("Please ensure Ollama is running, and 'llama2' and 'nomic-embed-text' models are available.\n");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String userMessage = inputField.getText();
        if (userMessage.trim().isEmpty()) {
            return;
        }

        chatArea.append("You: " + userMessage + "\n");
        inputField.setText("");

        // Integrate with Ollama and RAG
        if (ollamaModel != null && retriever != null) {
            new Thread(() -> {
                try {
                    // 1. Retrieve relevant information
                    List<TextSegment> relevantSegments = retriever.retrieve(userMessage);
                    String context = relevantSegments.stream()
                            .map(TextSegment::text)
                            .collect(Collectors.joining("\n\n"));

                    // 2. Construct a prompt with context
                    String prompt = "Based on the following information, answer the question:\n\n" +
                                    "Context:\n" + context + "\n\n" +
                                    "Question: " + userMessage;

                    // 3. Generate response using Ollama with context
                    String botResponse = ollamaModel.generate(prompt);
                    SwingUtilities.invokeLater(() -> chatArea.append("Bot: " + botResponse + "\n"));
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> chatArea.append("Bot Error: " + e.getMessage() + "\n"));
                    e.printStackTrace();
                }
            }).start();
        } else {
            chatArea.append("Bot: (Ollama or RAG components not initialized. Please check dependencies and configuration.)\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gym Membership Chatbot");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.add(new Chatbot());
            frame.setVisible(true);
        });
    }
}