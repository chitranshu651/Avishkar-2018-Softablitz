package sample;

public class Session_Id {

    //Stores the session info of the current user
    private static String username;
    private static String TestId;
    private static int no_Question;
    private static int no_sections;
    private static String SectionId;
    private static int marks;
    private static String [] sections;

    public static String[] getSections() {
        return sections;
    }

    public static void setSections(String[] sections) {
        Session_Id.sections = sections;
    }

    public static int getMarks() {
        return marks;
    }

    public static void setMarks(int marks) {
        Session_Id.marks = marks;
    }

    public static String getSectionId() {
        return SectionId;
    }

    public static void setSectionId(String sectionId) {
        SectionId = sectionId;
    }

    public static int getNo_sections() {
        return no_sections;
    }

    public static void setNo_sections(int no_sections) {
        Session_Id.no_sections = no_sections;
    }

    public void setUsername(String Username) {
        username = Username;
    }

    public  String getUsername() {
        return username;
    }

    public static String getTestId() {
        return TestId;
    }

    public static void setTestId(String testId) {
        TestId = testId;
    }

    public static int getNo_Question() {
        return no_Question;
    }

    public static void setNo_Question(int no_Question) {
        Session_Id.no_Question = no_Question;
    }

    public static void addMarks(int add){
        marks+=add;
    }
}