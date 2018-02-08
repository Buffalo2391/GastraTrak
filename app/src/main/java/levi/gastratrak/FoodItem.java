package levi.gastratrak;
import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Levi on 17/11/2017. a class to hold information regarding food items
 */

class FoodItem {
    private String foodItem = "";
    private Calendar foodTime = null;

    public FoodItem(String foodName, Calendar time) {
        this.setFoodItem(foodName);
        this.setItemTime(time);
    }

    private void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    private void setItemTime(Calendar foodTime) {
        this.foodTime = foodTime;
    }

    public Calendar getFoodTime() {
        return this.foodTime;
    }

    public String getFoodItem() {
        return this.foodItem;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FoodItem.class &&
                ((FoodItem) obj).getFoodItem().equals(this.getFoodItem()) &&
                ((FoodItem) obj).getFoodTime().equals(this.getFoodTime());
    }

}