package com.vfa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vfa.enums.EmployeeStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDate created;

    @JsonIgnore
    private String verificationCode;

    @Column(nullable = false)
    private long temporaryVerificationCode;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_address",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses;

    @OneToOne
    private Authority authority;

    public Employee() {
        status = EmployeeStatus.UNVERIFIED;
        created = LocalDate.now();
    }

    public Employee(String firstName, String lastName, String verificationCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.verificationCode = verificationCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public long getTemporaryVerificationCode() {
        return temporaryVerificationCode;
    }

    public void setTemporaryVerificationCode(long temporaryVerificationCode) {
        this.temporaryVerificationCode = temporaryVerificationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && temporaryVerificationCode == employee.temporaryVerificationCode && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && status == employee.status && Objects.equals(created, employee.created) && Objects.equals(verificationCode, employee.verificationCode) && Objects.equals(addresses, employee.addresses) && Objects.equals(authority, employee.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, status, created, verificationCode, temporaryVerificationCode, addresses, authority);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", verificationCode='" + verificationCode + '\'' +
                ", temporaryVerificationCode=" + temporaryVerificationCode +
                ", addresses=" + addresses +
                ", authority=" + authority +
                '}';
    }
}
