package levi.gastratrak;
import java.sql.Time;

/**
 * Created by Levi on 17/11/2017. a class to hold information regarding food items
 */

class FoodItem {
    private String foodItem = "";
    private Time foodTime = null;

    public FoodItem(String foodName, Time time) {
        this.setFoodItem(foodName);
        this.setItemTime(time);
    }

    private void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    private void setItemTime(Time foodTime) {
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
        return obj.getClass() == FoodItem.class &&
                ((FoodItem) obj).getFoodItem().equals(this.getFoodItem()) &&
                ((FoodItem) obj).getFoodTime().equals(this.getFoodTime());
    }

}