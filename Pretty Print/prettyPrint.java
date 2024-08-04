
/**
* Name : Yasoda Krishna Reddy Annapureddy
* ULID : YANNAPU
* I Pleadge of honesty that I do not copy/modify from others code
* © Yasoda Krishna Reddy Annapureddy
* A declaration of my copyright that no one else should copy/modify the codes. 
*/
import java.nio.file.*;
import java.util.*;

class prettyPrint {
    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * ULID : YANNAPU
     * Date : 04/15/2023
     * Time : 12:43 PM
     * 
     * The main method reads the input file and the line width from the command-line
     * arguments,
     * splits the input into paragraphs, and then prints each paragraph with the
     * prettyPrint method.
     * It also prints the author's name at the end.
     * 
     * @param args an array of command-line arguments, where args[0] is the path to
     *             the input file,
     *             and args[1] is the line width.
     * @throws Exception if there is an error reading the input file.
     */
    public static void main(String args[]) throws Exception {
        int output = 0;
        String para = "";
        Path inputFile = Path.of(args[0]);
        int width = Integer.parseInt(args[1]);
        System.out.println("*** Pretty Print " + args[0]);
        System.out.println("*** Width=" + args[1]);
        String input = Files.readString(inputFile);

        String[] fileInput = input.split("\\r?\\n");

        for (int i = 0; i < fileInput.length; i++) {
            if (fileInput[i].isEmpty() || i == fileInput.length - 1) {
                if (i == fileInput.length - 1) {
                    para = para + fileInput[i] + " ";
                }
                if (para.length() == 0) {
                    continue;
                }
                String[] words = para.split("\\s+");
                output = prettyPrint(words, width);
                System.out.println("===[" + output + "]");
                System.out.println();
                para = "";
                continue;
            }
            para = para + fileInput[i];
            para = para + " ";

        }

        System.out.println("*** Asg 7 by Yasoda Krishna Reddy Annapureddy");
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * ULID : YANNAPU
     * Date : 04/16/2023
     * Time : 06:19AM
     * 
     * This method finds the optimal line break points for a given array of words
     * and a specified line width,
     * using dynamic programming. It returns an array of break points, where
     * breakPoints[i] is the index of
     * the word that starts the last line of the paragraph that starts with word i.
     * 
     * @param words     an array of words in the paragraph.
     * @param lineWidth the width of each line in the paragraph.
     * @return an array of break points.
     */
    public static int[] findRaggadness(String[] words, int lineWidth) {
        int n = words.length;
        int[] raggedness = new int[n + 1];
        int[] breakPoints = new int[n + 1];
        int[] memo = new int[n + 1];

        memo[n] = 0;
        raggedness[n] = 0;
        breakPoints[n] = n;

        for (int i = n - 1; i >= 0; i--) {
            int lineLength = -1;
            memo[i] = Integer.MAX_VALUE;

            for (int j = i; j < n; j++) {

                lineLength += words[j].length() + 1;

                if (lineLength > lineWidth) {
                    break;
                }

                int temp = lineWidth - lineLength;
                int totalRaggedness = 0;

                if (j == n - 1) {
                    totalRaggedness = raggedness[j + 1];
                } else {
                    totalRaggedness = temp * temp + raggedness[j + 1];

                }

                if (totalRaggedness < memo[i]) {
                    memo[i] = totalRaggedness;
                    breakPoints[i] = j + 1;
                }
            }
            raggedness[i] = memo[i];
        }
        return breakPoints;
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * ULID : YANNAPU
     * Date : 04/15/2023
     * Time : 06:23 PM
     * 
     * This method PrettyPrint finds the optimal raggadeness of the given words and
     * line width.
     * 
     * @param words     an array of words in the paragraph.
     * @param lineWidth the width of each line in the paragraph.
     * @return the total raggedness of the paragraph.
     */
    public static int prettyPrint(String[] words, int lineWidth) {
        int n = words.length;
        int[] linebreak = findRaggadness(words, lineWidth);

        int start = 0;
        int output = 0;

        while (start < n) {
            int end = linebreak[start];
            int lineLength = -1;

            for (int i = start; i < end; i++) {
                System.out.print(words[i]);
                lineLength += words[i].length() + 1;
                if (i < end - 1) {
                    System.out.print(" ");
                }
            }

            if (end != n) {
                int temp = lineWidth - lineLength;
                output += temp * temp;
            }

            System.out.println();
            start = end;
        }

        return output;
    }

}
