package by.bsuir.bank.entity;

public enum City {
    MINSK("Minsk"),
    GOMEL("Gomel");

    private String name;

    City(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
