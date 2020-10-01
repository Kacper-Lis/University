import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to test the functionality of the Graph.java class
 */
public class GraphMain {
    public static void main(String[] args) {
        BST bst = FileReader.readProfileSet("src\\data\\profiles_02.txt");
        Graph graph = new Graph("src\\data\\graph_02.txt", bst);
        Profile[] profiles = bst.getTreeElements();
        BST[] bsts = graph.friendRecommendations(profiles);
        for (int i = 0; i < bsts.length; i++) {
            System.out.println("Recommended friends for: " + profiles[i]);
            bsts[i].printAlphabetical();
            System.out.println("");
        }
    }
}
