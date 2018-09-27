package sample.Student;

public class Clock {

    private int time;

    public Clock(int time){

        this.time= (time*60);
    }


    public void down(){

        time = time -1;
    }

    public int getTime() {
        return time;
    }
}
