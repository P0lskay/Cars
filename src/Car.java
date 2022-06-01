import java.util.Comparator;

public class Car {
    private String mark, model, color, quality, idNum;
    private int cost, year;
    private double mileage;

    Car(String mark, String model, String color, String quality, String idNum, int cost, double mileage, int year){
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.cost = cost;
        this.quality = quality;
        this.mileage = mileage;
        this.idNum = idNum;
        this.year = year;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getQuality() {
        return quality;
    }

    public String getIdNum() {
        return idNum;
    }

    public int getCost() {
        return cost;
    }

    public double getMileage() {
        return mileage;
    }

    public int getYear() {
        return year;
    }

    //Машины должны сравниваться по id, так как остальные параметры, кроме марки и модели изменяемы
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Car otherObj = (Car)obj;
        return this.idNum.equals(otherObj.idNum);
    }

    public static final Comparator<Car> PRICE_COMPARATOR = new Comparator<Car>() {
        @Override
        public int compare(Car o1, Car o2) {
            return o1.cost - o2.cost;
        }
    };

    public static final Comparator<Car> MILEAGE_COMPARATOR = new Comparator<Car>(){
        @Override
        public int compare(Car o1, Car o2) {
            return (int) (o1.mileage - o2.mileage);
        }
    };
}
