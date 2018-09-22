package sample;

public class TestData {

    private static String testCode;
    private static  int time;

    public TestData(String testCode, int time){
        this.time= time;
        this.testCode=testCode;
    }

    public TestData(){};

    public int getTestCode(){
        return Integer.parseInt(testCode);
    }

    public int getTime(){
        return time;
    }
}
