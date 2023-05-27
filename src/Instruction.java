public class Instruction {
    public short opcode;
    public short sourceRegister1;
    public short sourceRegister2;

    public Instruction(short opcode, short sourceRegister1, short sourceRegister2) {
        this.opcode = opcode;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    public short getRInsruction() {
        return (short) (opcode << 12 | sourceRegister1 << 6 | sourceRegister2);

    }

    public short getIInsruction() {
        return (short) (opcode << 12 | sourceRegister1 << 6);

    }


    public String printbinaryInstruction() {
        if (isIType()) {
            String binaryInstruction = Integer.toBinaryString(getIInsruction());
            if (binaryInstruction.length() > 16) {
                binaryInstruction = binaryInstruction.substring(16);
            }
            // Processor.pw.println("dfd "+binaryInstruction);
            binaryInstruction = "0".repeat(16 - binaryInstruction.length()) + binaryInstruction.substring(0, binaryInstruction.length() - 6) + convertImmToBinary(sourceRegister2);
            // Processor.pw.println("dfddd "+binaryInstruction);
            return binaryInstruction;

        } else {
            String binaryInstruction = Integer.toBinaryString(getRInsruction());
            binaryInstruction = "0".repeat(16 - binaryInstruction.length()) + binaryInstruction;
            return binaryInstruction;
        }
    }

    public static String convertImmToBinary(short number) {
        String binary = "";
        for (int i = 0; i < 6; i++) {
            binary = (number & 1) + binary;
            number >>= 1;
        }
        return binary;

    }

    public boolean isIType() {

        if (opcode == 3 || opcode == 4 || opcode == 5 || opcode == 8 || opcode == 9 || opcode == 10 || opcode == 11)
            return true;
        return false;
    }

    public void execute() {
        switch (opcode) {
            case 0:
                add();
                break;
            case 1:
                sub();
                break;
            case 2:
                mul();
                break;
            case 3:
                movi();
                break;
            case 4:
                beqz();
                break;
            case 5:
                andi();
                break;
            case 6:
                eor();
                break;
            case 7:
                br();
                break;
            case 8:
                sal();
                break;
            case 9:
                sar();
                break;
            case 10:
                ldr();
                break;
            case 11:
                str();
                break;


        }


    }

    public void add() {
        int res = RegisterFile.registers[sourceRegister1] + RegisterFile.registers[sourceRegister2];
        byte result = (byte) (RegisterFile.registers[sourceRegister1] + RegisterFile.registers[sourceRegister2]);
        StatusRegister.carryFlag = (((res >> 8) & 1) == 1 ? true : false);
        Processor.pw.println("Carry flag is set to " + StatusRegister.carryFlag);
        //------------------------//
        if ((RegisterFile.registers[sourceRegister1] > 0 && RegisterFile.registers[sourceRegister2] > 0 && result <= 0) ||
                (RegisterFile.registers[sourceRegister1] < 0 && RegisterFile.registers[sourceRegister2] < 0 && result >= 0)) {
            StatusRegister.overFlowFlag = true;
            Processor.pw.println("Overflow flag is set to " + true);
        } else {
            StatusRegister.overFlowFlag = false;
            Processor.pw.println("Overflow flag is set to " + false);
        }
        //------------------------//
        StatusRegister.negativeFlag = (result < 0 ? true : false);
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        StatusRegister.signFlag = StatusRegister.negativeFlag ^ StatusRegister.overFlowFlag;
        Processor.pw.println("Sign flag is set to " + StatusRegister.signFlag);
        //------------------------//
        StatusRegister.zeroFlag = (result == 0 ? true : false);
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        //------------------------//
        Processor.pw.println(StatusRegister.printstatusreg());
        RegisterFile.registers[sourceRegister1] = (byte) result;
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);


    }

    public void sub() {
        int res = RegisterFile.registers[sourceRegister1] - RegisterFile.registers[sourceRegister2];
        byte result = (byte) (RegisterFile.registers[sourceRegister1] - RegisterFile.registers[sourceRegister2]);
        //------------------------//
        if ((RegisterFile.registers[sourceRegister1] > 0 && RegisterFile.registers[sourceRegister2] < 0 && result <= 0) ||
                (RegisterFile.registers[sourceRegister1] < 0 && RegisterFile.registers[sourceRegister2] > 0 && result >= 0)) {
            StatusRegister.overFlowFlag = true;
            Processor.pw.println("Overflow flag is set to " + true);
        } else {
            StatusRegister.overFlowFlag = false;
            Processor.pw.println("Overflow flag is set to " + false);
        }
        //------------------------//
        StatusRegister.negativeFlag = (result < 0 ? true : false);
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        StatusRegister.signFlag = StatusRegister.negativeFlag ^ StatusRegister.overFlowFlag;
        Processor.pw.println("Sign flag is set to " + StatusRegister.signFlag);
        //------------------------//
        StatusRegister.zeroFlag = (result == 0 ? true : false);
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        //------------------------//
        Processor.pw.println(StatusRegister.printstatusreg());

        RegisterFile.registers[sourceRegister1] = (byte) result;
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);
    }

    public void mul() {
        int res = RegisterFile.registers[sourceRegister1] * RegisterFile.registers[sourceRegister2];
        byte result = (byte) (RegisterFile.registers[sourceRegister1] * RegisterFile.registers[sourceRegister2]);

        //------------------------//
        StatusRegister.negativeFlag = (result < 0 ? true : false);
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//

        StatusRegister.zeroFlag = (result == 0 ? true : false);
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        //------------------------//
        Processor.pw.println(StatusRegister.printstatusreg());

        RegisterFile.registers[sourceRegister1] = (byte) result;
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);
    }

    public void movi() {
        RegisterFile.registers[sourceRegister1] = (byte) sourceRegister2;
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);

    }

    public void beqz() {
        if (RegisterFile.registers[sourceRegister1] == 0) {
            InstructionMemory.pcRegisterValue =
                    (short) (InstructionMemory.decodeexecute2.pcRegisterValue + 1 + sourceRegister2);
            InstructionMemory.fetchdecode1.ready = false;
            InstructionMemory.decodeexecute1.ready = false;
            InstructionMemory.fetchdecode2.ready = false;
            InstructionMemory.decodeexecute2.ready = false;
        }
    }

    public void andi() {

        StatusRegister.negativeFlag = (RegisterFile.registers[sourceRegister1] & sourceRegister2) < 0 ? true : false;
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        //zero flag is set if the result is zero
        StatusRegister.zeroFlag = (RegisterFile.registers[sourceRegister1] & sourceRegister2) == 0 ? true : false;
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        //------------------------//
        Processor.pw.println(StatusRegister.printstatusreg());
        RegisterFile.registers[sourceRegister1] = (byte) (RegisterFile.registers[sourceRegister1] & sourceRegister2);
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);
    }

    public void eor() {

        StatusRegister.negativeFlag = (RegisterFile.registers[sourceRegister1] ^ RegisterFile.registers[sourceRegister2]) < 0 ? true : false;
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        //zero flag is set if the result is zero
        StatusRegister.zeroFlag = (RegisterFile.registers[sourceRegister1] ^ RegisterFile.registers[sourceRegister2]) == 0 ? true : false;
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        //------------------------//
        Processor.pw.println(StatusRegister.printstatusreg());

        RegisterFile.registers[sourceRegister1] = (byte) (RegisterFile.registers[sourceRegister1] ^ RegisterFile.registers[sourceRegister2]);
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);

    }

    public void br() {
        short pcvalue=RegisterFile.registers[sourceRegister1];
        pcvalue<<=8;
        pcvalue|=RegisterFile.registers[sourceRegister2];
        InstructionMemory.pcRegisterValue =    pcvalue;
        InstructionMemory.fetchdecode1.ready = false;
        InstructionMemory.decodeexecute1.ready = false;
        InstructionMemory.fetchdecode2.ready = false;
        InstructionMemory.decodeexecute2.ready = false;

    }

    public void sal() {
        //negative flag is set if the result is negative
        StatusRegister.negativeFlag = (RegisterFile.registers[sourceRegister1] << sourceRegister2) < 0 ? true : false;
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        //zero flag is set if the result is zero
        StatusRegister.zeroFlag = (RegisterFile.registers[sourceRegister1] << sourceRegister2) == 0 ? true : false;
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);

        Processor.pw.println(StatusRegister.printstatusreg());

        RegisterFile.registers[sourceRegister1] = (byte) (RegisterFile.registers[sourceRegister1] << sourceRegister2);
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);
        //


    }

    public void sar() {
        //negative flag is set if the result is negative
        StatusRegister.negativeFlag = (RegisterFile.registers[sourceRegister1] >> sourceRegister2) < 0 ? true : false;
        Processor.pw.println("Negative flag is set to " + StatusRegister.negativeFlag);
        //------------------------//
        //zero flag is set if the result is zero
        StatusRegister.zeroFlag = (RegisterFile.registers[sourceRegister1] >> sourceRegister2) == 0 ? true : false;
        Processor.pw.println("Zero flag is set to " + StatusRegister.zeroFlag);
        Processor.pw.println(StatusRegister.printstatusreg());

        RegisterFile.registers[sourceRegister1] = (byte) (RegisterFile.registers[sourceRegister1] >> sourceRegister2);
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);

    }

    public void ldr() {
        RegisterFile.registers[sourceRegister1] = DataMemory.contents[sourceRegister2];
        Processor.pw.println("The content of register " + sourceRegister1 + " is " + RegisterFile.registers[sourceRegister1]);
    }

    public void str() {
        DataMemory.contents[sourceRegister2] = RegisterFile.registers[sourceRegister1];
        Processor.pw.println("The content of Memory address " + sourceRegister2 + " is " + RegisterFile.registers[sourceRegister1]);
    }
}
