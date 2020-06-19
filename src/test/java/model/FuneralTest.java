package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuneralTest {

    @Test
    void funeralGraveDiggerAssociation() {
        Funeral funeral = new Funeral();
        GraveDigger graveDigger = new GraveDigger();

        funeral.addGraveDigger(graveDigger);

        // check if reverse connection was created
        assertTrue(graveDigger.getFunerals().contains(funeral));

        graveDigger.removeFuneral(funeral);

        // check if correctly removed
        assertFalse(funeral.getGraveDiggers().contains(graveDigger));
    }

    @Test
    void funeralCoffinAssociation() {
        Funeral funeral = new Funeral();
        Coffin premiumCoffin = new PremiumCoffin();

        funeral.addCoffin(premiumCoffin);

        // check reverse connection
        assertTrue(premiumCoffin.getFuneral().equals(funeral));

        // check with multiple coffins
        Coffin standardCoffin = new StandardCoffin();
        standardCoffin.setFuneral(funeral);

        assertTrue(funeral.getCoffins().contains(standardCoffin));
        assertTrue(funeral.getCoffins().contains(premiumCoffin));
    }

    @Test
    void funeralCaravanAssociation() {
        Funeral funeral = new Funeral();
        Caravan caravan = new Caravan();

        funeral.addCaravan(caravan);

        // check reverse connection
        assertTrue(caravan.getFunerals().contains(funeral));

        Caravan caravan1 = new Caravan();
        funeral.addCaravan(caravan1);

        assertTrue(caravan1.getFunerals().contains(funeral));

        Funeral funeral1 = new Funeral();
        caravan.addFuneral(funeral1);

        assertTrue(funeral1.getCaravans().contains(caravan));
    }
}