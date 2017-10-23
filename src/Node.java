public class Node {
    public int value = 0;
    public String myinfo = null;
    public Node next = null;
    public Node prev = null;
    public Node up = null;
    public Node down = null;
    public int skip = 1;
    public int index = -1;
    public Node(int value, String myinfo){
        this.value = value;
        this.myinfo = myinfo;
    }
}
