public class Car {
    private String mark, model, color, status, idNum;
    private int cost;
    private double mileage;

    Car(String mark, String model, String color, String status, String idNum, int cost, double mileage){
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.cost = cost;
        this.status = status;
        this.mileage = mileage;
        this.idNum = idNum;
    }

    //Машины должны сравниваться по id, так как остальные параметры, кроме марки и модели изменяемы
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        if (this == obj) return true;
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Car otherObj = (Car)obj;
        return this.idNum == otherObj.idNum;
    }
}
