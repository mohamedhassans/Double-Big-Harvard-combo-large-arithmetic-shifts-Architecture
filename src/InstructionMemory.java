import java.io.PrintWriter;

public class InstructionMemory {
    public static final int maxRows = 1024;
    public static  short pcRegisterValue;
    public static  Instruction[] instructions;
    public static PipelineRegister fetchdecode1;
    public static PipelineRegister fetchdecode2;
    public static PipelineRegister decodeexecute1;
    public static PipelineRegister decodeexecute2;
    public InstructionMemory() {
        pcRegisterValue = 0;
        instructions = new Instruction[maxRows];
        fetchdecode1 = new PipelineRegister();
        decodeexecute1 = new PipelineRegister();
        fetchdecode2 = new PipelineRegister();
        decodeexecute2 = new PipelineRegister();
    }

    public static void printBinaryInstructions() {
        PrintWriter printWriter = new PrintWriter(System.out);
        for (Instruction instruction : instructions) {
            if(instruction == null) {
                break;
            }

            printWriter.println(instruction.printbinaryInstruction());
        }
        printWriter.flush();
    }




    public static void fetch(){
        if(instructions[pcRegisterValue] == null){
            Processor.pw.println("No more instructions to be fetched");
            fetchdecode1.ready = false;
            return;
        }
        else {
            fetchdecode1.instruction = instructions[pcRegisterValue];
            fetchdecode1.pcRegisterValue = pcRegisterValue;
            fetchdecode1.ready = true;
            pcRegisterValue++;
            Processor.pw.println("Instruction fetched " + fetchdecode1.instruction.printbinaryInstruction());

        }

    }

    public static void decode(){


        if(fetchdecode2.ready) {
            decodeexecute1.instruction = fetchdecode2.instruction;
            decodeexecute1.pcRegisterValue = fetchdecode2.pcRegisterValue;
            decodeexecute1.ready = true;
            fetchdecode2.ready = false;

            Processor.pw.println("Instruction decoded " + fetchdecode2.instruction.printbinaryInstruction());

        }
        else {
            Processor.pw.println("No instruction to be decoded");
        }
        if(fetchdecode1.ready){
            fetchdecode2.instruction = fetchdecode1.instruction;
            fetchdecode2.pcRegisterValue = fetchdecode1.pcRegisterValue;
            fetchdecode2.ready = true;


        }






    }
    public static void execute(){

        if(decodeexecute2.ready) {
            Processor.pw.println("Instruction executed " + decodeexecute2.instruction.printbinaryInstruction());
            decodeexecute2.instruction.execute();
            decodeexecute2.ready = false;
        }
        else {
            Processor.pw.println("No instruction to be executed");
        }
        if (decodeexecute1.ready) {
            decodeexecute2.instruction = decodeexecute1.instruction;
            decodeexecute2.pcRegisterValue = decodeexecute1.pcRegisterValue;
            decodeexecute2.ready = true;
            decodeexecute1.ready = false;

        }
        else {

            decodeexecute2.ready = false;
        }

    }


}
