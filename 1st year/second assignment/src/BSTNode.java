/**
 * Template to create nodes of binary search tree
 */
public class BSTNode {
    private Profile data;
    private BSTNode l;
    private BSTNode r;

    /**
     * Constructor of a BSTNode class
     * @param data profile to be inserted into a node
     */
    public BSTNode(Profile data) {
        this.data = data;
        l = null;
        r = null;
    }

    /**
     * Method returns left node
     * @return left node
     */
    public BSTNode getL() {
        return l;
    }

    /**
     * Method sets the left node
     * @param l node to set to
     */
    public void setL(BSTNode l) {
        this.l = l;
    }

    /**
     * Method returns the right node
     * @return right node
     */
    public BSTNode getR() {
        return r;
    }

    /**
     * Method sets the right node
     * @param r node to set to
     */
    public void setR(BSTNode r) {
        this.r = r;
    }

    /**
     * Method returns the profile the node is storing
     * @return profile of the node
     */
    public Profile getProfile() {
        return data;
    }
}
