package by.bsuir.bank.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Client {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private Timestamp birthday;
    private String email;
    private String passportSeries;
    private String passportNumber;
    private String passportGivePlace;
    private Timestamp passportGivenDate;
    private String identifyNumber;
    private String birthplace;
    private City livingPlace;
    private String livingAddress;
    private String homePhone;
    private String mobilePhone;
    private String workPlace;
    private String post;
    private MaritalStatus maritalStatuses;
    private Nationality nationality;
    private Disability disability;
    private boolean isRetiree;
    private boolean isLiableForMilitaryService;
    private BigDecimal monthlyEarnings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportGivePlace() {
        return passportGivePlace;
    }

    public void setPassportGivePlace(String passportGivePlace) {
        this.passportGivePlace = passportGivePlace;
    }

    public Timestamp getPassportGivenDate() {
        return passportGivenDate;
    }

    public void setPassportGivenDate(Timestamp passportGivenDate) {
        this.passportGivenDate = passportGivenDate;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public City getLivingPlace() {
        return livingPlace;
    }

    public void setLivingPlace(City livingPlace) {
        this.livingPlace = livingPlace;
    }

    public String getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public MaritalStatus getMaritalStatuses() {
        return maritalStatuses;
    }

    public void setMaritalStatuses(MaritalStatus maritalStatuses) {
        this.maritalStatuses = maritalStatuses;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Disability getDisability() {
        return disability;
    }

    public void setDisability(Disability disability) {
        this.disability = disability;
    }

    public boolean isRetiree() {
        return isRetiree;
    }

    public void setRetiree(boolean retiree) {
        isRetiree = retiree;
    }

    public boolean isLiableForMilitaryService() {
        return isLiableForMilitaryService;
    }

    public void setLiableForMilitaryService(boolean liableForMilitaryService) {
        isLiableForMilitaryService = liableForMilitaryService;
    }

    public BigDecimal getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(BigDecimal monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                isRetiree == client.isRetiree &&
                isLiableForMilitaryService == client.isLiableForMilitaryService &&
                Objects.equals(email, client.email) &&
                Objects.equals(passportSeries, client.passportSeries) &&
                Objects.equals(passportNumber, client.passportNumber) &&
                Objects.equals(passportGivePlace, client.passportGivePlace) &&
                Objects.equals(passportGivenDate, client.passportGivenDate) &&
                Objects.equals(identifyNumber, client.identifyNumber) &&
                Objects.equals(birthplace, client.birthplace) &&
                livingPlace == client.livingPlace &&
                Objects.equals(livingAddress, client.livingAddress) &&
                Objects.equals(homePhone, client.homePhone) &&
                Objects.equals(mobilePhone, client.mobilePhone) &&
                Objects.equals(workPlace, client.workPlace) &&
                Objects.equals(post, client.post) &&
                maritalStatuses == client.maritalStatuses &&
                nationality == client.nationality &&
                disability == client.disability &&
                Objects.equals(monthlyEarnings, client.monthlyEarnings);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, passportSeries, passportNumber, passportGivePlace, passportGivenDate, identifyNumber, birthplace, livingPlace, livingAddress, homePhone, mobilePhone, workPlace, post, maritalStatuses, nationality, disability, isRetiree, isLiableForMilitaryService, monthlyEarnings);
    }
}
