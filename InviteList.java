import java.lang.IllegalArgumentException;
import java.util.*;

public class InviteList {
    
    
    // Finals
    public static final int MIN_TITLE_LEN = 5;
    public static final String DEFAULT_TITLE = "Untitled Event";
    public static final int DEFAULT_HOUR = 17;
    public static final int DEFAULT_EXTRA_GUESTS = 2;
    public static final int MIN_HOUR = 0;
    public static final int MAX_HOUR = 23;
    public static final int MIN_EXTRA_GUESTS = 0;

    
    // Instance variables
    private String title;
    private String[] invitees;
    private int hour;
    private int maxExtraGuests;
    private String[] RSVPed = {" "};
    private int currentRSVPs;
    private int totalGuests;
    private static String[] blackListed;
    
    
    // Class variables
    private static int totalRSVPs;
    private static int totalInvitees;
    
    
    // Constructors
    public InviteList(String title, String[] invitees, int hour, int maxExtraGuests) {   
        if (title.length() < MIN_TITLE_LEN){
            title = DEFAULT_TITLE;
        }
        this.title = title;

        for (String name : invitees){
            if (name == null || countIgnoreCase(name, invitees) > 1 || name.length() < 2){
                throw new IllegalArgumentException("Invitees cannot contain any duplicates, names < 2 characters long, or null values");
            }
        }
        if (title.equalsIgnoreCase("blacklist")){
            this.blackListed = invitees;
            return;
        }
        else {
            this.invitees = invitees;
        }
        totalInvitees += this.invitees.length;

        if (hour < MIN_HOUR || hour > MAX_HOUR){
            hour = DEFAULT_HOUR;
        }
        this.hour = hour;

        if (maxExtraGuests < MIN_EXTRA_GUESTS){
            maxExtraGuests = DEFAULT_EXTRA_GUESTS;
        }
        this.maxExtraGuests = maxExtraGuests;
    }
    
    public InviteList(String title, String[] invitees, int hour) {   
        this(title, invitees, hour, -1);
    }
    
    
    public InviteList(String title, String[] invitees) {   
        this(title, invitees, -1, -1);
    }
    
    
    public InviteList(String[] invitees) {   
        this("NONE", invitees, -1, -1);
    }

    // Basic Functionality
    public boolean markAsRSVPed(String invitee, int ExtraGuests){
        if (!containsIgnoreCase(invitee, RSVPed) && containsIgnoreCase(invitee, this.invitees) && ExtraGuests <= maxExtraGuests){
            this.currentRSVPs++;
            String[] RSVPcopy = this.RSVPed.clone();
            this.RSVPed = new String[this.currentRSVPs];
            for (int i = 0; i < RSVPcopy.length; i++){
                this.RSVPed[i] = RSVPcopy[i];
            }
            this.RSVPed[currentRSVPs-1] = invitee;
            this.totalGuests += ExtraGuests + 1;
            totalRSVPs++;
            return true;
        }
        return false;
    }

    public boolean hasRSVPed(String invitee){
        if (!containsIgnoreCase(invitee, this.invitees)){
            throw new IllegalArgumentException("This invitee is not invited to this event");
        }
        if (containsIgnoreCase(invitee, this.RSVPed)){
            return true;
        }
        return false;
    }

    public boolean isInvited(String invitee){
        if (containsIgnoreCase(invitee, this.invitees)){
            return true;
        }
        return false;
    }

    public String toString(){
        return (this.title + " at " + this.hour + ":00!\nTotal Guests: " + this.totalGuests + " (" + (roundTo(((double)this.getRSVPCount()/(double)this.invitees.length) * 100, 2)) + "% of invitees RSVPed)");
    }

    // Accessor Methods
    public String getTitle(){
        return this.title;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMaxExtraGuests(){
        return this.maxExtraGuests;
    }

    public int getRSVPCount(){
        if (RSVPed[0].equals(" ")){
            return 0;
        }
        return this.RSVPed.length;
    }

    public int getTotalAttendees(){
        return this.totalGuests;
    }

    // Advanced Functionality
    public static double getRSVPRate(){
        return (roundTo(((double)totalRSVPs/(double)totalInvitees), 2));
    }

    public static int countOverlapRSVPs(InviteList l1, InviteList l2){
        String[] first = l1.invitees;
        if (l1.invitees.length > l2.invitees.length)
            first = l2.invitees;
        int count = 0;
        for (String invitee : first){
            if (containsIgnoreCase(invitee, l1.invitees) && containsIgnoreCase(invitee, l2.invitees)){
                if (l1.hasRSVPed(invitee) && l2.hasRSVPed(invitee)){
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isCoolEvent(){
        if (getRSVPRate() < (double)this.RSVPed.length/(double)this.invitees.length && this.hour >= 20){
            return true;
        }
        return false;
    }

    public static InviteList createDuplicate(InviteList toCopy, String[] blackList){
        InviteList blackListInvite = new InviteList("blacklist", blackList);
        int count = 0;
        int i = 0;
        String[] Copy = toCopy.invitees.clone();
        for (String name : Copy){
            for (String blacklists : blackListed){
                if (name.equalsIgnoreCase(blacklists)){
                    count++;
                    Copy[i] = " ";
                }
            }
            i++;
        }
        String[] finalCopy = new String[count];
        for (i = 0; i < count; i++){
            if (!Copy[i].equals(" ")){
                finalCopy[i] = toCopy.invitees[i];
            }
        }
        InviteList CopyInviteList = new InviteList(toCopy.title, finalCopy, toCopy.hour, 0);
        return CopyInviteList;
    }
    
    //Returns the index of the first occurrence of target (case insensitive)
    //in values or -1 if target is not found
    public static int indexOfIgnoreCase(String target, String[] values) {
        if (target != null && values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i].equalsIgnoreCase(target))
                    return i;
            }
        }
        return -1;
    }
    
    //Returns boolean indicating if target appears (case insensitive)
    //in values (true) or not (false)
    public static boolean containsIgnoreCase(String target, String[] values) {
        if (target != null && values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i].equalsIgnoreCase(target))
                    return true;
            }
        }
        return false;
    }

    //Returns number of times target appears (case insensitive)
    //in values
    public static int countIgnoreCase(String target, String[] values) {
        int count = 0;
        for (String str : values) {
            if (target.equalsIgnoreCase(str))
                count++;
        }
        return count;
    }
    
    //Rounds num to n decimal places
    public static double roundTo(double num, int n) {
        return Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
    }
    
}
