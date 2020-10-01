import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that supplies static method to read files
 */
public class FileReader {

    /**
     * Method reads the profile data from a file and creates binary search tree
     * @param filename name of the file
     * @return binary search tree
     */
    public static BST readProfileSet(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        ArrayList<String[]> profilesData = readLines(scanner);
        scanner.close();
        ArrayList<Profile> profiles = new ArrayList<>();
        //Creates profiles from a data list
        for (String[] profileData : profilesData) {
            profiles.add(createProfile(profileData));
        }
        //Creates binary search tree from a profile list
        BST bst = new BST();
        for(Profile profile: profiles){
            bst.insertProfile(profile);
        }
        return bst;
    }

    /**
     * Method creates a profile based on a given data
     * @param line data about profile
     * @return created profile
     */
    private static Profile createProfile(String[] line) {
        String lastName = line[0];
        String firstName = line[1];
        int day = Integer.parseInt(line[2]);
        int month = Integer.parseInt(line[3]);
        int year = Integer.parseInt(line[4]);
        String email = line[5];
        String[] interest = lineSplit(line[6], ";");
        String[] activities = lineSplit(line[7], ";");
        return new Profile(lastName, firstName, day, month, year, email, interest, activities);
    }

    /**
     * Method reads the lines of a file and returns them as list of lists
     * @param scanner scanner that reads the file
     * @return List of the profiles information stored as a list
     */
    private static ArrayList<String[]> readLines(Scanner scanner) {
        ArrayList<String[]> data = new ArrayList<>();
        while (scanner.hasNextLine()) {
            data.add(lineSplit(scanner.nextLine(), ","));
        }
        return data;
    }

    /**
     * Method splits the elements of a string into array based on given delimiter
     * @param line string to split
     * @param delimiter delimiter on which you want to split
     * @return array of splitted elements
     */
    private static String[] lineSplit(String line, String delimiter) {
        String[] tokens = line.split(delimiter);
        return tokens;
    }
}
