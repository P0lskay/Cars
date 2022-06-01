
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {

        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Car> cars = new ArrayList<Car>();
        ArrayList<Mark> marks = new ArrayList<Mark>();

        Set<String> repeat_car_number = new HashSet<String>();


        Set<Integer> set_years = new TreeSet<Integer>();


        //БЛОК №1 - считывание и сохранение информации
        try{
            Scanner sc = new Scanner(new File("data_auto.txt"));
            sc.useLocale(Locale.ENGLISH);
            while (sc.hasNext()) {
                int price = 0;
                int year = 0;
                int id = 0;
                double miles = 0;
                String color = "", quality = "", idNum = "", mark = "", model = "", id_party = "";
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

                    set_years.add(year);


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
                        colors.get(colors.indexOf(new Color(color))).addMark(mark);
                    }
                    else break;

                    Car car = new Car(mark, model, color, quality, idNum, price, miles);
                    //Если машина с таким же id уже добавлена, значит этот id повторяется и его нужно вывести
                    if(cars.contains(car))
                        repeat_car_number.add(idNum);
                    cars.add(car);

                }
                else throw new IOException();
                //Читаем партию авто
                if(sc.hasNext()) id_party = sc.nextLine();
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
            if(repeat_car_number.size() == 0) {
                System.out.println("Повторяющиеся id автомобилей не найдены!");
                writer.write("Повторяющиеся id автомобилей не найдены!\n");
            }else {
                System.out.println("Список повторяющихся id среди автомобилей:");
                writer.write("Список повторяющихся id среди автомобилей:\n");
                for(String i : repeat_car_number){
                    System.out.println(i);
                    writer.write(i);
                    writer.write("\n");
                }
            }

            System.out.println("\nМарки авто, отсортированные по цене:");
            writer.write("\nМарки авто, отсортированные по цене:");
            Map<String, Integer> sorted_avg_price_mark = avg_price_mark.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> { throw new AssertionError(); },
                            LinkedHashMap::new
                    ));
            String leftAlignFormat = "| %-15s | %-10d |%n";
            for (Map.Entry<String, Integer> entry : sorted_avg_price_mark.entrySet()) {
                System.out.format(leftAlignFormat, entry.getKey(), entry.getValue());
                writer.write(String.format(leftAlignFormat, entry.getKey(), entry.getValue()));
            }


            System.out.println("\nГистограммы всех цветов встречащихся авто:");
            for (Map.Entry<String, Map<String, Integer>> entry : color_mark_result.entrySet()) {
                System.out.println("Цвет: "+ entry.getKey() +":");
                leftAlignFormat = "|%-12s";
                int max = 0;
                for(Map.Entry<String, Integer> entry2: entry.getValue().entrySet())
                {
                    if(entry2.getValue() > max)
                        max = entry2.getValue();
                }
                for(int i = max; i > 0; i--)
                {
                    System.out.format("%-2d", i);
                    for(Map.Entry<String, Integer> entry2: entry.getValue().entrySet())
                    {
                        if (entry2.getValue() >= i){
                            System.out.format(leftAlignFormat, "#");
                        }else{
                            System.out.format(leftAlignFormat, " ");
                        }
                    }
                    System.out.format("\n");
                }
                for(Map.Entry<String, Integer> entry2: entry.getValue().entrySet())
                {
                    System.out.format("_____________");
                }
                System.out.format("\n  ");
                for(Map.Entry<String, Integer> entry2: entry.getValue().entrySet())
                {
                    System.out.format(leftAlignFormat, entry2.getKey());
                }
                System.out.format("\n\n");
            }


            leftAlignFormat = "| %-15s | %-15d | %-15d | %-15d |%n";
            System.out.println("Таблица с марками авто и их макс ценой, мин ценой и т.д.:");
            writer.write("\n\n");
            writer.write("Таблица с марками авто и их макс ценой, мин ценой и т.д.:\n");
            System.out.format("| %-15s | %-15s | %-15s | %-15s |","Марка", "Средння цена", "Максимальная", "Минимальная");
            writer.write(String.format("| %-15s | %-15s | %-15s | %-15s |","Марка", "Средння цена", "Максимальная", "Минимальная"));
            writer.write("\n");
            System.out.format("\n");
            for (Map.Entry<String, Integer> entry : sorted_avg_price_mark.entrySet()) {
                System.out.format(leftAlignFormat, entry.getKey(), entry.getValue(), max_price_mark.get(entry.getKey()), min_price_mark.get(entry.getKey()));
                writer.write(String.format(leftAlignFormat, entry.getKey(), entry.getValue(), max_price_mark.get(entry.getKey()), min_price_mark.get(entry.getKey())));

            }

            String years  = "";
            String colors = "";
            String mark = "";
            String quantity = "";
            int sort_choice = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Максимальный возраст авто: ");
            years = scanner.nextLine();
            System.out.println("Цвета авто: ");
            colors = scanner.nextLine();
            System.out.println("Марка авто: ");
            mark = scanner.nextLine();
            System.out.println("Состояние авто: ");
            quantity = scanner.nextLine();

            System.out.println("Выберите сортировку: \n1-По возрастанию цены\n2-По убыванию цены\n3-По возрастанию пробега\n4-По убыванию пробега");
            sort_choice = scanner.nextInt();



            leftAlignFormat = "| %-20s | %-4d | %-5d | %-10s | %-20s | %-10d | %-10f |%n";
            System.out.format("| %-20s | %-4s | %-5s | %-10s | %-20s | %-10s | %-10s |%n", "Марка", "ID", "Год", "Цвет", "Состояние", "Цена", "Пробег");
            writer.write(String.format("%n| %-20s | %-4s | %-5s | %-10s | %-20s | %-10s | %-10s |%n", "Марка", "ID", "Год", "Цвет", "Состояние", "Цена", "Пробег"));
            if(sort_choice == 1)
            {
                Map<Integer, Integer> sorted_price = id_price.entrySet().stream()
                        .sorted(Comparator.comparingInt(e -> e.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> { throw new AssertionError(); },
                                LinkedHashMap::new
                        ));
                for(Map.Entry<Integer, Integer> entry :sorted_price.entrySet()){
                    int key = entry.getKey();
                    if((years == "" || 2020 - id_year.get(key) < Integer.parseInt(years)) &&
                            (colors == "" || Arrays.stream(colors.split(" ")).toList().equals(id_color.get(key))) &&
                            (mark == "" || mark.equals(id_mark.get(key))) &&
                            (quantity == "" || quantity.equals(id_quality.get(key)))){
                        System.out.format(leftAlignFormat, id_mark.get(key), key, id_year.get(key), id_color.get(key),
                                id_quality.get(key), id_price.get(key), id_miles.get(key));
                        writer.write(String.format(leftAlignFormat, id_mark.get(key), key, id_year.get(key), id_color.get(key),
                                id_quality.get(key), id_price.get(key), id_miles.get(key)));
                    }
                }

            }
            else if(sort_choice == 2)
            {
                Map<Integer, Integer> sorted_price = id_price.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> { throw new AssertionError(); },
                            LinkedHashMap::new
                    ));
                for(Map.Entry<Integer, Integer> entry :sorted_price.entrySet()){
                    int key = entry.getKey();
                    if((years == "" || 2020 - id_year.get(key) < Integer.parseInt(years)) &&
                            (colors == "" || Arrays.stream(colors.split(" ")).toList().equals(id_color.get(key))) &&
                            (mark == "" || mark == id_mark.get(key)) &&
                            (quantity == "" || quantity == id_quality.get(key) )){
                        System.out.format(leftAlignFormat, id_mark.get(key), key, id_year.get(key), id_color.get(key),
                                id_quality.get(key), id_price.get(key), id_miles.get(key));
                    }
                }
            }else if(sort_choice == 3)
            {
                Map<Integer, Double> sorted_price = id_miles.entrySet().stream()
                        .sorted(Comparator.comparingDouble(e -> e.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> { throw new AssertionError(); },
                                LinkedHashMap::new
                        ));
                for(Map.Entry<Integer, Double> entry :sorted_price.entrySet()){
                    int key = entry.getKey();
                    if((years == "" || 2020 - id_year.get(key) < Integer.parseInt(years)) &&
                            (colors == "" || Arrays.stream(colors.split(" ")).toList().equals(id_color.get(key))) &&
                            (mark == "" || mark == id_mark.get(key)) &&
                            (quantity == "" || quantity == id_quality.get(key) )){
                        System.out.format(leftAlignFormat, id_mark.get(key), key, id_year.get(key), id_color.get(key),
                                id_quality.get(key), id_price.get(key), id_miles.get(key));
                    }
                }
            }else if(sort_choice == 2)
            {
                Map<Integer, Double> sorted_price = id_miles.entrySet().stream()
                        .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> { throw new AssertionError(); },
                                LinkedHashMap::new
                        ));
                for(Map.Entry<Integer, Double> entry :sorted_price.entrySet()){
                    int key = entry.getKey();
                    if((years == "" || 2020 - id_year.get(key) < Integer.parseInt(years)) &&
                            (colors == "" || Arrays.stream(colors.split(" ")).toList().equals(id_color.get(key))) &&
                            (mark == "" || mark == id_mark.get(key)) &&
                            (quantity == "" || quantity == id_quality.get(key) )){
                        System.out.format(leftAlignFormat, id_mark.get(key), key, id_year.get(key), id_color.get(key),
                                id_quality.get(key), id_price.get(key), id_miles.get(key));
                    }
                }
            }


//            String leftAlignFormat = "| %-4d | %-10d | %-10d | %-10d | %-10s | %-10d | %-10d | %-10f |%n";
//            System.out.format("+------+------------+------------+------------+------------+------------+------------+------------+%n");
//            System.out.format("| Year | Avg. price | min price  | min. price | max. color | clean veh. |salvage ins.|    km.     |%n");
//            System.out.format("+------+------------+------------+------------+------------+------------+------------+------------+%n");
//            writer.write("+------+------------+------------+------------+------------+------------+------------+------------+\n");
//            writer.write("| Year | Avg. price | min price  | min. price | max. color | clean veh. |salvage ins.|    km.     |\n");
//            writer.write("+------+------------+------------+------------+------------+------------+------------+------------+\n");

//            for (Integer year : set_years) {
//                Map<String, Integer> allColorsYear = color_result.get(year);
//                String color = Collections.max(allColorsYear.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
//                System.out.format(leftAlignFormat, year, sum_price.get(year) / counter_car.get(year), min_price.get(year), max_price.get(year),
//                        color, clean_vehicle.get(year), salvage_insurance.get(year), miles_car.get(year) / counter_car.get(year) * 1.61);
//                writer.write(String.format(leftAlignFormat, year, sum_price.get(year) / counter_car.get(year), min_price.get(year), max_price.get(year),
//                        color, clean_vehicle.get(year), salvage_insurance.get(year), miles_car.get(year) / counter_car.get(year) * 1.61));
//            }

            writer.flush();
        }catch (IOException e) {
            System.out.println("file error");
        }
    }

}