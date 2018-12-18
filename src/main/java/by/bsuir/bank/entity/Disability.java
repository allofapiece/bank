package by.bsuir.bank.entity;

public enum Disability {
    FIRST_DEGREE("First degree"),
    SECOND_DEGREE("Second degree"),
    THIRD_DEGREE("Third degree");

    private String name;

    Disability(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
