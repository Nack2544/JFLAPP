import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Project1 {
    private static final int DIST_SCALE_HOR = 200;
    private static final int DIST_SCALE_VERT = 60;

    static class State{
        int id;
        boolean isInitial;
        boolean isFinal;
        State right;
        State left;

        State(){

        }
        State(int id){
            this.id = id;
            this.isInitial = false;
            this.isFinal = false;
            this.right = null;
            this.left = null;
        }
    }

    public static void main(String[] args) {
        String inputFileName;
        String outputFileName;

        Scanner scanner = null;
        if (args.length < 2) {
            scanner = new Scanner(System.in);
            // Check if input file name is provided
            if (args.length >= 1) {
                inputFileName = args[0];
            } else {
                // They must not have provided an input file so ask for it.
                System.out.print("Enter the input file name: ");
                inputFileName = scanner.nextLine();
            }

            // They must not have provided an output file so ask for it.
            System.out.print("Enter the output file name: ");
            outputFileName = scanner.nextLine();
            scanner.close();
        } else {
            inputFileName = args[0];
            outputFileName = args[1];
        }

        // You can consider restructuing this code to us a cleaner AutoCloseable syntax
        // See try-with-resources
        Scanner input = null;
        PrintStream output = null;
        try {
            input = new Scanner(new FileReader(inputFileName));
        } catch (FileNotFoundException e) {
            System.err.printf("Unable to open input file: `%s`\n", inputFileName);
            System.exit(1);
        }
        try {
            output = new PrintStream(outputFileName);
        } catch (FileNotFoundException e) {
            System.err.printf("Unable to open output file: `%s`\n", outputFileName);
            System.exit(1);
        }

        printJFLAPHead(output);

        // I am going to eventually make an initial state with an id of 0
        // I am not making it yet, so I can put it in the middle
        int currentId = 1;
        int currentHeight = 0;
        int lineNum = 1;

        State state = new State(0);
        ArrayList<State> arrayState = new ArrayList<>();
        Set<String> setString = new HashSet<>();

        // Read lines from standard input until EOF
        while (input.hasNextLine()) {
            String line = input.nextLine().toLowerCase();
            int lenLine = line.length();

           setString.add(line);

           for(String word: setString){

           }


            // Make a lambda transition from the initial state (id 0) to the start of our
            // string
            printJFLAPTransition(output, 0, currentId, null);
            currentId++;

            // Handle each character in the string
            for (int i = 0; i < lenLine; i++) {
                // Check if the current character is the end of the string
                boolean isFinal = (i + 1 >= lenLine) || (line.charAt(i + 1) == '\r') || (line.charAt(i + 1) == '\n');

                // Make the state for the current character
                printJFLAPState(output, currentId, i * DIST_SCALE_HOR, currentHeight, false, isFinal);
                char symbol = line.charAt(i);

                if(symbol < 'a' || symbol > 'z'){
                    System.out.println(String.format("Error on line %d, symbol  '%c' is invalid. All characters should be from a-z", lineNum, symbol));
                    System.exit(1); // Exit with code 1

                }


                // Transition from the previous state to the current state
                printJFLAPTransition(output, currentId - 1, currentId, symbol);

                currentId++;
                if (isFinal) {
                    break;
                }

            }
            lineNum++;
            currentHeight += DIST_SCALE_VERT;

        }

        // Print our initial state in the middle of the screen vertically
        printJFLAPState(output, 0, -2 * DIST_SCALE_HOR, (currentHeight - DIST_SCALE_VERT) / 2, true, false);
        printJFLAPTail(output);

        input.close();
        output.close();
    }

    /////////////////////////////////////////////////////////////
    // Below are the methods to print to a file.
    // There is no provided structure to these functions,
    // they are just there for reference.
    //
    // You can use them directly, but I'd recommend modifying them
    // to suit your needs.
    //
    // You can also adjust the fomatting (whitespace) of the output if you desire.
    //
    // This could also include using a class that handles the head and tail for you.
    /////////////////////////////////////////////////////////////

    /**
     * This should always be printed first!
     *
     * @param output The output stream to print to. This is where the JFLAP file
     *               will be written.
     * @throws NullPointerException if output is null
     */
    public static void printJFLAPHead(PrintStream output) {
        output.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><structure><type>fa</type><automaton>");
    }

    /**
     * This should always be printed last!
     *
     * @param output The output stream to print to. This is where the JFLAP file
     *               will be written.
     * @throws NullPointerException if output is null
     */
    public static void printJFLAPTail(PrintStream output) {
        output.println("</automaton></structure>");
    }

    /**
     * This prints a state.
     * It is safe to use this whenever you have printed the head of the file, but
     * not the tail
     *
     * @param output    The output stream to print to. This is where the JFLAP file
     *                  will be written.
     * @param stateId   A unique integer id to represent the state internally for
     *                  JFLAP.
     *                  This is not displayed to the user.
     *                  I'd recommend keeping a counter somewhere and just
     *                  incrementing it each time you make a new state.
     *                  Having a class handle the printing for you could make the
     *                  automatic.
     * @param x         The x coordinate of the state. Feel free to change this to a
     *                  double if you want decimal places, I just didn't want it.
     *                  You can also leave this as just 0 if you aren't looking for
     *                  the extra credit.
     * @param y         The y coordinate of the state. Feel free to change this to a
     *                  double if you want decimal places, I just didn't want it.
     *                  You can also leave this as just 0 if you aren't looking for
     *                  the extra credit.
     * @param isInitial Whether or not this is an intial state or not.
     *                  Reminder: There should only be one initial state!
     * @param isFinal   Whether or not this is a final state or not.
     *                  Reminder: You can have as many final states as you need.
     * @throws NullPointerException if output is null
     *
     */
    public static void printJFLAPState(PrintStream output, int stateId, int x, int y, boolean isInitial,
                                       boolean isFinal) {
        output.printf("<state id=\"%d\" name=\"q%d\">\n", stateId, stateId);
        output.printf("  <x>%d</x>\n", x);
        output.printf("  <y>%d</y>\n", y);
        if (isInitial) {
            output.println("  <initial/>");
        }
        if (isFinal) {
            output.println("  <final/>");
        }
        output.println("</state>");
    }

    // Use null to represent a lambda transition
    // You SHOULD NOT have a lambda transition in a DFA
    /**
     * This prints a state.
     * It is safe to use this whenever you have printed the head of the file, but
     * not the tail.
     * JFLAP is pretty flexible; you can even print a transition using states that
     * don't yet exist, as long as you print them later.
     *
     * @param output The output stream to print to. This is where the JFLAP file
     *               will be written.
     * @param fromId The unique internal state id the transition is coming from.
     *               See {@link Project1#printJFLAPState} for info about state id.
     * @param toId   The unique internal state id the transition is going to.
     *               See {@link Project1#printJFLAPState} for info about state id.
     * @param symbol The symbol that the transition is labeled with. This determines
     *               how you get from the state with fromId to the state with toId.
     *               Since this example code is generating NFA, and not DFA, I have
     *               it set up so I can pass a null for the symbol to get a "free
     *               transition" or "lambda transition".
     *               There should be NO lambda transitions in your code, so you
     *               could consider removing that behavior, or simply never using
     *               it.
     * @throws NullPointerException if output is null
     */
    public static void printJFLAPTransition(PrintStream output, int fromId, int toId, Character symbol) {
        output.println("<transition>");
        output.printf("  <from>%d</from>\n", fromId);
        output.printf("  <to>%d</to>\n", toId);
        if (symbol == null) {
            output.println("  <read/>");
        } else {
            output.printf("  <read>%c</read>\n", symbol);
        }
        output.println("</transition>");
    }
}
