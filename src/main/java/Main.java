import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger COUNTER_ON_THREE = new AtomicInteger(0);
    private static final AtomicInteger COUNTER_ON_FOUR = new AtomicInteger(0);
    private static final AtomicInteger COUNTER_ON_FIVE = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            COUNTER_ON_THREE.getAndAdd(findCountOfPalidrome(genShortWords(), 3));
            COUNTER_ON_FOUR.getAndAdd(findCountOfPalidrome(genShortWords(), 4));
            COUNTER_ON_FIVE.getAndAdd(findCountOfPalidrome(genShortWords(), 5));
        });

        Thread thread2 = new Thread(() -> {
            COUNTER_ON_THREE.getAndAdd(findCountWordWithDublicateLetters(genShortWords(), 3));
            COUNTER_ON_FOUR.getAndAdd(findCountWordWithDublicateLetters(genShortWords(), 4));
            COUNTER_ON_FIVE.getAndAdd(findCountWordWithDublicateLetters(genShortWords(), 5));

        });

        Thread thread3 = new Thread(() -> {
            COUNTER_ON_THREE.getAndAdd(findCountWordWithAscOrder(genShortWords(), 3));
            COUNTER_ON_FOUR.getAndAdd(findCountWordWithAscOrder(genShortWords(), 4));
            COUNTER_ON_FIVE.getAndAdd(findCountWordWithAscOrder(genShortWords(), 5));
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.format("Красивых слов с длиной 3: %d шт\n", COUNTER_ON_THREE.get());
        System.out.format("Красивых слов с длиной 4: %d шт\n", COUNTER_ON_FOUR.get());
        System.out.format("Красивых слов с длиной 5: %d шт\n", COUNTER_ON_FIVE.get());
    }

    /**
     * Генератор случайного текста
     *
     * @param letters - набор букв
     * @param length  - длина текста
     * @return сгенерированный текст
     */
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    /**
     * Генерирует 100 000 коротких слов
     *
     * @return массив коротких слов
     */
    private static String[] genShortWords() {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    /**
     * Находит в массиве строк количество слов заданной длины, являющихся палиндромом
     *
     * @param texts  - массив строк
     * @param length - длина слова
     * @return количество слов заданной длины, являющихся палинромом
     */
    private static int findCountOfPalidrome(String[] texts, int length) {
        int count = 0;
        for (String word : texts) {
            int n = word.length();
            if (n == length) {
                for (int i = 0; i < n / 2; i++) {
                    if (word.charAt(i) == word.charAt(n - i - 1)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Находит в массиве строк количество слов заданной длины, с монобуквами
     *
     * @param texts  - массив строк
     * @param length - длина слова
     * @return количество слов с монобуквами и заданной длины
     */
    private static int findCountWordWithDublicateLetters(String[] texts, int length) {
        int count = 0;
        for (String word : texts) {
            if (word.length() == length) {
                for (char Char = 'a'; Char <= 'z'; Char++) {
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) != Char) {
                            break;
                        } else {
                            if (i == word.length() - 1) {
                                count++;
                            }
                        }
                    }

                }
            }
        }
        return count;
    }

    /**
     * Находит в массиве строк колчество слов заданной длины, с порядком букв следующих по возрастанию
     *
     * @param texts  - массив строк
     * @param length - длина слова
     * @return колчество слов заданной длины, с порядком букв следующих по возрастанию
     */
    private static int findCountWordWithAscOrder(String[] texts, int length) {
        int count = 0;
        for (String word : texts) {
            if (word.length() == length) {
                for (int i = 0; i < word.length() - 1; i++) {
                    if (word.charAt(i) <= word.charAt(i + 1)) {
                        if (i == word.length() - 2) {
                            count++;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return count;
    }
}
