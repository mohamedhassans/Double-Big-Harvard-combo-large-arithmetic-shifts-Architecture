public class StatusRegister {
    public static boolean carryFlag;
    public static boolean overFlowFlag;
    public static boolean negativeFlag;
    public static boolean signFlag;
    public static boolean zeroFlag;

public static String printstatusreg(){
    return "Status register in binary: "+"000"+(carryFlag?1:0)+(overFlowFlag?1:0)+(negativeFlag?1:0)+(signFlag?1:0)+(zeroFlag?1:0);

}

}
