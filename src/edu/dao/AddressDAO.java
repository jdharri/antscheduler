package edu.dao;

import edu.jpacontrollers.AddressJpaController;
import edu.model.Address;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DAO for {@link Address}
 *
 * @author jdharri
 */
public class AddressDAO {

    private final AddressJpaController con;
    private final EntityManagerFactory emf;

    public AddressDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new AddressJpaController(emf);
    }

    public Address addAddress(Address address) throws Exception {
       return con.create(address);
    }

    public void editAddress(Address address) throws Exception {
        con.edit(address);
    }

    public void deleteAddress(int addressId) throws Exception {
        con.destroy(addressId);
    }

    public List<Address> getAllCities() {
        return con.findAddressEntities();
    }

    public Address getAddressById(int addressId) {
        return con.findAddress(addressId);
    }
}
