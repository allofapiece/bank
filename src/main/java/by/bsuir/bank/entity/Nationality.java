package by.bsuir.bank.entity;

public enum Nationality {
    BELARUS("Belarus"),
    RUSSIA("Russia");

    private String name;

    Nationality(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
