import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Template to create user recommendation graph for profiles
 */
public class Graph {
    private ArrayList<String> names = new ArrayList<>();
    private BST bst;
    private Scanner scanner = null;
    private HashMap<String, Profile> profileMap = new HashMap<>();
    private HashMap<Profile, ArrayList<Profile>> edgeList = new HashMap<>();

    /**
     * Constructor of a graph class
     *
     * @param filename name of the file with edge list
     * @param bst      binary search tree reference
     */
    public Graph(String filename, BST bst) {
        this.bst = bst;
        try {
            File file = new File(filename);
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    /**
     * Method to get the friend recommendations for a list of users
     *
     * @param users list of users
     * @return trees of user recommendations for each user
     */
    public BST[] friendRecommendations(Profile[] users) {
        for (Profile profile : users) {
            edgeList.put(profile, new ArrayList<>());
        }
        populateNamesHashMap(users);
        addFriendsFromFile();
        BST[] bsts = new BST[users.length];
        for (int i = 0; i < users.length; i++) {
            BST bst = new BST();
            ArrayList<Profile> list = edgeList.get(users[i]);
            for (int j = 0; j < list.size(); j++) {
                ArrayList<Profile> friendList = edgeList.get(users[j]);
                for (int k = 0; k < friendList.size(); k++) {
                    if (!list.contains(friendList.get(k)) && !users[i].equals(friendList.get(k))) {
                        bst.insertProfile(friendList.get(k));
                    }
                }
            }
            bsts[i] = bst;
        }
        return bsts;
    }

    /**
     * Method adds profiles to the hashmap that associates names of users with their profile object
     *
     * @param profiles user list
     */
    private void populateNamesHashMap(Profile[] profiles) {
        for (Profile profile : profiles) {
            profileMap.put(profile.getName(), profile);
            names.add(profile.getName());
        }
    }

    /**
     * Method reads file and adds connections between users to the edge list
     */
    private void addFriendsFromFile() {
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(",");
            if (profileMap.get(tokens[0]) == null || profileMap.get(tokens[1]) == null) {
                System.out.println("Profile does not exist.");
                System.exit(1);
            }
            edgeList.get(profileMap.get(tokens[0])).add(profileMap.get(tokens[1]));
            edgeList.get(profileMap.get(tokens[1])).add(profileMap.get(tokens[0]));
        }
    }
}
