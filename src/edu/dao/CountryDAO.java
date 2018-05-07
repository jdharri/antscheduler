/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dao;

import edu.jpacontrollers.CountryJpaController;
import edu.model.Country;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DAO for {@link Country}
 *
 * @author jdharri
 */
public class CountryDAO {

    private final CountryJpaController con;
    private final EntityManagerFactory emf;

    public CountryDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new CountryJpaController(emf);
    }
 
    public Country addCountry(Country country) throws Exception {
       return con.create(country);
    }

    public void editCountry(Country country) throws Exception {
        con.edit(country);
    }

    public void deleteCountry(int countryId) throws Exception {
        con.destroy(countryId);
    }

    public List<Country> getAllCities() {
        return con.findCountryEntities();
    }

    public Country getCountryById(int countryId) {
        return con.findCountry(countryId);
    }
}
