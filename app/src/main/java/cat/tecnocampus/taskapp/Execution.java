package cat.tecnocampus.taskapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Objects;

public class Execution implements Parcelable {
    String id_execution;
    String id_exercise;
    String exercise_name;
    String num_set;
    String repetitions;
    String weight;
    String rpe;
    String rir;
    String done;

    public Execution(String id_execution, String id_exercise,String exercise_name, String num_set, String repetitions, String weight, String rpe, String rir, String done) {
        this.id_execution = id_execution;
        this.exercise_name = exercise_name;
        this.num_set = num_set;
        this.repetitions = repetitions;
        this.weight = weight;
        this.rpe = rpe;
        this.rir = rir;
        this.done = done;
        this.id_exercise = id_exercise;
    }

    protected Execution(Parcel in) {
         id_execution = in.readString();
         id_exercise = in.readString();
         exercise_name = in.readString();
         num_set = in.readString();
         repetitions = in.readString();
         weight = in.readString();
         rpe = in.readString();
         rir = in.readString();
         done = in.readString();
    }

    public static final Creator<Execution> CREATOR = new Creator<Execution>() {
        @Override
        public Execution createFromParcel(Parcel in) {
            return new Execution(in);
        }

        @Override
        public Execution[] newArray(int size) {
            return new Execution[size];
        }
    };

    public Execution(JSONObject optJSONObject) {
        this.id_execution = optJSONObject.optString("id_execution");
        this.id_exercise = optJSONObject.optString("id_exercise");
        this.exercise_name = optJSONObject.optString("exercise_name");
        this.num_set = optJSONObject.optString("num_set");
        this.repetitions = optJSONObject.optString("repetitions");
        this.weight = optJSONObject.optString("weight");
        this.rpe = optJSONObject.optString("rpe");
        this.rir = optJSONObject.optString("rir");
        this.done = optJSONObject.optString("done");
    }

    public String getId_execution() {
        return id_execution;
    }

    public String getId_exercise() {
        return id_exercise;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getNum_set() {
        return num_set;
    }

    public void setNum_set(String num_set) {
        this.num_set = num_set;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRpe() {
        return rpe;
    }

    public void setRpe(String rpe) {
        this.rpe = rpe;
    }

    public String getRir() {
        return rir;
    }

    public void setRir(String rir) {
        this.rir = rir;
    }

    public String getDone() {
       return done.equals("1") ? "✅" : "❌";
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_execution);
        parcel.writeString(id_exercise);
        parcel.writeString(exercise_name);
        parcel.writeString(num_set);
        parcel.writeString(repetitions);
        parcel.writeString(weight);
        parcel.writeString(rpe);
        parcel.writeString(rir);
        parcel.writeString(done);
    }

}
