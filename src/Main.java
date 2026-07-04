import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception { // throws Exception add kiya
        Scanner sc = new Scanner(System.in);
        String apiKey = System.getenv("GROQ_API_KEY");

        List<Message> history = new ArrayList<>();
        history.add(new Message("system", "You are a helpful and friendly assistant named Jarvis."));

        System.out.println("Welcome ⭐");
        while (true) {

            System.out.print("What do you want to talk about🤨🤨: ");
            String userMessage = sc.nextLine();

            if (userMessage.equalsIgnoreCase("exit")) {
                System.out.println("Jarvis: Goodbye!");
                break;
            }

            history.add(new Message("user", userMessage));

            StringBuilder messagesJson = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                Message msg = history.get(i);
                messagesJson.append("{\"role\":\"")
                        .append(msg.getRole())
                        .append("\",\"content\":\"")
                        .append(msg.getContent())
                        .append("\"}");
                if (i < history.size() - 1) {
                    messagesJson.append(",");
                }
            }

            String jsonBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[" + messagesJson + "]}";

            try {
                System.out.println("Jarvis is thinking.....🤔");
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String responseBody = response.body();
                int startIndex = responseBody.indexOf("\"content\":\"") + "\"content\":\"".length();
                int endIndex = responseBody.indexOf("\"},\"logprobs\"");
                String fullContent = responseBody.substring(startIndex, endIndex);

                String aiReply = fullContent.replace("\\n", "\n").replace("\\\"", "\"");
                System.out.println("------------------------");

                System.out.println("Jarvis: " + aiReply); // print kiya
                System.out.println("------------------------\n");

                history.add(new Message("assistant", aiReply)); // history mein add kiya
            } catch (Exception e) {
                System.out.println("Something went wrong" + e.getMessage());
                System.out.println("Please check your internet connection and try again");
                System.out.println("========================\n");
            }
        }
    }
}