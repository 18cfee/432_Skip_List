
//TODO - sorry about the overall organization, if anyone wants me to explain or work on isolating one of the methods that needs to be worked on please let me know
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MainList {

    private static ArrayList<Node> HEAD = new ArrayList<Node>();
    private static int totalNumberNodes = 0;
    public static int maxHeight = 1;

    public static void main(String args[]) {
        // initialize as a circle to itself
        Node originalHEAD = new Node(4);
        HEAD.add(0, originalHEAD);

        printList();
        insert(new Node(7), 1);
        insert(new Node(6), 4);
        insert(new Node(43110));
        printList();
        insert(new Node(-230), 1);
        insert(new Node(-23423), 2);
        insert(new Node(9), 1);
        insert(new Node(70), 3);
        insert(new Node(34), 1);
        printList();
        for (int i = 0; i < 1; i++) {
            int num = chooseNewOTGT();
            System.out.println("OGTG: " + num);
            for (Node n : chooseOrigin(num)) {
                System.out.println("nValue: " + n.value);
            }
        }
        insert(new Node(-2343), 2);
        printList();
        insert(new Node(94), 3);
        printList();
        insert(new Node(73), 2);
        printList();
        insert(new Node(3434), 1);
        insert(new Node(-233), 2);
        insert(new Node(92), 1);
        insert(new Node(702), 2);
        insert(new Node(342), 1);
        insert(new Node(341));
        insert(new Node(342));
        insert(new Node(343));
        insert(new Node(344));
        insert(new Node(345));
        insert(new Node(346));
        insert(new Node(347));
        insert(new Node(348));
        insert(new Node(349));
        insert(new Node(3410));
        insert(new Node(3411));

        printList();

    }

    // h is hardcoded in this one
    public static boolean insert(Node node, int h) {
        int insertVal = node.value;
        Node current = HEAD.get(HEAD.size() - 1);
        // bounds checking, since next is checked from here on
        if (insertVal == current.value) {
            return true;
        }
        // When starting from a HEAD on it's own list
        while (current.next == null) {
            if (current.down != null) {
                current = current.down;
            } else {
                current.next = node;
                current.prev = node;
                node.next = current;
                node.prev = current;
                totalNumberNodes++;
                return false;
            }
        }
        while (true) {
            int onVal = current.value;
            Node nextN = current.next;
            int nextVal = nextN.value;
            if (nextVal == insertVal) {
                return true;
            }
            // right interval (include circular logic)
            if ((onVal < insertVal && insertVal < nextVal) || (onVal > nextVal && (insertVal > onVal || insertVal < nextVal))) {
                if (current.down == null) {
                    current.next = node;
                    node.prev = current;
                    node.next = nextN;
                    nextN.prev = node;
                    // Insert extra levels after original insert
                    for (int i = 2; i <= h; i++) {
                        node.up = new Node(insertVal);
                        node.up.down = node;
                        if (HEAD.size() < i) {
                            HEAD.add(i - 1, node.up);
                        }
                        // New list
                        if (i > maxHeight) {
                            maxHeight = i;
                            node = node.up;
                        } else { // this moves out both directions to find nearest node on next level up to splice into
                            Node ccw = node.next;
                            Node cw = node.prev;
                            while (cw.up == null) {
                                cw = cw.prev;
                            }
                            while (ccw.up == null) {
                                ccw = ccw.next;
                            }
                            // Move up to do splicing
                            ccw = ccw.up;
                            cw = cw.up;
                            node = node.up;
                            node.prev = cw;
                            node.next = ccw;
                            ccw.prev = node;
                            cw.next = node;
                        }
                    }
                    totalNumberNodes++;
                    return false;
                } else {
                    current = current.down;
                }
            } else {
                current = nextN;
            }
        }
    }

    // Print out the List

    //Todo Erik or Robbie edit this
    public static void printList() {
        System.out.println("Start New Print:");
        // print all levels
        for (int i = 0; i < HEAD.size(); i++) {
            Node current = HEAD.get(i);
            System.out.print(current.value);
            int levelStartVal = current.value;
            while (current.next != null && current.next.value != levelStartVal) {
                current = current.next;
                System.out.print(" ::-> " + current.value);
            }
            System.out.println();
        }
    }

    // h will be chosen automatically in this one
    public static boolean insert(Node node) {
        int h = pickRandomHeight();
        int insertVal = node.value;
        Node current = HEAD.get(HEAD.size() - 1);
        // bounds checking, since next is checked from here on
        if (insertVal == current.value) {
            return true;
        }
        // When starting from a HEAD on it's own list
        while (current.next == null) {
            if (current.down != null) {
                current = current.down;
            } else {
                current.next = node;
                current.prev = node;
                node.next = current;
                node.prev = current;
                totalNumberNodes++;
                return false;
            }
        }
        while (true) {
            Node saveLowNode = node;
            int onVal = current.value;
            Node nextN = current.next;
            int nextVal = nextN.value;
            if (nextVal == insertVal) {
                return true;
            }
            // right interval (include circular logic)
            if ((onVal < insertVal && insertVal < nextVal) || (onVal > nextVal && (insertVal > onVal || insertVal < nextVal))) {
                if (current.down == null) {
                    current.next = node;
                    node.prev = current;
                    node.next = nextN;
                    nextN.prev = node;
                    // Insert extra levels after original insert
                    for (int i = 2; i <= h; i++) {
                        node.up = new Node(insertVal);
                        node.up.down = node;
                        if (HEAD.size() < i) {
                            HEAD.add(i - 1, node.up);
                        }
                        // New list
                        if (i > maxHeight) {
                            maxHeight = i;
                            node = node.up;
                        } else { // this moves out both directions to find nearest node on next level up to splice into
                            Node ccw = node.next;
                            Node cw = node.prev;
                            while (cw.up == null) {
                                cw = cw.prev;
                            }
                            while (ccw.up == null) {
                                ccw = ccw.next;
                            }
                            // Move up to do splicing
                            ccw = ccw.up;
                            cw = cw.up;
                            node = node.up;
                            node.prev = cw;
                            node.next = ccw;
                            ccw.prev = node;
                            cw.next = node;
                        }
                    }
                    updateNxtIndex(saveLowNode);
                    totalNumberNodes++;
                    return false;
                } else {
                    current = current.down;
                }
            } else {
                current = nextN;
            }
        }
    }

    public static void MasterInsert() {
        //TODO - maybe split up the insert method into parts for clarity
        // TODO - make insert based off of a set origin - we can add choosing the origin later
        int otgt = chooseNewOTGT();
        chooseOrigin(otgt);
    }

    //
    public static boolean deleteNode(int value) {
        //TODO
        return false;
    }

    public static boolean searchNode(int value) {
        //TODO Erik or Robbie
        return false;
    }

    // Based on distribution
    public static int pickRandomHeight() {
        Random r = new Random();
        int height = 1;
        while (true) {
            int flip = r.nextInt(2);
            if (flip == 0) {
                height++;
            } else {
                return height;
            }
        }
    }

    // array of nodes from highest level to lowest (ond[] array)
    public static Node[] chooseOrigin(int otgt) {
        Node[] ond = new Node[maxHeight];
        Node current = HEAD.get(0);
        for (int i = 1; i <= maxHeight; i++) {
            Node fullCircle = current.prev;
            Node largest = current;
            while (current.value >= otgt) {      //Move untill we find a value in the list smaller than otgt
                if (current.value > largest.value) {
                    largest = current;
                }
                if (current == fullCircle) {     //If we have gone full circle an no value was less than otgt.
                    current = largest;
                    break;
                }
                current = current.next;
            }
            while (current.value + current.skip < otgt) {      //Move untill current.next is bigger than otgt.
                current = current.next;
            }
            ond[i - 1] = current;
            if (current.down != null) {
                current = current.down;
            } else {
                current = HEAD.get(i - 1);
            }
        }
        return ond;
    }

    // Choose the new value based on the absolute positioning
    public static int chooseNewOTGT() {
        Random rand = new Random();
        int otgt = rand.nextInt((totalNumberNodes) + 1);    //absolute position
        System.out.println("OTGT: " + otgt);
        Node current = HEAD.get(0);
        while (current.index != otgt) {      //Move untill we find a value in the list smaller than otgt
            if (current.index < otgt) {
                current = current.next;
            } else if (current.index > otgt) {
                current = current.prev;
            }
        }
        return current.value;
    }

    //Update the the nodes skip values when a node is inserted into the List
    // the inserted node should be the lowest inserted on the list so that it is easy to go back up and add - this node tested on 11/6
    public static void updateNxtIndex(Node start) {
        //TODO - understading the insert() method probably would help for this because it will invole some of the same concepts
    }
}
