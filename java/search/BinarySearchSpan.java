package search;

public class BinarySearchSpan {
    // Pre:  array.length >= 0 && array[i] >= array[i + 1] для любого i из [0; array.length - 1)
    //
    // Post: R = минимальное i такое, что при вставке на i-ю позицию числа toFind
    //       для любого i из [0; array.length] будет всё ещё выполняться array[i] >= array[i + 1]
    public static int iterativeFind(int[] array, int toFind) {
        // array.length >= 0 && array[i] >= array[i + 1] для любого i из [0; array.length - 1)
        int left = 0;
        // left == 0 && array.length >= 0 && array[i] >= array[i + 1] для любого i из [0; array.length - 1)
        int right = array.length;
        // 0 <= left && left <= right && right <= array.length &&
        // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
        // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right]
        while (left < right) {
            // 0 <= left && left < right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right]
            int middle = (left + right) / 2;
            // 0 <= left && left < right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1)
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
            // ___left - 1 < middle && middle < right___
            if (array[middle] > toFind) {
                // 0 <= left && left < right && right <= array.length &&
                // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
                // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
                // left - 1 < middle && middle < right && ___array[middle] >= toFind___
                left = middle + 1;
                // 0 <= left && left <= right && right <= array.length &&
                // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
                // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
                // ___left - 1 >= middle___ && middle < right
                // left увеличилось
            } else {
                // 0 <= left && left < right && right <= array.length &&
                // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
                // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
                // left - 1 < middle && middle < right && ___array[middle] <= toFind___
                right = middle;
                // 0 <= left && left < right && right <= array.length &&
                // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
                // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
                // left - 1 < middle && middle >= right
                // right уменьшилось
            } // right - left уменьшилось => цикл конечный
            // 0 <= left && left <= right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right]
        }
        // left >= right && left == 0 || args[left - 1] >= toFind && right == array.length || toFind >= args[right]
        return right;
        // R = right
    }

    // Pre:  array.length >= 0 && array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
    //       0 <= left && left <= right && right <= array.length &&
    //       left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right]
    //
    // Post: R = минимальное i такое, что при вставке на i-ю позицию числа toFind
    //       для любого i из [0; array.length] будет всё ещё выполняться array[i] >= array[i + 1]
    public static int recursiveFind(int[] array, int toFind, int left, int right) {
        if (left >= right) {
            // left >= right && left == 0 || array[left - 1] >= toFind &&
            // right == array.length || toFind >= array[right]
            return right;
            // R = right
        } // else
        int middle = (left + right) / 2;
        // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
        // 0 <= left && left <= right && right <= array.length &&
        // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
        // ___left - 1 < middle && middle < right___
        if (array[middle] > toFind) {
            // 0 <= left && left < right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
            // left - 1 < middle && middle < right && ___array[middle] >= toFind___
            return recursiveFind(array, toFind, middle + 1, right);
            // 0 <= left && left <= right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
            // ___left - 1 >= middle___ && middle < right
            //
            // left увеличилось => right - left уменьшилось
            // R = recursiveFind(array, toFind, middle + 1, right)
        } else {
            // 0 <= left && left < right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
            // left - 1 < middle && middle < right && ___array[middle] <= toFind___
            return recursiveFind(array, toFind, left, middle);
            // 0 <= left && left < right && right <= array.length &&
            // array[i] >= array[i + 1] для любого i из [0; array.length - 1) &&
            // left == 0 || array[left - 1] >= toFind && right == array.length || toFind >= array[right] &&
            // left - 1 < middle && middle >= right
            //
            // right уменьшилось => right - left уменьшилось
            // R = recursiveFind(array, toFind, left, middle)
        }
    }

    // Pre:  array.length >= 0 && array[i] >= array[i + 1] для любого i из [0; array.length - 1)
    //
    // Post: R = минимальное i такое, что при вставке на i-ю позицию числа toFind
    //       для любого i из [0; array.length] будет всё ещё выполняться array[i] >= array[i + 1]
    public static int recursiveFind(int[] array, int toFind) {
        return recursiveFind(array, toFind, 0, array.length);
        // R = recursiveFind(array, toFind, 0, array.length)
    }

    // Pre:  args.length >= 0 && для любого i из [1; args.length - 1) выполняется
    //       args[i] >= args[i + 1] при сравнении числовых значений
    // Post: два числа в stdout:
    //       * Минимальное (i - 1) такое, что при вставке на i-ю позицию числа toFind
    //       для любого i из [1; args.length] будет всё ещё выполняться
    //       args[i] >= args[i + 1] при сравнении   их числовых значений
    //       * Количество чисел args[i] = toFind для i из [1; args.length)
    public static void main(String[] args) {
        int toFind = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            array[i - 1] = Integer.parseInt(args[i]);
        }
        // array.length >= 0 && для любого i из [0; array.length - 1) выполняется
        // array[i] >= array[i + 1] && toFind == args[0]
        int start = iterativeFind(array, toFind);
//        int start = recursiveFind(array, toFind);
        // array.length >= 0 && для любого i из [0; array.length - 1) выполняется
        // array[i] >= array[i + 1] && toFind == args[0] &&
        // start = минимальное i такое, что при вставке на i-ю позицию числа toFind
        // для любого i из [0; array.length] будет всё ещё выполняться array[i] >= array[i + 1]
        int end;
        if (toFind == Integer.MIN_VALUE) {
            // ---||--- && toFind меньше любого array[i] для i из [0; array.length)
            end = array.length;
            // ---||--- && end - минимальная позиция вставки числа (toFind - 1)
        } else {
            // ---||--- && toFind > INT_MIN
//            end = iterativeFind(array, toFind - 1);
            end = recursiveFind(array, toFind - 1);
            // ---||--- && end - позиция вставки числа (toFind - 1)
        }
        System.out.println(start + " " + (end - start));
        // start - минимальная позиция вставки числа toFind
        // (end - start) - длина отрезка из элементов toFind
    }
}
