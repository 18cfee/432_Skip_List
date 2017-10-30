import java.util.HashSet;

public class MainList {
    private static Node HEAD = null;
    private static int totalNumberNodes = 0;
    public static int maxHeight = 1;
    public static void main(String args[]){
        // initialize as a circle to itself
        Node originalHEAD = new Node(4);
        HEAD = originalHEAD;

        printList();
        insert(new Node(7),1);
        insert(new Node(6),1);
        insert(new Node(-230),1);
        insert(new Node(-23423),2);
        insert(new Node(9),1);
        insert(new Node(70),1);
        insert(new Node(34),1);
        printList();
        insert(new Node(-2343),2);
        printList();
        insert(new Node(94),1);
        printList();
        insert(new Node(73),2);
        printList();
        insert(new Node(3434),1);
        insert(new Node(-233),2);
        insert(new Node(92),1);
        insert(new Node(702),2);
        insert(new Node(342),1);
        printList();


    }

    // h is hardcoded in this one
    public static boolean insert(Node node, int h){
        int insertVal = node.value;
        Node current = HEAD;
        // bounds checking, since next is checked from here on
        if(insertVal == HEAD.value){
            return true;
        }
        while(current.next == null){
            if(current.down != null){
                current = current.down;
            } else{
                current.next = node;
                current.prev = node;
                node.next = current;
                node.prev = current;
                return false;
            }
        }
        while(true){
            int onVal = current.value;
            Node nextN = current.next;
            int nextVal = nextN.value;
            if(nextVal == insertVal) return true;
            // right interval (include circular logic)
            if((onVal < insertVal && insertVal < nextVal) || (onVal > nextVal && (insertVal > onVal || insertVal < nextVal))){
                if(current.down == null){
                    current.next = node;
                    node.prev = current;
                    node.next = nextN;
                    nextN.prev = node;
                    // Insert extra levels after original insert
                    for (int i = 2; i <= h; i++) {
                        node.up = new Node(insertVal);
                        node.up.down = node;

                        if(i > maxHeight){
                            HEAD = node.up;
                            maxHeight = i;
                            return false;
                        }


                        Node ccw = node.next;
                        Node cw = node.prev;
                        while(cw.up == null){
                            cw = cw.prev;
                        }
                        while(ccw.next == null){
                            ccw = node.next;
                        }
                        node.prev = cw;
                        node.next = ccw;
                        ccw.prev = node;
                        cw.next = node;

                        node = node.up;
                    }
                    return false;
                } else{
                    current = current.down;
                }
            } else current = nextN;
        }
    }

    // Print out the List
    public static void printList(){
        System.out.println("Start New Print:");
        Node current = HEAD;
        Node levelStart = HEAD;
        // print all levels
        while(true){

            System.out.print(current.value);
            while(current.next != null &&  current.next.value != levelStart.value){
                current = current.next;
                System.out.print(" ::-> " + current.value);
            }
            System.out.println();
            if(current.down != null) current = current.down;
            else break;
        }
    }

    // h will be chosen automatically in this one
    public static void insert(Node node){

    }

    //
    public static boolean deleteNode(int value){
        return false;
    }

    public static boolean searchNode(int value){
        return false;
    }

    // Based on distribution
    public static int pickRandomHeight(){
        return 1; // Change this
    }

    // array of nodes from highest level to lowest
    public static Node[] chooseOrigin(int otgt){
        return null;
    }

    // Choose the new value based on the absolute positioning
    public static int chooseNewOTGT(){
        return 0;
    }
}
