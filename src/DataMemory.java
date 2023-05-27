public class DataMemory {
    public static final int maxRows = 2048;
    public static byte[] contents;

    public DataMemory() {
        contents = new byte[maxRows];
    }

    public byte loadWord(int address) {
        return contents[address];
    }
    public void storeWord(int address, byte data) {
        contents[address] = data;
    }
    public static void printContents() {
        for (int i = 0; i < maxRows; i++) {
            System.out.println("Address: " + i + " Data: " + contents[i]);
        }
    }
}
