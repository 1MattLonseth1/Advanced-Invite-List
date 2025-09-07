import java.lang.IllegalArgumentException;
import java.util.Arrays;

public class InviteListClient {
  
  
  public static  void main(String[] args){
    
    String[] invitees1 = {"Terra", "Locke", "Edgar", "Celes", "Mog"};
    InviteList test1 = new InviteList("Test #1", invitees1, 14, 2);
    
    System.out.println();

    System.out.println("markAsRSVPed(Mog, 1)?: " + test1.markAsRSVPed("Mog", 1));
    System.out.println("markAsRSVPed(Edgar, 3)?: " + test1.markAsRSVPed("Edgar", 3));
    System.out.println("markAsRSVPed(Matt, 4)?: " + test1.markAsRSVPed("Matt", 4));

    System.out.println();

    System.out.println("isInvited(Celes)?: " + test1.isInvited("Celes"));
    System.out.println("isInvited(Kefka)?: " + test1.isInvited("Kefka"));

    System.out.println();

    System.out.println("hasRSVPed(Mog)?: " + test1.hasRSVPed("Mog")); 
    System.out.println("hasRSVPed(Terra)?: " +test1.hasRSVPed("Terra"));    

    System.out.println();

    System.out.println(test1);

    System.out.println();

    String[] invitees2 = {"Rob", "Matt", "Vincent", "Anthony", "Victoria", "Ash", "Kim", "Mike", "Sam"};
    InviteList test2 = new InviteList("Birthday Party", invitees2, 16, 1);


    test2.markAsRSVPed("Rob", 1);
    test2.markAsRSVPed("Matt", 1);
    test2.markAsRSVPed("Vincent", 1);
    test2.markAsRSVPed("Ash", 1);
    test2.markAsRSVPed("Kim", 1);
    test2.markAsRSVPed("Sam", 1);

    System.out.println(test2);

    System.out.println();

    System.out.println("Basic Functionality");

    System.out.println(test2.getTitle());
    System.out.println(test2.getHour());
    System.out.println(test2.getMaxExtraGuests());
    System.out.println(test2.getRSVPCount());
    System.out.println(test2.getTotalAttendees());

    System.out.println();

    System.out.println("RSVP rate");

    System.out.println(InviteList.getRSVPRate());

    System.out.println();

    System.out.println("Test3 and Test4 Overlaps");

    String[] invitees3 = {"Roberto", "Matt", "Nectar", "Cammy", "Karly", "Ash", "Kim", "Poke"};
    InviteList test3 = new InviteList("Wedding", invitees3, 20, 3);

    test3.markAsRSVPed("Roberto", 1);
    test3.markAsRSVPed("Matt", 1);
    test3.markAsRSVPed("Nectar", 2);
    test3.markAsRSVPed("Cammy", 3);
    test3.markAsRSVPed("Karly", 1);
    test3.markAsRSVPed("Ash", 1);
    test3.markAsRSVPed("Poke", 2);

    String[] invitees4 = {"Roberto", "Matt", "Nectar", "Karly", "Anne", "Kim", "Poke"};
    InviteList test4 = new InviteList("Wedding", invitees4, 4, -1);

    test4.markAsRSVPed("Roberto", 1);
    test4.markAsRSVPed("Matt", 1);
    test4.markAsRSVPed("Nectar", 2);
    test4.markAsRSVPed("Ash", 2);

    System.out.println(InviteList.countOverlapRSVPs(test4, test3));

    System.out.println();

    System.out.println("Test3isCool");

    System.out.println(test3.isCoolEvent());

    System.out.println();

    System.out.println("Dupe of Test 4");

    String[] blacklist = {"Nectar", "Karly"};
    InviteList test5 = InviteList.createDuplicate(test4, blacklist);
    System.out.println(test5);

    System.out.println();

  }
  
}
