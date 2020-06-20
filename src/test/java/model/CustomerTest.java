package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void CustomerFuneralAssociation() throws Exception {
        Funeral funeral = new Funeral();
        Customer customer = new Customer();
        customer.setFuneral(funeral);

        assertEquals(funeral.getCustomer(), customer);
    }
}