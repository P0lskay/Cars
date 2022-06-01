import java.util.Comparator;

public class Mark {
    String mark;
    int count, sum, avg;


    public String getMark() {
        return mark;
    }
    public int getAvg() {
        return avg;
    }


    Mark(String mark, int cost){
        this.mark = mark;
        sum = cost;
        count = 1;
        avg = cost;
    }

    void addOne(int cost){
        count++;
        sum += cost;
        avg = cost / sum;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if(other == null)
            return false;
        if(this.getClass() != other.getClass())
            return false;
        Mark otherMark = (Mark) other;
        return this.mark == otherMark.mark;
    }

    public static final Comparator<Mark> MARK_COMPARATOR = new Comparator<Mark>() {
        @Override
        public int compare(Mark o1, Mark o2) {
            return o1.avg - o2.avg;
        }
    };
}
