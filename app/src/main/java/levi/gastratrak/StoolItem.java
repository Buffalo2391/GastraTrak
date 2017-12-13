package levi.gastratrak;

import java.sql.Time;

/**
 * Created by Levi on 6/12/2017. holds information about stools
 */

class StoolItem {
    private Time recordedTime = null;
    private int[] stoolArray;


    public StoolItem(int[] stoolArray, Time time) {
        this.setStoolArray(stoolArray);
        this.setPainTime(time);
    }
    private void setStoolArray(int[] stoolArray){
        this.stoolArray = stoolArray.clone();
    }
    public int[] getStoolArray(){
        return this.stoolArray;
    }
    private void setPainTime(Time time){
        this.recordedTime = time;
    }
    public Time getStoolTime(){return this.recordedTime;}

}

