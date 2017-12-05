
//TODO - sorry about the overall organization, if anyone wants me to explain or work on isolating one of the methods that needs to be worked on please let me know
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class MainList {

    //private static ArrayList<Node> HEAD = new ArrayList<Node>();
    private static Node HEAD;
    private static int totalNumberNodes = 1;
    public static int maxHeight = 1;
    public static void main(String args[]) {
        // initialize as a circle to itself
        Node originalHEAD = new Node(100);
        originalHEAD.index = 1;
        HEAD = originalHEAD;
        insert(new Node(341));
        insert(new Node(342));
        insert(new Node(343));
        insert(new Node(344));
        insert(new Node(345));
        insert(new Node(346));
        insert(new Node(347));
        insert(new Node(348));
        insert(new Node(349));
        insert(new Node(369));
        insert(new Node(380));
        //printListWithIndex();
        printList();
        Scanner in = new Scanner(System.in);
        int input = 0;
        int otgtVal = 0;
        Node[] ond = null;
        do{
            otgtVal = chooseNewOTGT();
            ond = chooseOrigin(otgtVal);
            System.out.println("Enter the number you want to search for or -1 to exit: ");
            input = in.nextInt();
            if(input != -1){
                System.out.println("Found: "+ searchNode(input,ond));
            }
            printList();
        } while(input != -1);

    }

    // h will be chosen automatically in this one
    public static boolean insert(Node node) {
        int oldHeight = maxHeight;
        int h = pickRandomHeight();
        int insertVal = node.value;
        Node current = HEAD;
        // bounds checking, since next is checked from here on
        if (insertVal == current.value) {
            return true;
        }
        // When starting from a HEAD on it's own list
        while (current.next == null) {
            if (current.down != null) {
                current = current.down;
            } else {
                // First node inserted??
                current.next = node;
                current.prev = node;
                node.next = current;
                node.prev = current;
                node.index = 1;
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
                // already found the value we are trying to insert
                return true;
            }
            // right interval (include circular logic)
            if ((onVal < insertVal && insertVal < nextVal) || (onVal > nextVal && (insertVal > onVal || insertVal < nextVal))) {
                if (current.down == null) {
                    current.next = node;
                    node.prev = current;
                    node.next = nextN;
                    node.index = 1; // Lowest level skips one
                    nextN.prev = node;
                    // Insert extra levels after original insert
                    // These vars are used to update index value
                    int nodesBefore = 1;
                    int nodesAfter = 1;
                    for (int i = 2; i <= h; i++) {
                        node.up = new Node(insertVal);
                        node.up.down = node;
                        /*if (HEAD.size() < i) {
                            HEAD.add(i - 1, node.up);
                        }*/
                        // New list
                        if (i > maxHeight) {
                            maxHeight = i;
                            node = node.up;
                            node.index = totalNumberNodes;
                            HEAD = node;
                        } else { // this moves out both directions to find nearest node on next level up to splice into
                            Node ccw = node.next;
                            Node cw = node.prev;
                            while (cw.up == null) {
                                cw = cw.prev;
                                //nodesBefore++;
                            }
                            while (ccw.up == null) {
                                ccw = ccw.next;
                                //nodesAfter++;
                            }
                            // Move up to do splicing
                            ccw = ccw.up;
                            cw = cw.up;
                            node = node.up;
                            node.prev = cw;
                            node.next = ccw;
                            ccw.prev = node;
                            cw.next = node;
                            //cw.index = nodesBefore;
                            //node.index = nodesAfter;
                        }
                    }
                    updateNxtIndex(saveLowNode, oldHeight);
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

    /*public static void MasterInsert() {
        //TODO - maybe split up the insert method into parts for clarity
        // TODO - make insert based off of a set origin - we can add choosing the origin later
        int otgt = chooseNewOTGT();
        chooseOrigin(otgt);
    }*/

    //
    public static boolean deleteNode(int value) {
        //TODO
        return false;
    }

    public static boolean searchNode(int insertVal, Node[] ond) {
        //TODO Erik
        int indexOnd = 0;
        Node cur = ond[indexOnd];
        cur.visited = true;
        // get through the lists with only one node
        while(cur.next == null){
            if(cur.value == insertVal){
                // found
                return true;
            } else{
                //continue
                indexOnd++;
                cur = ond[indexOnd];
                cur.visited = true;
            }
        }
        if(cur.value == insertVal){
            cur.visited = true;
            return true;
        }
        while(true) {

            int onVal = cur.value;
            int nextVal = cur.next.value;
            cur.visited = true;
            if(nextVal == insertVal) {
                cur.next.visited = true;
                return true;
            }
            //if the value is in the right slot
            if (rightIntervol(onVal, insertVal, nextVal)) {
                if(cur.down != null){
                    // Only evealuate OND if ond is in the current intervol
                    if(rightIntervol(onVal, ond[indexOnd].value, nextVal)) {
                        //Choose between next ond and going down
                        //between current and next ond
                        if (rightIntervol(onVal, insertVal, ond[indexOnd + 1].value)) {
                            cur = cur.down;
                        } else {
                            cur = ond[indexOnd + 1];
                        }
                    }
                    else{
                        cur = cur.down;
                    }
                    indexOnd++;
                } else{
                    return false;
                }
            } else {
                cur = cur.next;
            }
        }
    }

    // Right slot
    public static boolean rightIntervol(int onVal, int insertVal, int nextVal){
        return ((onVal <= insertVal && insertVal <= nextVal) ||
                (onVal >= nextVal && (insertVal >= onVal || insertVal <= nextVal)));
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
        // todo fix Node current = HEAD.get(0);
        Node current = HEAD;
        for (int i = 1; i <= maxHeight; i++) {
            if(current.next != null) {
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
                while (current.next.value < otgt) {      //Move untill current.next is bigger than otgt.
                    current = current.next;
                }
            }
            ond[i - 1] = current;
            if (current.down != null) {
                current = current.down;
            } else {
                //TODO fix current = HEAD.get(i - 1);
            }
        }
        print(ond);
        return ond;
    }

    public static void print(Node[] ond){
        System.out.print("Ond: ");
        for (int i = 0; i < ond.length; i++) {
            ond[i].ond = true;
            System.out.print(ond[i].value + ", ");
        }
        System.out.println();
    }

    // Choose the new value based on the absolute positioning
    public static int chooseNewOTGT() {
        Random rand = new Random();
        int otgt = rand.nextInt((totalNumberNodes));    //absolute position
        System.out.println("OTGT: " + otgt);
        Node current = HEAD;
        //This far depects how many absolute pos have been traversed
        int indexFromHead = 0;
        while (indexFromHead != otgt) {      //Move untill we find otgt
            if (indexFromHead + current.index <= otgt) { // Does not overshoot
                indexFromHead+=current.index; // add number of nodes skipped by moving to next
                current = current.next;
            } else {
                current = current.down;
            }
        }
        System.out.println("OTGT value: " + current.value);
        return current.value;
    }

    // Print out the List
    public static void printList(){
        System.out.println("Start New Print:");
        Node current = HEAD;
        Node levelStart = HEAD;
        int toBeSkipped;
        // print all levels
        while(true){
            toBeSkipped =   current.index;
            if(current.ond){
                System.out.print("O");
            }else{
                System.out.print(" ");
            }
            System.out.print(current.value);
            if(current.visited == true){
                System.out.print("V");
            }else{
                System.out.print(" ");
            }
            current.visited = false;
            current.ond = false;
            int levelStartVal = current.value;
            // print one level
            while(current.next != null &&  current.next.value != levelStartVal){
                while(toBeSkipped  > 1)
                {
                    System.out.print("           ");
                    toBeSkipped--;
                }
                current = current.next;
                System.out.print(" ::-> ");
                if(current.ond){
                    System.out.print("O");
                }else{
                    System.out.print(" ");
                }
                if(current.visited == true)
                {
                    System.out.print(current.value + "V");
                }
                else
                {
                    System.out.print(current.value + " ");
                }
                current.visited = false;
                current.ond = false;
                toBeSkipped =   current.index;
            }
            System.out.println();
            if(levelStart.down != null) {
                levelStart = levelStart.down;
                current = levelStart;
            }
            else break;
        }
        System.out.println();
        System.out.println();
    }

    // Print out the List
    public static void printListWithIndex(){
        System.out.println("Start New Print:");
        Node current = HEAD;
        Node levelStart = HEAD;
        // print all levels
        while(true){

            System.out.print(current.value + ":" + current.index);
            int levelStartVal = current.value;
            // print one level
            while(current.next != null &&  current.next.value != levelStartVal){
                current = current.next;
                System.out.print(" ::-> " + current.value+ ":" + current.index);
            }
            System.out.println();
            if(levelStart.down != null) {
                levelStart = levelStart.down;
                current = levelStart;
            }
            else break;
        }
    }

    //Update the the nodes skip values when a node is inserted into the List
    // the inserted node should be the lowest inserted on the list so that it is easy to go back up and add - this node tested on 11/6
    public static void updateNxtIndex(Node start, int updateHieght) {
        //TODO - understading the insert() method probably would help for this because it will invole some of the same concepts
        Node current = start;
        Node cw = current.prev;
        int before = cw.index; //1
        //int h = Math.min(maxHeight, updateHieght + 1);
        for (int i = 1; i < maxHeight; i++) {
            while(cw.up == null){
                cw = cw.prev;
                before+=cw.index;
            }
            cw = cw.up;
            if(current.up != null && current.value != cw.value){
                int after = cw.index - before;
                cw.index = before;
                current = current.up;
                current.index = after + 1;
            } else{
                cw.index++;
            }

        }
    }
}
