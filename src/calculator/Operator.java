package calculator;

import gui.CalcButtonListener;

public enum Operator {
    ADD("+"),
    SUB("-"),
    MUL("x"),
    DIV("÷"),
    EQ("="),
    MOD("%"),
    IDIV("/"),
    EXP("^"),
    ROOT("root"),
    BADOP("bad");

    private String opString;

    private Operator(String opStr) {
        this.opString = opStr;
    }

    public static Operator strTop(String opStr) {
        for (Operator op : Operator.values()) {
            if (op.opString == opStr) return op;
        }
        return BADOP;
    }

    @Override
    public String toString() {
        return this.opString;
    }

    public boolean isUnary() {
        boolean result = false;
        switch (this) {
            default -> result = false;
        }
        return result;
    }

    public String operate(double operand1, double operand2) {

        switch(this) {
            case ADD:
                return String.valueOf(operand1 + operand2);
            case SUB:
                return String.valueOf(operand1 - operand2);
            case MUL:
                return String.valueOf(operand1 * operand2);
            case DIV:
                return String.valueOf(operand1 / operand2);
            case IDIV:
                return String.valueOf(Math.floorDiv((int) operand1, (int) operand2));
            case MOD:
                return String.valueOf(operand1 % operand2);
            case EXP:
                return String.valueOf(Math.pow(operand1, operand2));
            case ROOT:
                return String.valueOf(Math.pow(operand1, 1.0/operand2));
            default:
                return CalcButtonListener.ERROR;
        }
    }
}
