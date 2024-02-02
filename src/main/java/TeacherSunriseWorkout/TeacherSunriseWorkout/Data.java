package TeacherSunriseWorkout.TeacherSunriseWorkout;
public class Data {
    private String exercisename;
    private String duration;
    private String status;
    private String date;
    private String username;
    // Constructor for the Data class
    public Data(String exercisename, String duration, String username) {
    	 this.exercisename = exercisename;
         this.duration = duration;
         this.username = username;
    }
    public Data() {
   	 
   }
    // Constructor
    public Data(String exercisename, String duration, String status, String date, String username) {
        this.exercisename = exercisename;
        this.duration = duration;
        this.status = status;
        this.date = date;
        this.username = username;
    }

    // Getter methods
    public String getExercisename() {
        return exercisename;
    }

    public String getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    // Setter methods
    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
