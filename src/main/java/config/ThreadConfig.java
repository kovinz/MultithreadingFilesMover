package config;

public class ThreadConfig {
    private int quantityOfThreads;
    private int quantityOfRowsForThread;

    public int getQuantityOfThreads() {
        return quantityOfThreads;
    }

    public void setQuantityOfThreads(int quantityOfThreads) {
        this.quantityOfThreads = quantityOfThreads;
    }

    public int getQuantityOfRowsForThread() {
        return quantityOfRowsForThread;
    }

    public void setQuantityOfRowsForThread(int quantityOfRowsForThread) {
        this.quantityOfRowsForThread = quantityOfRowsForThread;
    }
}
