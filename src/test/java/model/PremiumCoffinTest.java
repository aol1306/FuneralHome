package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremiumCoffinTest {

    @Test
    void premiumCoffinDecorationAggregation() {
        PremiumCoffin premiumCoffin = new PremiumCoffin();
        Decoration decoration = new Decoration();
        decoration.setPremiumCoffin(premiumCoffin);

        assertTrue(premiumCoffin.getDecorations().contains(decoration));
        assertEquals(decoration.getPremiumCoffin(), premiumCoffin);

        // add second time - this shouldn't break anything (so test)
        premiumCoffin.addDecoration(decoration);

        Decoration decoration1 = new Decoration();
        premiumCoffin.addDecoration(decoration1);
        assertTrue(premiumCoffin.getDecorations().contains(decoration));
        assertTrue(premiumCoffin.getDecorations().contains(decoration1));

        // test two-way removing
        premiumCoffin.removeDecoration(decoration1);
        assertNull(decoration1.getPremiumCoffin());
    }
}