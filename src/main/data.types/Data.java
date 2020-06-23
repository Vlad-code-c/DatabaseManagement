package main.data.types;

public abstract class Data {
    private String name;

    public Data(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
