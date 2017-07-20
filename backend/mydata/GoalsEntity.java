package mydata;

/**
 * Created by user on 2017-07-02.
 */
public class GoalsEntity {
    private int id;
    private String goal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalsEntity that = (GoalsEntity) o;

        if (id != that.id) return false;
        if (goal != null ? !goal.equals(that.goal) : that.goal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        return result;
    }
}
