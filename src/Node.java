public class Node {
    public boolean visited = false; //for the search method, to print the path out and make sure it doesn't infinite loop on us.
    public int value = 0;
    public Node next = null;
    public Node prev = null;
    public Node up = null;
    public Node down = null;
    // not using skip as of now
    public int skip = 1;
    public int index = -1;
    public Node(int value){
        this.value = value;
    }
}
