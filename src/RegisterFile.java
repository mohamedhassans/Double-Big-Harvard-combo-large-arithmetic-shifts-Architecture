public class RegisterFile {
    public final StatusRegister statusRegister;
    public static final int numberOfGeneralRegisters = 64;
    public static byte[] registers;


    public RegisterFile() {
        registers = new byte[numberOfGeneralRegisters];
        statusRegister = new StatusRegister();
    }

    public StatusRegister getStatusRegister() {
        return statusRegister;
    }

    //print the contents of the register file
    public static void printContents() {
        for (int i = 0; i < numberOfGeneralRegisters; i++) {
            System.out.println("Register: " + i + " Data: " + registers[i]);
        }
    }

}
