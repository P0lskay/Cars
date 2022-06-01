import java.util.Comparator;

public class Mark {
    String mark;
    int count;
    int sum;
    int avg;
    int max_price;
    int min_price;

    public int getMax_price() {
        return max_price;
    }

    public int getMin_price() {
        return min_price;
    }

    public String getMark() {
        return mark;
    }
    public int getAvg() {
        return avg;
    }


    Mark(String mark, int cost){
        this.mark = mark;
        count = 1;
        sum = max_price = min_price = avg = cost;
    }

    void addOne(int cost){
        count++;
        sum += cost;
        avg = cost / sum;
        if(cost > max_price) max_price = cost;
        if(cost < min_price) min_price = cost;
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
