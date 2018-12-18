package by.bsuir.bank.entity;

public enum MaritalStatus {
    UNMARRIED("Unmarried"),
    ENGAGED("Engaged"),
    MARRIED("Married");

    private String name;

    MaritalStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
