/**
 * Class used to test the functionality of BST.java class
 */
public class BSTMain {
    public static void main(String[] args){
        Profile n1 = new Profile("Smith", "Mark", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        Profile n2 = new Profile("Johnson", "Jon", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        Profile n3 = new Profile("Williams", "Bob", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        Profile n4 = new Profile("Brown", "Greg", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        BST bst = new BST();
        bst.insertProfile(n1);
        bst.insertProfile(n2);
        bst.insertProfile(n3);
        bst.insertProfile(n4);
        bst.printAlphabetical();
    }
}
