
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Car> cars = new ArrayList<Car>();
        ArrayList<Mark> marks = new ArrayList<Mark>();

        Set<String> repeatCarNumber = new HashSet<String>();

        //БЛОК №1 - считывание и сохранение информации
        try{
            Scanner sc = new Scanner(new File("data_auto.txt"));
            sc.useLocale(Locale.ENGLISH);
            while (sc.hasNext()) {
                int price = 0;
                int year = 0;
                int id = 0;
                double miles = 0;
                String color = "", quality = "", idNum = "", mark = "", model = "", idParty = "";
                //Читаем индекс авто
                if(sc.hasNextInt())
                    id = Integer.parseInt(sc.nextLine());
                else throw new IOException();

                if(sc.hasNextInt()) {
                    //Читаем цену авто
                    price = Integer.parseInt(sc.nextLine());

                    //Читаем марку авто
                    if(sc.hasNext()){
                        mark = sc.nextLine();
                        if(marks.contains(new Mark(mark, price))){
                            marks.get(marks.indexOf(new Mark(mark, price))).addOne(price);
                        }else{
                            marks.add(new Mark(mark, price));
                        }
                    }
                    else break;

                    //Читаем модель авто
                    if(sc.hasNext())
                        model = sc.nextLine();
                    else break;

                    //Читаем год авто
                    if (sc.hasNextInt())
                        year = Integer.parseInt(sc.nextLine());
                    else break;

                    //Читаем состояние авто
                    if(sc.hasNext())
                        quality = sc.nextLine();
                    else break;

                    //Читаем пробег авто
                    if(sc.hasNextDouble())
                        miles = Double.parseDouble(sc.nextLine());
                    else break;

                    //Читаем цвет авто
                    if(sc.hasNext()) {
                        color = sc.nextLine();
                        if(!colors.contains(new Color(color)))
                            colors.add(new Color(color));
                        int index = colors.indexOf(new Color(color));
                        Color newColor = colors.get(index);

                        newColor.addMark(mark);
                        colors.set(index, newColor);
                    }
                    else break;

                    if(sc.hasNext())
                        idNum = sc.nextLine();
                    else break;

                    Car car = new Car(mark, model, color, quality, idNum, price, miles, year);
                    //Если машина с таким же id уже добавлена, значит этот id повторяется и его нужно вывести
                    if(cars.contains(car))
                        repeatCarNumber.add(idNum);
                    cars.add(car);

                }
                else throw new IOException();
                //Читаем партию авто
                if(sc.hasNext()) idParty = sc.nextLine();
                else break;
            }
            sc.close();
        }catch (IOException e) {
            System.out.println("file input error");
        }

        //БЛОК №2 - обработка и вывод информации
        try {
            FileWriter writer = new FileWriter("output.txt", false);


            //Задание №1 - вывод повторяющихся id авто
            if(repeatCarNumber.size() == 0) {
                System.out.println("Повторяющиеся id автомобилей не найдены!");
                writer.write("Повторяющиеся id автомобилей не найдены!\n");
            }else {
                System.out.println("Список повторяющихся id среди автомобилей:");
                writer.write("Список повторяющихся id среди автомобилей:\n");
                for(String i : repeatCarNumber){
                    System.out.println(i);
                    writer.write(i);
                    writer.write("\n");
                }
            }


            //Задание №2 - вывод гистограмм для каждого цвета
            System.out.println("\nГистограммы всех цветов встречащихся авто:");
            for (Color color : colors) {
                System.out.println("Цвет: "+ color.getColor_name() +":");
                String leftAlignFormat = "|%-12s";
                //Находим будущий самый высокий столбец на гистограмме для корректного отображения
                int maxColumnSize = 0;
                for(int count: color.getMarks().values())
                {
                    if(count > maxColumnSize)
                        maxColumnSize = count;
                }

                //Теперь построчно выводим гистограмму на консоль
                for(int i = maxColumnSize; i > 0; i--)
                {
                    System.out.format("%-2d", i);
                    for(int count: color.getMarks().values())
                    {
                        if (count >= i){
                            System.out.format(leftAlignFormat, "#");
                        }else{
                            System.out.format(leftAlignFormat, " ");
                        }
                    }
                    System.out.format("\n");
                }
                for(int count: color.getMarks().values())
                {
                    System.out.format("_____________");
                }
                System.out.format("\n  ");
                for(Map.Entry<String, Integer> entry: color.getMarks().entrySet())
                {
                    System.out.format(leftAlignFormat, entry.getKey());
                }
                System.out.format("\n\n");
            }

            //Задание №3 - вывод марок авто отсортированных по средней цене
            System.out.println("\nМарки авто, отсортированные по цене:");
            writer.write("\nМарки авто, отсортированные по цене:");
            Collections.sort(marks, Mark.MARK_COMPARATOR);
            String AlignFormat = "| %-15s | %-10d |%n";
            for (Mark m : marks) {
                System.out.format(AlignFormat, m.getMark(), m.getAvg());
                writer.write(String.format(AlignFormat, m.getMark(), m.getAvg()));
            }

            //Задание №4 - вывод марок авто с их средней ценой, макс ценой, мин ценой
            AlignFormat = "| %-15s | %-15d | %-15d | %-15d |%n";
            System.out.println("Таблица с марками авто и их макс ценой, мин ценой и т.д.:");
            writer.write("\n\n");
            writer.write("Таблица с марками авто и их макс ценой, мин ценой и т.д.:\n");
            System.out.format("| %-15s | %-15s | %-15s | %-15s |","Марка", "Средння цена", "Максимальная", "Минимальная");
            writer.write(String.format("| %-15s | %-15s | %-15s | %-15s |","Марка", "Средння цена", "Максимальная", "Минимальная"));
            writer.write("\n");
            System.out.format("\n");
            for (Mark m : marks) {
                System.out.format(AlignFormat, m.getMark(), m.getAvg(), m.getMax_price(), m.getMin_price());
                writer.write(String.format(AlignFormat, m.getMark(), m.getAvg(), m.getMax_price(), m.getMin_price()));

            }

            //Задание №5 - вывод тех авто, которые запросит пользователь
            int years  = 2022;
            String color = "";
            String mark = "";
            String quality = "";
            int sortChoice = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Максимальный возраст авто: ");
            years = scanner.nextInt();
            System.out.println("Цвета авто: ");
            color = scanner.nextLine();
            System.out.println("Марка авто: ");
            mark = scanner.nextLine();
            System.out.println("Состояние авто: ");
            quality = scanner.nextLine();

            System.out.println("Выберите сортировку: \n1-По возрастанию цены\n2-По убыванию цены\n3-По возрастанию пробега\n4-По убыванию пробега");
            sortChoice = scanner.nextInt();

            AlignFormat = "| %-20s | %-14s | %-5d | %-20s | %-20s | %-10d | %-10f |%n";
            System.out.format("| %-20s | %-14s | %-5s | %-20s | %-20s | %-10s | %-10s |%n", "Марка", "ID", "Год", "Цвет", "Состояние", "Цена", "Пробег");
            writer.write(String.format("%n| %-20s | %-14s | %-5s | %-20s | %-20s | %-10s | %-10s |%n", "Марка", "ID", "Год", "Цвет", "Состояние", "Цена", "Пробег"));
            //В зависимости от выбранной сортировки сортируем список машин в нужном порядке
            if(sortChoice == 1)
            {
                Collections.sort(cars, Car.PRICE_COMPARATOR);
            }
            else if(sortChoice == 2)
            {
                Collections.sort(cars, Collections.reverseOrder(Car.PRICE_COMPARATOR));
            }else if(sortChoice == 3)
            {
                Collections.sort(cars, Car.MILEAGE_COMPARATOR);
            }else if(sortChoice == 4)
            {
                Collections.sort(cars, Collections.reverseOrder(Car.MILEAGE_COMPARATOR));
            }
            //Выводим все машины, которые удовлетворяют условиям
            for(Car car : cars){
                if((years == 2022 || 2022 - car.getYear() < years) &&
                        (color == "" || Arrays.stream(color.split(" ")).toList().equals(car.getColor()) &&
                                (mark == "" || mark.equals(car.getMark())) &&
                                (quality == "" || quality.equals(car.getQuality())))){
                    System.out.format(AlignFormat, car.getMark(), car.getIdNum(), car.getYear(), car.getColor(),
                            car.getQuality(), car.getCost(), car.getMileage());
                    writer.write(String.format(AlignFormat, car.getMark(), car.getIdNum(), car.getYear(), car.getColor(),
                            car.getColor(), car.getCost(), car.getMileage()));
                }
            }

            writer.flush();
        }catch (IOException e) {
            System.out.println("file error");
        }
    }

}