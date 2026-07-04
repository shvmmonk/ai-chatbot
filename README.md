# AI Chatbot — Jarvis

A Java console chatbot powered by the Groq API. Have a real conversation with Jarvis — an AI assistant that remembers everything you've said in the session, just like a real chat.

## What makes this different from a basic AI tool

Most simple AI tools send one message and get one reply — they have no memory. Jarvis maintains a **full conversation history**, sending every previous message to the AI with each new request. This means Jarvis actually remembers:

- Your name
- What you talked about earlier
- Context from the whole conversation

### Example

```
Welcome ⭐
What do you want to talk about??: Hi my name is Shivam
Jarvis is thinking..... 🤔
------------------------
Jarvis: Nice to meet you, Shivam! I'm Jarvis, your friendly assistant. How can I help you today?
------------------------

What do you want to talk about??: What is my name?
Jarvis is thinking..... 🤔
------------------------
Jarvis: Your name is Shivam! How can I assist you further?
------------------------

What do you want to talk about??: exit
Jarvis: Goodbye!
```

## Tech Stack

- Java 17 (built-in `HttpClient` — no external libraries needed)
- [Groq API](https://groq.com/) — `llama-3.3-70b-versatile` model

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/shvmmonk/ai-chatbot.git
cd ai-chatbot
```

### 2. Get a free Groq API key
Sign up at [console.groq.com](https://console.groq.com/) and create a free API key.

### 3. Set your API key as an environment variable

**Windows:**
- Search "Edit the system environment variables" → Environment Variables → New (under User variables)
- Variable name: `GROQ_API_KEY`
- Variable value: your actual API key
- Restart VS Code/terminal after setting

**Mac/Linux:**
```bash
export GROQ_API_KEY="your_key_here"
```

### 4. Compile and run
```bash
javac src/*.java
java src.Main
```

## How it works

1. Reads API key securely from environment variable (never hardcoded)
2. Initializes conversation history with a system message defining Jarvis's personality
3. Takes user input via `Scanner`
4. Adds user message to conversation history (`List<Message>`)
5. Converts entire history to JSON format using `StringBuilder`
6. Sends HTTP POST request to Groq API with full conversation history
7. Parses JSON response to extract just the AI's reply
8. Prints Jarvis's reply with clean formatting
9. Adds AI reply to history (so next message has full context)
10. Loops back — handles errors gracefully without crashing

## Project Structure

```
src/
├── Main.java       # Main loop, API calls, JSON building and parsing
└── Message.java    # Represents a single chat message (role + content)
```

## Key Concepts Used

- **Conversation History** — maintaining a `List<Message>` and sending full context with every API call
- **Java HttpClient** — HTTP POST requests without external libraries
- **StringBuilder** — efficiently building JSON strings from a list of objects
- **JSON parsing** — extracting AI response using `indexOf()` and `substring()`
- **Environment variables** — secure API key storage
- **Error handling** — `try-catch` for graceful failure recovery

## Known Limitations

- Conversation history is stored in memory — restarting the program clears all history
- Manual JSON building (no external library) — could break with unusual characters in messages
- No file persistence for saving/loading past conversations

## Future Improvements

- Save conversation history to a file so chats persist across sessions
- Add multiple "modes" — study assistant, code reviewer, roast machine 😄
- Build a Spring Boot web version with a proper chat UI
- Add support for multiple named personas/personalities