package lesson1;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        try (BufferedReader inFile = new BufferedReader(new FileReader(inputName));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(outputName))) {
            String str = "";
            ArrayList<Integer> AMList = new ArrayList<>();
            ArrayList<Integer> PMList = new ArrayList<>();

            while ((str = inFile.readLine()) != null) {
                boolean found = Pattern.matches("(0\\d|1[0-2]):([0-5]\\d):([0-5]\\d):(PM|AM)", str);
                if (found) {
                    StringBuilder strBuilder = new StringBuilder();
                    for (String part: str.split(":")) {
                        if (part.equals("AM")) {
                            AMList.add(Integer.valueOf(strBuilder.toString()));
                        } else if (part.equals("PM")) {
                            PMList.add(Integer.valueOf(strBuilder.toString()));
                        } else {
                            strBuilder.append(part);
                        }
                    }
                } else throw new IOException();
            }

            int[] AMArray = AMList.stream().mapToInt(i -> i).toArray();
            int[] PMArray = PMList.stream().mapToInt(i -> i).toArray();
            Sorts.quickSort(AMArray);
            Sorts.quickSort(PMArray);

            for (int element: AMArray) {
                StringBuilder strBuilder = new StringBuilder();
                if (element/10000 > 9) strBuilder.append(element/10000);
                else strBuilder.append("0").append(element / 10000);
                strBuilder.append(':');
                if (element % 10000 / 100 > 9) strBuilder.append(element % 10000 / 100);
                else strBuilder.append("0").append(element / 10000);
                strBuilder.append(':');
                if (element % 100 > 9) strBuilder.append(element % 100);
                else strBuilder.append("0").append(element % 100);
                strBuilder.append(':').append("AM\n");
                outFile.write(strBuilder.toString());
            }

            for (int element: PMArray) {
                StringBuilder strBuilder = new StringBuilder();
                if (element/10000 > 9) strBuilder.append(element/10000);
                else strBuilder.append("0").append(element / 10000);
                strBuilder.append(':');
                if (element % 10000 / 100 > 9) strBuilder.append(element % 10000 / 100);
                else strBuilder.append("0").append(element / 10000);
                strBuilder.append(':');
                if (element % 100 > 9) strBuilder.append(element % 100);
                else strBuilder.append("0").append(element % 100);
                strBuilder.append(':').append("PM\n");
                outFile.write(strBuilder.toString());
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //Сложность O(N), N - количество чисел
    //Память O(1), всегда фиксированная
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        double[] temperatures = new double[7731];
        try (BufferedReader inFile = new BufferedReader(new FileReader(inputName));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(outputName))) {

            String str = "";

            while ((str = inFile.readLine()) != null) {
                if (Pattern.matches("-?[0-9]{1,3}\\.\\d", str))
                    temperatures[(int) (Double.parseDouble(str) * 10) + 2730]++;
                else throw new IOException();
            }

            for (int i = 0; i < temperatures.length; i++) {
                if (temperatures[i] > 0) {

                    for (int j = 0; j < temperatures[i]; j++) {
                        double number = (double) (i - 2730) / 10.0;
                        outFile.write(String.valueOf(number));
                        outFile.newLine();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    //Сложность O(N), N - количество чисел
    //Память O(N)
    static public void sortSequence(String inputName, String outputName) throws IOException {
        HashMap<Integer, Integer> answerMap = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        try (BufferedReader inFile = new BufferedReader(new FileReader(inputName));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(outputName))) {

            String str = "";
            int k = 0;

            while ((str = inFile.readLine()) != null) {
                k++;
                if (Pattern.matches("[0-9]*", str)) {
                    Integer number = Integer.valueOf(str);
                    list.add(number);
                    if (!answerMap.containsKey(number)) {
                        answerMap.put(Integer.valueOf(str), 1);
                    } else answerMap.replace(number, answerMap.get(number) + 1);
                }
                else throw new IOException();
            }

            Integer max = 0;
            Integer maxNumber = 0;
            for (Map.Entry<Integer, Integer> entry : answerMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                if (value > max) {
                    max = value;
                    maxNumber = key;
                } else if (value.equals(max) && maxNumber > key) {
                    max = value;
                    maxNumber = key;
                }
            }

            for (Integer element: list) {
                if (!element.equals(maxNumber)) {
                    outFile.write(element.toString());
                    outFile.newLine();
                }
            }

            for (int i = 0; i < max; i++) {
                outFile.write(maxNumber.toString());
                outFile.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
