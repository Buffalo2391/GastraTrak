package levi.gastratrak;
import java.sql.Time;

/**
 * Created by Levi on 17/11/2017.
 */

public class FoodItem {
    private String foodType = "";
    private Time consumedTime = null;
    public long id = 0;

    public FoodItem(String foodName, Time time) {
        this.setFoodItem(foodName);
        this.setItemTime(time);
        this.id = time.getTime();
    }

    public void setFoodItem(String foodItem){
        this.foodType = foodItem;
    }
    public void setItemTime(Time foodTime){
        this.consumedTime = foodTime;
    }
    public Time getFoodTime(){
        return this.consumedTime;
    }
    public  String getFoodItem(){
        return this.foodType;
    }

}