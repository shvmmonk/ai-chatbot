import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String apiKey = System.getenv("GROQ_API_KEY");
        
        
        while(true){
            System.out.print("Hey Whatsapp !!: ");
            String message = sc.nextLine();



            if(message.equalsIgnoreCase("exit")){
                System.out.println("Exit");
                break;
            }
        }
    }

}


