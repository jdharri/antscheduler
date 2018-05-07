package edu.dao;

import edu.jpacontrollers.CityJpaController;
import edu.model.City;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DAO for {@link City}
 *
 * @author jdharri
 */
public class CityDAO {

    private final CityJpaController con;
    private final EntityManagerFactory emf;

    public CityDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new CityJpaController(emf);
    }

    public City addCity(City city) throws Exception {
       return con.create(city);
    }

    public void editCity(City city) throws Exception {
        con.edit(city);
    }

    public void deleteCity(int cityId) throws Exception {
        con.destroy(cityId);
    }

    public List<City> getAllCities() {
        return con.findCityEntities();
    }

    public City getCityById(int cityId) {
        return con.findCity(cityId);
    }
}
