package sample.Student;

public class TestData {

    private static String testCode;
    private static  int time;

    public TestData(String testCode, int time){
        this.time= time;
        this.testCode=testCode;
    }

    public TestData(){};

    public String getTestCode(){
        return (testCode);
    }

    public int getTime(){
        return time;
    }
}
