package cat.tecnocampus.taskapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Meal implements Parcelable {
    int id_meal;
    String name;
    String description;
    int calories;
    int protein;
    int carbohydrates;
    int fat;

    public Meal(int id_meal, String name, String description, int calories, int protein, int carbohydrates, int fat) {
        this.id_meal = id_meal;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    public int getId_meal() {
        return id_meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    protected Meal(Parcel in) {
        id_meal = in.readInt();
        name = in.readString();
        description = in.readString();
        calories = in.readInt();
        protein = in.readInt();
        carbohydrates = in.readInt();
        fat = in.readInt();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_meal);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(calories);
        parcel.writeInt(protein);
        parcel.writeInt(carbohydrates);
        parcel.writeInt(fat);
    }
}
