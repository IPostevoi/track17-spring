package track.lessons.lesson1;

import java.io.File;
import java.io.*;


/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */
public class CountWords {


    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */
    public long countNumbers(File file) throws Exception {
        String dirPath = file.getAbsolutePath();
        BufferedReader br = new BufferedReader( new FileReader(dirPath));
        String line;
        long sum = 0;
        while ((line = br.readLine()) != null) {
            if (isNumeric(line)) {
                sum += Integer.parseInt(line);
            }
        }
        return sum;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {

        String dirPath = file.getAbsolutePath();
        BufferedReader br = new BufferedReader(new FileReader(dirPath));
        String line;
        StringBuilder string = new StringBuilder();

        while ((line = br.readLine()) != null) {

            if (!isNumeric(line) && !line.isEmpty()) {
                string.append(line);
                string.append(" ");
            }
        }
        br.close();

        return string.toString().trim();
    }
}

