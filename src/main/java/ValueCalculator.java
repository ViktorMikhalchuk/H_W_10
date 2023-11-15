public class ValueCalculator {
    private float []array;
    private int size;

    public ValueCalculator(int size) {
        this.size = Math.max(size, 1000000);
        this.array = new float[size];
    }

    public void calculate() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            array[i] = 1.0f;
        }

        int half = size / 2;

        float[] a1 = new float[half];
        float[] a2 = new float[half];

        System.arraycopy(array, 0, a1, 0, half);
        System.arraycopy(array, half, a2, 0, half);
        Thread thread1 = new Thread(() -> processArray(a1, 0));
        Thread thread2 = new Thread(() -> processArray(a2, half));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, array, 0, half);
        System.arraycopy(a2, 0, array, half, half);

        long end = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (end - start) + " milliseconds");
    }

    private void processArray(float[] arr, int offset) {
        for (int i = 0; i < arr.length; i++) {
            int index = offset + i;
            arr[i] = (float) (arr[i] * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
        }
    }
}
