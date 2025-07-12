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
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import dev.langchain4j.rag.query.Query;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Chatbot extends JPanel {

    private JTextArea chatArea;
    private JTextField inputField;
    private ChatLanguageModel chatModel;
    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private ContentRetriever retriever;

    public Chatbot() {
        setLayout(new BorderLayout());

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

        // Initialize openai RAG components
        try {
            chatModel = OpenAiChatModel.builder().apiKey("REPLACE WITH API KEY DURING RUNNING").modelName("gpt-3.5-turbo").build();

            embeddingModel = OpenAiEmbeddingModel.builder().apiKey("REPLACE WITH API KEY DURING RUNNING").modelName("text-embedding-ada-002").build();

            embeddingStore = new InMemoryEmbeddingStore<>();

            // Load and process training data
            java.nio.file.Path trainingDataPath = Paths.get("src/main/resources/training_data.txt");
            Document document = FileSystemDocumentLoader.loadDocument(trainingDataPath);

            DocumentSplitter splitter = DocumentSplitters.recursive(500, 0); // Adjust chunk size and overlap as needed
            List<TextSegment> segments = splitter.split(document);

            embeddingStore.addAll(embeddingModel.embedAll(segments).content(), segments);

            retriever = EmbeddingStoreContentRetriever.builder().embeddingStore(embeddingStore).embeddingModel(embeddingModel).maxResults(2) // Retrieve up to 2 most relevant results
                    .minScore(0.7) // Minimum relevance score to consider a result
                    .build();

            chatArea.append("Chatbot initialized. Type your questions!\n");
        } catch (Exception e) {
            chatArea.append("Error initializing Chatbot: " + e.getMessage() + "\n");
            chatArea.append("Please ensure you have a valid OpenAI API key.\n");
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

        // Integrate with RAG
        if (chatModel != null && retriever != null) {
            new Thread(() -> {
                try {
                    List<dev.langchain4j.rag.content.Content> relevantSegments = retriever.retrieve(Query.from(userMessage));
                    String context = relevantSegments.stream().map(content -> content.textSegment().text()).collect(Collectors.joining("\n\n"));

                    String prompt = "Based on the following information, answer the question:\n\n" + "Context:\n" + context + "\n\n" + "Question: " + userMessage;

                    String botResponse = chatModel.generate(prompt);
                    SwingUtilities.invokeLater(() -> chatArea.append("Bot: " + botResponse + "\n"));
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> chatArea.append("Bot Error: " + e.getMessage() + "\n"));
                    e.printStackTrace();
                }
            }).start();
        } else {
            chatArea.append("Bot: (RAG components not initialized. Please contact administrator.)\n");
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