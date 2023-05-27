import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Processor {

    static PrintWriter pw;
    static StringWriter stringWriter;


    public static void runProgram(){
         stringWriter = new StringWriter();
        pw = new PrintWriter(stringWriter);

        new InstructionMemory();
        new DataMemory();
        new RegisterFile();
        InstructionsEncoder.loadInstructions(new File("src\\test.txt"));

        int cycles = 1;

        while ((InstructionMemory.instructions[InstructionMemory.pcRegisterValue] != null) ||
                (InstructionMemory.fetchdecode2.ready == true) ||
                (InstructionMemory.decodeexecute2.ready == true)) {
            pw.println("Cycle: " + cycles);

            InstructionMemory.fetch();

            InstructionMemory.decode();

            InstructionMemory.execute();

            cycles++;
            pw.println("--------------------------------------------------");

        }
        pw.println("Done");
        //pw.flush();
        System.out.println("--------------------------------------------------");
        System.out.println("Instructions: ");
        InstructionMemory.printBinaryInstructions();
        System.out.println("--------------------------------------------------");

        System.out.println("Register File Contents: ");
        RegisterFile.printContents();
        System.out.println("--------------------------------------------------");
        System.out.println("Data Memory Contents: ");
        DataMemory.printContents();
        System.out.println("--------------------------------------------------");
        System.out.println("Done");
    }

}



