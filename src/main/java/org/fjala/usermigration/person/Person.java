/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fjala.usermigration.person;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ubuntu
 */
public class Person implements Serializable {

    /**
     * {@value #THREE} Calculation constant used in method hashCode.
     */
    private static final int THREE = 3;
    /**
     * {@value #FIVE} Calculation constant used in method hashCode.
     */
    private static final int FIFTY_NINE = 59;

    /**
     * Full name of person.
     */
    private String name;
    /**
     * Last name of person.
     */
    private String lastName;
    /**
     * Second last name of person.
     */
    private String secondLastName;
    /**
     * Personal identification card of person.
     */
    private String ci;
    
    /**
     * Email of person.
     */
    private String username;
    
    /**
     * Email of person.
     */
    private String email;

    /**
     * Image of the person.
     */
    private byte[] image;

    /**
     * Date of birth date of the person.
     */
    private Date birthdate;

    /**
     * Builder for contact information.
     */
    public Person() {
        /**
         * Get the person's roles
         * /
         */
    }

    public Person(String name, String lastName, String secondLastName,
            String ci, String email, byte[] image, Date birthdate) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.ci = ci;
        //use StringBuilder instead of concat for username
        this.username = name.concat(".".concat(lastName));
        this.email = email;
        this.birthdate = birthdate;
    }

    /**
     * Returns full name of person.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Renames person.
     *
     * @param namePerson of the person
     */
    public void setName(final String namePerson) {
        this.name = namePerson;
    }

    /**
     * Returns last name of person.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Renames the last name of person.
     *
     * @param lastNamePerson of the person
     */
    public void setLastName(final String lastNamePerson) {
        this.lastName = lastNamePerson;
    }

    /**
     * Returns second last name of person.
     *
     * @return secondLastName
     */
    public String getSecondLastName() {
        return secondLastName;
    }

    /**
     * Renames the second last name of person.
     *
     * @param secondLastNamePerson of the person
     */
    public void setSecondLastName(final String secondLastNamePerson) {
        this.secondLastName = secondLastNamePerson;
    }

    /**
     * Return the personal identification card of person.
     *
     * @return ci
     */
    public String getCi() {
        return ci;
    }

    /**
     * Renames personal identification card of person.
     *
     * @param ciPerson of person
     */
    public void setCi(final String ciPerson) {
        this.ci = ciPerson;
    }

    /**
     * Returns username of person.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Renames username of person.
     *
     * @param username of the person
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Returns email of person.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Renames email of person.
     *
     * @param emailPerson of the person
     */
    public void setEmail(final String emailPerson) {
        this.email = emailPerson;
    }

    /**
     * Return the date of the person.
     *
     * @return Date
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Set a date for the person.
     *
     * @param birthdatePerson of the person
     */
    public void setBirthdate(final Date birthdatePerson) {
        this.birthdate = birthdatePerson;
    }

    /**
     * In this method returns the image.
     *
     * @return String
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Insert the image of a person.
     *
     * @param imagePerson of the person
     */
    public void setImage(final byte[] imagePerson) {
        this.image = imagePerson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = THREE;
        hash = FIFTY_NINE * hash + Objects.hashCode(this.email);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
