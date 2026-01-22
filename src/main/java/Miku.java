import java.util.Scanner;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Display welcome message
        System.out.println("\t____________________________________________________________");
        System.out.println("\t「Miku-miku ni shite ageru! ♪」");
        System.out.println("\tHatsune Miku, your personal assistant, is now online! (´꒳`)♡");
        System.out.println("\tI've got your schedule synced and I'm ready to keep your day in perfect rhythm.");
        System.out.println("\tWhat’s the first task on our playlist today?");
        System.out.println("\t____________________________________________________________");

        // Echos back user input until "bye" is entered
        String input = "";
        while (!(input = sc.nextLine()).equals("bye")) {
            System.out.println("\t____________________________________________________________");
            System.out.println("\t" + input);
            System.out.println("\t____________________________________________________________");
        }

        // Display goodbye message
        System.out.println("\t____________________________________________________________");
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println("\t____________________________________________________________");
    }
}
