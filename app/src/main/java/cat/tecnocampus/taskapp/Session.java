package cat.tecnocampus.taskapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable {
    int id_training_session;
    String name;
    String description;
    String session_date;
    String trainer_notes;
    int athlete_id;

    public Session(int id_training_session, String name, String description, String session_date, String trainer_notes, int athlete_id) {
        this.id_training_session = id_training_session;
        this.name = name;
        this.description = description;
        this.session_date = session_date;
        this.trainer_notes = trainer_notes;
        this.athlete_id = athlete_id;
    }

    protected Session(Parcel in) {
        id_training_session = in.readInt();
        name = in.readString();
        session_date = in.readString();
        trainer_notes = in.readString();
        athlete_id = in.readInt();
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    public int getId_training_session() {
        return id_training_session;
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
    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }

    public String getTrainer_notes() {
        return trainer_notes;
    }

    public void setTrainer_notes(String trainer_notes) {
        this.trainer_notes = trainer_notes;
    }

    public int getAthlete_id() {
        return athlete_id;
    }

    public void setAthlete_id(int athlete_id) {
        this.athlete_id = athlete_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_training_session);
        parcel.writeString(name);
        parcel.writeString(session_date);
        parcel.writeString(trainer_notes);
        parcel.writeInt(athlete_id);
    }
}
