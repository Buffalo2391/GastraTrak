package levi.gastratrak;

import java.sql.Time;

/**
 * Created by Levi on 6/12/2017.
 */

public class stoolItem {
    private Time recordedTime = null;
    private int[] stoolArray;


    public stoolItem(int[] stoolArray, Time time) {
        this.setStoolArray(stoolArray);
        this.setPainTime(time);
    }
    public void setStoolArray(int[] painArray){
        this.stoolArray = painArray.clone();
    }
    public int[] getStoolArray(){
        return this.stoolArray;
    }
    public void setPainTime(Time time){
        this.recordedTime = time;
    }
    public Time getStoolTime(){return this.recordedTime;}

}

