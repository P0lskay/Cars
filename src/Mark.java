public class Mark {
    String mark;
    int count, sum;

    Mark(String mark, int cost){
        this.mark = mark;
        sum = cost;
        count = 1;
    }

    void addOne(int cost){
        count++;
        sum += cost;
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
}
