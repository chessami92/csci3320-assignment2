import java.util.Random;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class TestEfficiency {
    private static Random random = new Random();

    public static void main(String[] args) {

    }

    static int[] randomIntegersNoRepeats(int length) {
        int[] selectionSet = createIntegerList(-length, length);

        int[] integers = new int[length];

        for (int i = length; i > 0; --i) {
            for(int j = 0; j < selectionSet.length; ++j)
                System.out.print(selectionSet[j] + " ");
            System.out.println();

            int numberOfElements = length + i + 1;
            System.out.println(numberOfElements);

            int randomNumber = random.nextInt(numberOfElements);
            System.out.println("Random: " + randomNumber);
            integers[length - i] = selectionSet[randomNumber];

            selectionSet[randomNumber] = selectionSet[numberOfElements - 1];
        }

        return integers;
    }

    static int[] createIntegerList(int lowerBound, int upperBound) {
        int[] list = new int[upperBound - lowerBound + 1];

        for (int i = lowerBound; i <= upperBound; ++i)
            list[i - lowerBound] = i;

        return list;
    }
}
