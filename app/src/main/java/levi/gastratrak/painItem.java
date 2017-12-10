package levi.gastratrak;
import java.sql.Time;

/**
 * Created by Levi on 17/11/2017.
 */

public class PainItem {
    private Time recordedTime = null;
    private int[] painArray;


    public PainItem(int[] painArray, Time time) {
        this.setPainLevel(painArray);
        this.setPainTime(time);
    }
    public void setPainLevel(int[] painArray){
        this.painArray = painArray.clone();
    }
    public int[] getPainLevel(){
        return this.painArray;
    }
    public void setPainTime(Time time){
        this.recordedTime = time;
    }
    public Time getPainTime(){
        return this.recordedTime;
    }

}