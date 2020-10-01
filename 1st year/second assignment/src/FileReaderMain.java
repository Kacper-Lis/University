/**
 * Class used to test the FileReader.java class functionality
 */
public class FileReaderMain {
    public static void main(String[] args){
        BST bst = FileReader.readProfileSet("src\\data\\profiles_01.txt");
        bst.printAlphabetical();
    }
}
