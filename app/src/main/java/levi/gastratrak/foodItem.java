package levi.gastratrak;
import java.sql.Time;

/**
 * Created by Levi on 17/11/2017.
 */

public class FoodItem {
    private String foodItem = "";
    private Time foodTime = null;

    public FoodItem(String foodName, Time time) {
        this.setFoodItem(foodName);
        this.setItemTime(time);
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public void setItemTime(Time foodTime) {
        this.foodTime = foodTime;
    }

    public Time getFoodTime() {
        return this.foodTime;
    }

    public String getFoodItem() {
        return this.foodItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != FoodItem.class) {
            return false;
        }
        if (((FoodItem) obj).getFoodItem() == this.getFoodItem() &&
                ((FoodItem) obj).getFoodTime() == this.getFoodTime()) {
                return true;
            }
        return false;

    }
}