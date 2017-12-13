package levi.gastratrak;
import java.sql.Time;

/**
 * Created by Levi on 17/11/2017. Holder for pain information
 */

class PainItem {
    private Time recordedTime = null;
    private int[] painArray;


    public PainItem(int[] painArray, Time time) {
        this.setPainLevel(painArray);
        this.setPainTime(time);
    }
    private void setPainLevel(int[] painArray){
        this.painArray = painArray.clone();
    }
    public int[] getPainLevel(){
        return this.painArray;
    }
    private void setPainTime(Time time){
        this.recordedTime = time;
    }
    public Time getPainTime(){
        return this.recordedTime;
    }

}