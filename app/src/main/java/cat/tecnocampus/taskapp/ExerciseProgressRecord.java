package cat.tecnocampus.taskapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseProgressRecord implements Parcelable {
    String date;
    String exercise_name;
    String num_set;
    String repetitions;
    String weight;
    String rpe;
    String rir;

    public ExerciseProgressRecord(String date, String exercise_name, String num_set, String repetitions, String weight, String rpe, String rir) {
        this.date = date;
        this.exercise_name = exercise_name;
        this.num_set = num_set;
        this.repetitions = repetitions;
        this.weight = weight;
        this.rpe = rpe;
        this.rir = rir;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public String getNum_set() {
        return num_set;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public String getWeight() {
        return weight;
    }

    public String getRpe() {
        return rpe;
    }

    public String getRir() {
        return rir;
    }


    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public void setNum_set(String num_set) {
        this.num_set = num_set;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setRpe(String rpe) {
        this.rpe = rpe;
    }

    public void setRir(String rir) {
        this.rir = rir;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(exercise_name);
        parcel.writeString(num_set);
        parcel.writeString(repetitions);
        parcel.writeString(weight);
        parcel.writeString(rpe);
        parcel.writeString(rir);
    }

    protected ExerciseProgressRecord(android.os.Parcel in) {
        date = in.readString();
        exercise_name = in.readString();
        num_set = in.readString();
        repetitions = in.readString();
        weight = in.readString();
        rpe = in.readString();
        rir = in.readString();
    }

    public static final Creator<ExerciseProgressRecord> CREATOR = new Creator<ExerciseProgressRecord>() {
        @Override
        public ExerciseProgressRecord createFromParcel(android.os.Parcel in) {
            return new ExerciseProgressRecord(in);
        }

        @Override
        public ExerciseProgressRecord[] newArray(int size) {
            return new ExerciseProgressRecord[size];
        }
    };


}
