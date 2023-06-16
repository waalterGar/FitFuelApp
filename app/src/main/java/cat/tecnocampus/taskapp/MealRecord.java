package cat.tecnocampus.taskapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MealRecord implements Parcelable {
    int id_meal_record;
    int id_meal;
    String meal_name;
    int quantity;
    String date;
    String eaten;

    public MealRecord(int id_meal_record, int id_meal, String meal_name, int quantity, String date, String eaten) {
        this.id_meal_record = id_meal_record;
        this.id_meal = id_meal;
        this.meal_name = meal_name;
        this.quantity = quantity;
        this.date = date;
        this.eaten = eaten;
    }

    public int getId_meal() {
        return id_meal;
    }

    public void setId_meal(int id_meal) {
        this.id_meal = id_meal;
    }

    public int getId_meal_record() {
        return id_meal_record;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEaten() {
        return eaten.equals("1") ? "✅" : "❌";
    }

    public void setEaten(String eaten) {
        this.eaten = eaten;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id_meal_record);
            parcel.writeString(meal_name);
            parcel.writeInt(quantity);
            parcel.writeString(date);
            parcel.writeString(eaten);
    }

    protected MealRecord(Parcel in) {
        id_meal_record = in.readInt();
        id_meal = in.readInt();
        meal_name = in.readString();
        quantity = in.readInt();
        date = in.readString();
        eaten = in.readString();
    }

    public static final Creator<MealRecord> CREATOR = new Creator<MealRecord>() {
        @Override
        public MealRecord createFromParcel(Parcel in) {
            return new MealRecord(in);
        }

        @Override
        public MealRecord[] newArray(int size) {
            return new MealRecord[size];
        }
    };

}
