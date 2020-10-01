import java.util.ArrayList;

/**
 * Template to create user profile for a social network
 */
public class Profile {

    private String firstName, lastName;
    private int day, month, year; //Day of birth
    private String emailAddress;
    private String[] interests;
    private String[] groupsAndActivities;
    private ArrayList<Profile> friends;

    /**
     * Constructor of the profile class
     *
     * @param lastName            last name of the user profile
     * @param firstName           first name of the user profile
     * @param day                 day of birth of the user profile
     * @param month               month of birth of the user profile
     * @param year                year of the birth of the user profile
     * @param emailAddress        email address of the user profile
     * @param interests           interests list of the user profile
     * @param groupsAndActivities groups and activities list of the user profile
     */
    public Profile(String lastName, String firstName, int day, int month, int year, String emailAddress,
                   String[] interests, String[] groupsAndActivities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.day = day;
        this.month = month;
        this.year = year;
        checkDate(day, month, year);
        this.emailAddress = emailAddress;
        this.interests = interests;
        this.groupsAndActivities = groupsAndActivities;
        this.friends = new ArrayList<>();
    }

    /**
     * Method checks whether the birth date is a valid date
     *
     * @param d day
     * @param m month
     * @param y year
     */
    private void checkDate(int d, int m, int y) {
        if (d < 1 || m < 1 || y < 1) {
            System.out.println("Profile cannot be created, date invalid. Closing...");
            System.exit(-1);
        }
        boolean has31Days = false;
        switch (m) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                has31Days = true;
                break;
        }
        if ((m == 2 && d > 28) || has31Days == false && d > 30) {
            System.out.println("Profile cannot be created, date invalid. Closing...");
            System.exit(-1);
        }
    }

    /**
     * Method returns full name of the profile
     *
     * @return full name
     */
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    /**
     * Method return the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method sets the first name
     *
     * @param firstName the first name to set to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method returns the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method sets the last name
     *
     * @param lastName last name to set to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method returns the date of birth in DD/MM/YYYY format
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return "" + day + "/" + month + "/" + year;
    }

    /**
     * Method returns the email address
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Method sets the email address
     *
     * @param emailAddress email address to set to
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Method returns the list of interests
     *
     * @return the list of interests
     */
    public String[] getInterests() {
        return interests;
    }

    /**
     * Method sets the interests from a given list
     *
     * @param interests list of interests to set to
     */
    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    /**
     * Method returns the list of groups and activities
     *
     * @return list of groups and activities
     */
    public String[] getGroupsAndActivities() {
        return groupsAndActivities;
    }

    /**
     * Method sets groups and activities from a given list
     *
     * @param groupsAndActivities list of groups and activities to set to
     */
    public void setGroupsAndActivities(String[] groupsAndActivities) {
        this.groupsAndActivities = groupsAndActivities;
    }

    /**
     * Method adds profile of a friend to a friend list
     *
     * @param p profile to add to the list
     */
    public void addFriend(Profile p) {
        friends.add(p);
    }

    /**
     * Method returns the profile of a friend at given index
     *
     * @param i index which friend profile to return
     * @return friend profile
     */
    public Profile getFriend(int i) {
        return friends.get(i);
    }

    /**
     * Method returns the number of friends
     *
     * @return number of friends
     */
    public int numOfFriends() {
        return friends.size();
    }

    /**
     * Method returns all the information about a profile as a string including first and last name date of birth
     * email address interest, activities and groups, friend list.
     *
     * @return all the information about a profile
     */
    @Override
    public String toString() {
        String base = getFirstName() + " " + getLastName() + " " + getDateOfBirth() + " " + getEmailAddress();
        String[] groups = getGroupsAndActivities();
        String[] interests = getInterests();
        String group = " ";
        String interest = "";
        String friendsString = "";
        for (String string : groups) {
            group += string + " ";
        }
        for (String string : interests) {
            interest += string + " ";
        }
        for (Profile profile : friends) {
            friendsString += profile.getFirstName() + " " + profile.getLastName();
        }

        return base + group + interest + friendsString;
    }
}
