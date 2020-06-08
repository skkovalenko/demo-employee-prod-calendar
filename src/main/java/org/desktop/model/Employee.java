package org.desktop.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "employess")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String position;
    @Column(name = "remote_work")
    private boolean remoteWork;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CalendarProd> calendarProds = new HashSet<>();

    @Transient
    private String fullName;
    @Transient
    private String addressStr;
    @Transient
    private String city;
    @Transient
    private String street;
    @Transient
    private String house;
    @Transient
    private String apart;
    @Transient
    private int age;

    public int getAge() {
        return Period.between(getDateOfBirth(), LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return address.getCity();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return address.getHouse();
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApart() {
        return address.getApp();
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    public String getAddressStr() {
        addressStr = "";
        addressStr = address.getCity() + " / "
                + address.getStreet() + " / "
                + address.getHouse() + " / "
                + address.getApp();
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getFullName() {
        fullName = "";
        fullName = lastName + " " + firstName + " " + middleName;
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public TreeSet<CalendarProd> getCalendarProds() {
        return new TreeSet<>(calendarProds);
    }

    public void setCalendarProds(Set<CalendarProd> calendarProds) {
        this.calendarProds = calendarProds;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return Instant.ofEpochMilli(dateOfBirth.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void setDateOfBirth(String dateOfBirth)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.dateOfBirth = Date.from(LocalDate.parse(dateOfBirth).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant());
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isRemoteWork() {
        return remoteWork;
    }

    public void setRemoteWork(boolean remoteWork) {
        this.remoteWork = remoteWork;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
