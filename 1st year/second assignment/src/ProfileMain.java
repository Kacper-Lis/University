/**
 * Class used to test the functionality of the Profile.java class
 */
public class ProfileMain {
    public static void main(String[] args){
        Profile n1 = new Profile("Smith", "Mark", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        Profile n2 = new Profile("Johnson", "Jon", 1, 1, 2000,
                "asa@gmail.com", new String[] {"interest1", "interest2"}, new String[] {"activ1" });
        System.out.println(n1);
        n1.addFriend(n2);
        System.out.println(n1.getFriend(0));
        System.out.println(n1.numOfFriends());
        System.out.println(n1.getDateOfBirth());
    }
}
