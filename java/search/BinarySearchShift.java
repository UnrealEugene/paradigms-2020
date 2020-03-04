package search;

public class BinarySearchShift {
    // Pre:  array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
    //       из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0]
    // Post: R = j из предусловия
    public static int iterativeFind(int[] array) {
        int left = 0;
        int right = array.length - 1;
        // I: array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
        // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
        // 0 <= left && left < right && right < array.length && array[left] >= array[0] && array[right] < array[0]
        while (right - left > 1) {
            int middle = (left + right) / 2;
            // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
            // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
            // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
            // array[left] >= array[0] && array[right] < array[0]
            if (array[middle] >= array[0]) {
                // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
                // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
                // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
                // array[left] >= array[0] && array[right] < array[0] && array[middle] >= array[0]
                left = middle;
                // array[middle] >= array[0]  =>  left' = middle > left  =>  right' - left' < right - left

                // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
                // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
                // 0 <= left' && left' < right' && right' < array.length && array[left'] >= array[0] && array[right'] < array[0]
            } else {
                // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
                // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
                // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
                // array[left] >= array[0] && array[right] < array[0] && array[middle] < array[0]
                right = middle;
                // array[middle] < array[0]  =>  right' = middle < right  =>  right' - left' < right - left

                // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
                // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
                // 0 <= left' && left' < right' && right' < array.length && array[left'] >= array[0] && array[right'] < array[0]
            } // right - left уменьшилось => цикл конечный
            // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
            // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
            // 0 <= left && left < right && right < array.length && array[left] >= array[0] && array[right] < array[0]
        }
        // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
        // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
        // right - left <= 1 && array[left] >= array[0] && array[right] < array[0]
        return right;
        // R = right
    }

    // Pre:  array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
    //       из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
    //       0 <= left < right < array.length && array[left] >= array[0] > array[right]&&
    // Post: R = j из предусловия
    public static int recursiveFind(int[] array, int left, int right) {
        if (right - left <= 1) {
            // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
            // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
            // right - left <= 1 && array[left] >= array[0] && array[right] < array[0]
            return right;
            // R = right
        } // else

        int middle = (left + right) / 2;
        // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
        // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
        // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
        // array[left] >= array[0] && array[right] < array[0]
        if (array[middle] >= array[0]) {
            // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
            // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
            // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
            // array[left] >= array[0] && array[right] < array[0] && array[middle] >= array[0]

            // array[middle] >= array[0]  =>  left' = middle > left  =>  right' - left' < right - left
            return recursiveFind(array, middle, right);  // right - left уменьшилось => рекурсия конечная
            // R = recursiveFind(array, middle, right)
        } else {
            // array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
            // из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0] &&
            // 0 <= left && left < right && right < array.length && left < middle && middle < right &&
            // array[left] >= array[0] && array[right] < array[0] && array[middle] < array[0]

            // array[middle] < array[0]  =>  right' = middle < right  =>  right' - left' < right - left
            return recursiveFind(array, left, middle);  // right - left уменьшилось => рекурсия конечная
            // R = recursiveFind(array, left, middle)
        }
    }

    // Pre:  array.length > 0 && существует j из [1; array.length), что для любого i + 1 != j
    //       из [0; array.length - 1) array[i] < array[i + 1], а также array[array.length - 1] < array[0]
    // Post: R = j из предусловия
    public static int recursiveFind(int[] array) {
        return recursiveFind(array, 0, array.length - 1);
        // R = recursiveFind(array, 0, array.length - 1)
    }

    // Pre:  args.length >= 0 && для любого i из [0; args.length - 1) выполняется
    //       args[i] < args[i + 1] при сравнении числовых значений || существует j из [1; args.length),
    //       что для любого i != j - 1 из [0; args.length - 1) args[i] < args[i + 1],
    //       а также args[args.length - 1] < args[0]
    // Post: число в stdout: j из предусловия или 0, если такого j не существует
    public static void main(String[] args) {
        int[] array = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = Integer.parseInt(args[i]);
        }
        int result;
        // array.length >= 0 && для любого i из [0; array.length - 1) выполняется
        // array[i] < array[i + 1] || существует j, что для любого i != j - 1 из [0; array.length - 1)
        // array[i] < array[i + 1], а также array[array.length - 1] < array[0]
        if (array.length == 0 || array[0] < array[array.length - 1]) {
            // array.length >= 0 && для любого i из [0; array.length - 1) выполняется array[i] < array[i + 1]
            result = 0;
        } else {
            // array.length > 0 && существует j, что для любого i != j - 1 из [0; array.length - 1)
            // array[i] < array[i + 1], а также array[array.length - 1] < array[0]
//            result = recursiveFind(array);
            result = iterativeFind(array);
        }
        // array.length >= 0 && для любого i из [0; array.length - 1) выполняется
        // array[i] < array[i + 1] || существует j, что для любого i != j - 1 из [0; array.length - 1)
        // array[i] < array[i + 1], а также array[array.length - 1] < array[0]
        // result = j из предусловия || result = 0, если такого j не существует
        System.out.println(result);
        // stdout: result
    }
}
