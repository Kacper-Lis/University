import java.util.ArrayList;

/**
 * Template to create the binary search tree object
 */
public class BST {
    private BSTNode root;

    /**
     * Constructor for the binary search tree class
     */
    public BST() {
        root = null;
    }

    /**
     * Method inserts profile into the binary search tree
     * @param p profile to insert into the tree
     */
    public void insertProfile(Profile p) {
        BSTNode node = new BSTNode(p);
        if (root == null) {
            root = node;
        } else {
            insert(root, p);
        }
    }

    /**
     * Method that recursively inserts profiles into the tree in the correct last name's alphabetical order
     * @param node reference node
     * @param p profile to insert
     * @return node
     */
    private BSTNode insert(BSTNode node, Profile p) {
        if (node == null){
            node = new BSTNode(p);
            return node;
        }
        if(p.getLastName().compareTo(node.getProfile().getLastName()) <= 0){
            node.setL(insert(node.getL(), p));
        } else if(p.getLastName().compareTo(node.getProfile().getLastName()) > 0){
            node.setR(insert(node.getR(), p));
        }
        return node;
    }

    /**
     * Method prints the tree in alphabetical order
     */
    public void printAlphabetical(){
        if(root == null){
            System.out.println("Tree empty");
            return;
        }
        treeTraversal(root);
    }

    /**
     * Method preforms inorder traversal to get elements of the tree in correct order
     * @param node reference node
     */
    private void treeTraversal(BSTNode node){
        if(node.getL() != null){
            treeTraversal(node.getL());
        }
        System.out.println(node.getProfile());
        if (node.getR() != null){
            treeTraversal(node.getR());
        }
    }

    /**
     * Method returns the list of elements of each node of the tree
     * @return list of elements of the tree
     */
    public Profile[] getTreeElements(){
        ArrayList<Profile> arrayList = new ArrayList<>();
        getElements(root, arrayList);
        Profile[] profiles = new Profile[arrayList.size()];
        for(int i = 0; i < profiles.length; i++){
            profiles[i] = arrayList.get(i);
        }
        return profiles;
    }

    /**
     * Method recursevliy adds elements of every node to an array
     * @param node reference node
     * @param array array to add to
     */
    private void getElements(BSTNode node, ArrayList<Profile> array){
        if(node.getL() != null){
            getElements(node.getL(), array);
        }
        array.add(node.getProfile());
        if (node.getR() != null){
            getElements(node.getR(), array);
        }
    }
}
