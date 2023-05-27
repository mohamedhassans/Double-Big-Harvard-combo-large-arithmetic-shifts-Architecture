
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class InstructionsEncoder {

    static short pointerForEncoding = 0;


    public static void loadInstructions(File file) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                encodeInstructionMemory(arr);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void encodeInstructionMemory(String[] arr) {
        short opcode = convertToOpcode(arr[0]);
        short register1 = registerBinary(arr[1]);
        short register2 = registerBinary(arr[2]);

        InstructionMemory.instructions[pointerForEncoding] = new Instruction(opcode, register1, register2);
        pointerForEncoding++;
    }

    public static short convertToOpcode(String s) {
        s = s.toLowerCase();
        short opcode = switch (s) {
            case "add" -> 0;
            case "sub" -> 1;
            case "mul" -> 2;
            case "movi" -> 3;
            case "beqz" -> 4;
            case "andi" -> 5;
            case "eor" -> 6;
            case "br" -> 7;
            case "sal" -> 8;
            case "sar" -> 9;
            case "ldr" -> 10;
            case "str" -> 11;
            default -> -1;
        };


        return opcode;

    }

    //converts the register to binary
    public static short registerBinary(String s) {
        short register;
        if (s.charAt((0)) == 'R')
            register = Short.parseShort(s.substring(1));
        else
            register = Short.parseShort(s);

        return register;
    }
}