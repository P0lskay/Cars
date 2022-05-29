import java.util.HashMap;
import java.util.HashSet;

public class Color {
    private String color_name;
    private HashMap<String, Integer> marks = new HashMap<String, Integer>();

    public Color(String color){
        color_name = color;
    }

    public void addMark(String mark){
        if(marks.containsKey(mark)){

        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        if (this == obj) return true;
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Color otherObj = (Color)obj;
        return this.color_name == otherObj.color_name;
    }
}
