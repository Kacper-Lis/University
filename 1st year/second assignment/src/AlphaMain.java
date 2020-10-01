/**
 * Class used to test BST.java class treetraversal functionality
 */
public class AlphaMain {
    public static void main(String[] args){
        BST bst = FileReader.readProfileSet("src\\data\\profiles_01.txt");
        bst.printAlphabetical();
    }
}
