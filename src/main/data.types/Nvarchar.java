package main.data.types;

public class Nvarchar extends Data {

    private String value;
    private int len = 50; //Default

    public Nvarchar(String name, String value) {
        super("Nvarchar");
        this.value = value;
    }

    public Nvarchar(String name, String value, int len) {
        super("Nvarchar");
        this.value = value;
        this.len = len;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
