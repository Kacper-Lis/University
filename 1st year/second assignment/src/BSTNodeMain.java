/**
 * Class used to test the functionality of the BSTNode.java class
 */
public class BSTNodeMain {
    public static void main(String[] args){
        BSTNode n1 = new BSTNode(new Profile("Smith", "Mark", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" }));
        BSTNode n2 = new BSTNode(new Profile("Johnson", "Jon", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" }));
        BSTNode n3 = new BSTNode(new Profile("Williams", "Bob", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" }));
        n1.setR(n2);
        n1.setL(n3);
        System.out.println(n1.getL().getProfile());
        System.out.println(n1.getR().getProfile());
    }
}
