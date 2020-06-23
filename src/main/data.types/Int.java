package main.data.types;

public class Int extends Data {
    private int value;

    public Int(int value) {
        super("Int");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
