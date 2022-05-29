
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {

        Map<String, Integer> sum_price_mark = new HashMap<String, Integer>();
        Map<String, Integer> min_price_mark = new HashMap<String, Integer>();
        Map<String, Integer> max_price_mark = new HashMap<String, Integer>();
        Map<String, Integer> avg_price_mark = new HashMap<String, Integer>();
        Map<String, Integer> counter_car_mark = new HashMap<String, Integer>();


        Map<Integer, Integer> id_price = new HashMap<Integer, Integer>();
        Map<Integer, String> id_mark = new HashMap<Integer, String>();
        Map<Integer, String> id_color = new HashMap<Integer, String>();
        Map<Integer, Integer> id_year = new HashMap<Integer, Integer>();
        Map<Integer, Double> id_miles = new HashMap<Integer, Double>();
        Map<Integer, String> id_quality = new HashMap<Integer, String>();

        ArrayList<Color> colors = new ArrayList<Color>();

        Map<String, Map<String, Integer>> color_mark_result = new HashMap<String, Map<String, Integer>>();


        Set<String> all_car_number = new HashSet<String>();
        Set<String> repeat_car_number = new HashSet<String>();


        Set<Integer> set_years = new TreeSet<Integer>();

        try{
            Scanner sc = new Scanner(new File("data_auto.txt"));
            sc.useLocale(Locale.ENGLISH);
            String str;
            while (sc.hasNext()) {
                int price = 0;
                int year = 0;
                int id = 0;
                double miles = 0;
                String color, quality, id_num, mark;
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
                        if(!sum_price_mark.containsKey(mark)){
                            sum_price_mark.put(mark, price);
                            min_price_mark.put(mark, price);
                            max_price_mark.put(mark, price);
                            counter_car_mark.put(mark, 1);
                        }else{
                            sum_price_mark.put(mark, sum_price_mark.get(mark) + price);
                            min_price_mark.put(mark, price < min_price_mark.get(mark) ? price : min_price_mark.get(mark));
                            max_price_mark.put(mark, price > max_price_mark.get(mark) ? price : max_price_mark.get(mark));
                            counter_car_mark.put(mark, counter_car_mark.get(mark)+1);
                        }
                        avg_price_mark.put(mark, sum_price_mark.get(mark)/counter_car_mark.get(mark));
                    }
                    else break;

                    //Читаем модель авто
                    if(sc.hasNext()) str = sc.nextLine();
                    else break;

                    //Читаем год авто
                    if (sc.hasNextInt())
                        year = Integer.parseInt(sc.nextLine());
                    else throw new IOException();

                    set_years.add(year);


                    //Читаем состояние авто
                    if(sc.hasNext())
                        quality = sc.nextLine();
                    else throw new IOException();

                    //Читаем пробег авто
                    if(sc.hasNextDouble())
                        miles = Double.parseDouble(sc.nextLine());
                    else throw new IOException();

                    //Читаем цвет авто
                    if(sc.hasNext()) {
                        color = sc.nextLine();
                        if(!colors.contains(new Color(color)))
                            colors.add(new Color(color));
                        colors.get(colors.indexOf(new Color(color))).addMark(mark);
                    }
                    else throw new IOException();

                    //Добавляем идентификационны номер машины
                    if(sc.hasNext()) {
                        id_num = sc.nextLine();
                        if(!all_car_number.add(id_num))
                            repeat_car_number.add(id_num);
                    }
                    else throw new IOException();


//                    miles_car.put(year, miles_car.get(year)+miles);

                    id_price.put(id, price);
                    id_color.put(id, color);
                    id_mark.put(id, mark);
                    id_miles.put(id, miles);
                    id_quality.put(id, quality);
                    id_year.put(id, year);


                }
                else throw new IOException();
                //Читаем партию авто
                if(sc.hasNext()) str = sc.nextLine();
                else break;
            }
            sc.close();
        }catch (IOException e) {
            System.out.println("file error");
        }
        try {
            FileWriter writer = new FileWriter("output.txt", false);

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