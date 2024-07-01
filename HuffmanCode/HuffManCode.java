import java.util.*;

class Node implements Comparable<Node> {
    // variable declaration
    int freq;
    char Char;
    int height;
    int ascii;
    int number;

    Node leftNode;
    Node rightNode;

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * @param current_char This attribute contains the character to be saved in the
     *                     node
     * @param current_freq This attribute contains the frequency of the character to
     *                     be saved in the node
     */
    public Node(char current_char, int current_freq) {
        // Initialize the character and its frequency as provided in the input
        Char = current_char;
        freq = current_freq;
        // Initialize the height of the node as 0.
        height = 0;
        // Since a new node forms a single node subtree so its counter should also be
        // initialized to 1
        number = 1;
        // Assign ASCII value of the character to the ascii variable .
        ascii = (int) current_char;
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * @param current_char a char variable that represents the character.
     * @param current_freq an int variable that stores the frequency of the
     *                     character occurrence.
     * @param leftNode     a Node object that represents the left subtree of the
     *                     current node.
     * @param rightNode    a Node object that represents the right subtree of the
     *                     current node.
     */
    public Node(char current_char, int current_freq, Node leftNode, Node rightNode) {
        // assigning the frequency of the character to a given current character
        // frequency
        freq = current_freq;
        // assigning the character to a given current character
        Char = current_char;
        // assigning the leftNode to a given leftNode
        this.leftNode = leftNode;
        // assigning the rightNode to a given rightNode
        this.rightNode = rightNode;
        // assinging the height of the node to the maximum height between left and right
        // nodes
        if (leftNode.height > rightNode.height) {
            height = this.leftNode.height;
        } else {
            height = this.rightNode.height;
        }
        // If the node has no children, set its ASCII code to the character's ASCII
        // value
        if (leftNode == null && rightNode == null) {
            ascii = (int) Char;
        } // Otherwise, set the sum of the left and right node's ASCII codes to the
        // current node's ASCII code
        else {
            ascii = this.leftNode.ascii + this.rightNode.ascii;
        }
        // Set the number of characters in the subtree rooted at this node by adding the
        // number of characters from the left and right subtrees
        number = leftNode.number + rightNode.number;
        // ascii sum is calculated the sum of the left subtree and right subtree's ASCII
        // values as well
        // as the ASCII value of the current node's character
        ascii = leftNode.ascii + rightNode.ascii + (int) Char;

    }

    @Override
    // This method returns an integer value after comparing the frequencies of two
    // nodes(Node node2)
    public int compareTo(Node node2) {
        return this.freq - node2.freq;
    }
}

public class HuffmanCode {
    Node root;
    PriorityQueue<Node> queue;
    int size = 0;
    Node temp1;
    Node temp2;
    String[] huffmanCode;
    int compareAsciiSum, compareHeight, compareNodes;

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * @param a This is a character array that contains the alphabets
     * @param f This is integer array that contains frequecies of all the alphabets
     *          in character array.
     */
    public HuffmanCode(char[] a, int[] f) {
        this.HuffmanTree(a, f);
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * A method that builds a Huffman tree using a priority queue based on
     * minimum frequency value assigned to each character.
     *
     * @param a This is a character array that contains the alphabets
     * @param f This is integer array that contains frequecies of all the alphabets
     *          in character array.
     *
     */
    public void HuffmanTree(char[] a, int[] f) {
        // Intializing a by creating a PriorityQueue using Node
        queue = new PriorityQueue<Node>();
        // Intializing the size variable with the lenght of the
        size = a.length;
        // creates a new node for each unique character with its frequency and adds it
        // to the queue.
        for (int character = 0; character < size; character++) {
            queue.add(new Node(a[character], f[character]));
        }
        // assigning the size variable with size of the queue
        size = queue.size();
        // loops until there is only one node left in the queue.
        while (size > 1) {
            // gets and removes the first element with the highest frequency from the queue.
            temp1 = queue.poll();
            // gets and removes the second element with the highest frequency from the
            // queue.
            temp2 = queue.poll();
            // compares the two nodes by their height, number, and ASCII value. If temp1 is
            // greater than temp2, swap them.
            compareHeight = Integer.compare(temp1.height, temp2.height);
            compareNodes = Integer.compare(temp1.number, temp2.number);
            compareAsciiSum = Integer.compare(temp1.ascii, temp2.ascii);
            if (compareHeight == 1 || (compareHeight == 0 && compareNodes == 1)
                    || (compareHeight == 0 && compareNodes == 0 && compareAsciiSum == 1)) {
                queue.add(new Node('+', temp1.freq + temp2.freq, temp2, temp1));
            }
            // create a new parent node where the children are temp1 and temp2 and their
            // frequency is the sum of both frequencies. Add this new node to the queue.
            else {
                queue.add(new Node('+', temp1.freq + temp2.freq, temp1, temp2));
            }
            // updates the queue size.
            size = queue.size();
        }
        // get the root node and store it in the variable 'root'.
        root = queue.peek();
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * This method findCode takes in a Node object called root and a String object
     * called code. It recursively traverses through the Nodes and builds the binary
     * code for each character in the Huffman tree.
     *
     *
     * @param root The node to find huffman code
     * @param code Huffman code for the alphabet by traversing through the tree
     */
    public void findCode(Node root, String code) {
        // checks if the current node is a leaf node . If yes, it stores the binary
        // code, frequency, and ASCII value of that character in an array called
        // huffmanCode.
        if (root.leftNode == null && root.rightNode == null) {
            huffmanCode[(int) root.Char] = code;
            System.out.println("" + root.Char + "[" + (int) root.Char + "] :" + code + " (" + root.freq
                    + ")");
        }
        // If the current node has a left child, it recursively calls the findCode
        // method with the left child as the new root and appends "0" to the code.
        if (root.leftNode != null) {
            findCode(root.leftNode, code + "0");
        }
        // If the current node has a right child, it recursively calls the findCode
        // method with the right child as the new root and appends "1" to the code.
        if (root.rightNode != null) {
            findCode(root.rightNode, code + "1");
        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * This method is part of a program that encodes text using the Huffman coding
     * technique.
     *
     * @param inputtext this string represents the text to be encoded
     * @return the encoded string s is returned.
     */
    public String encode(String inputtext) {
        // Intializing the empty string for saving the encoded inputtext
        String s = "";
        // creating the temp variable for saving the ASCII value for each character
        int temp;
        // It then calculates the size of the input text using length() function and
        // assigns it to the variable size.
        size = inputtext.length();
        // For each character, it converts the character to its ASCII value using (int)
        // inputtext.charAt(i). It then uses this ASCII value as an index for
        // huffmanCode array and extracts a string sequence that represents the Huffman
        // encoding of that character.
        for (int i = 0; i < size; i++) {
            temp = (int) inputtext.charAt(i);
            s = s + huffmanCode[temp];
            // .substring(huffmanCode[temp].indexOf(":") + 1,
            // huffmanCode[temp].lastIndexOf(" "));
        }

        return s;
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * This method decodes the input string and returns the decoded string
     *
     * @param input The encoded binary string input
     * @return returns the decoded output value of the encoded message
     *
     */
    public String decode(String input) {
        // initializes an empty string named decodedOutput which will be used to store
        // decoded messages.
        String decodedOutput = "";
        // initializes the start node as root node where the tree traversal for decoding
        // will start.
        Node start = root;
        // splits the received encoded message into an array of strings of each
        // character, to enable processing them individually.
        String[] temp = input.split("");
        // processes each element in the encoded message array, to traverse the tree
        // nodes based on the bits present in the encoded message.
        for (int text = 0; text < temp.length; text++) {
            // determines whether the next traversal in the tree should be towards its left
            // or right side, based on the binary values of the encoded characters.
            if (temp[text].equals("0")) {
                start = start.leftNode;
            } else if (temp[text].equals("1")) {
                start = start.rightNode;
            }
            // this line compares the current node as a leaf node to determine whether it
            // has reached to the end of the tree branch at start node, in order to extract
            // the character value for it stored on this node.
            if (start.leftNode == null && start.rightNode == null) {
                // concatenates the extracted character value from the leaves to the
                // decodedOutput variable.
                decodedOutput = decodedOutput + start.Char;
                // It re-assigns the start node with root to start traversing the whole Huffman
                // tree again for the next character.
                start = root;
            }
        }
        // returns the decoded output value of the encoded message.
        return decodedOutput;

    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     *
     * prints out all the Huffman codes created while constructing a Huffman tree.
     *
     */
    public void printCodeWords() {
        System.out.println("Huffman Codes:");
        // creates an array of strings with 128 elements to store Huffman codes
        huffmanCode = new String[128];
        // initializes an empty string temp which will be used to concatenate '0' or '1'
        // as Huffman code while traversing through the tree.
        String temp = "";
        // calls findCode function on root node to get Huffman codes for all characters.
        findCode(root, temp);

    }
}
