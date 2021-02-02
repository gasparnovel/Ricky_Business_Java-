package edu.ricky.business.ricky;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class ReceptivoTest {

    private Receptivo receptivo = null;
    private UfosParkTest parkTest = null;
    private GuestDispatcher packExpender = null;

    @Before
    public void setupTest() {

        parkTest = new UfosParkTest();
        parkTest.setupUfosPark();

        packExpender = (GuestDispatcher) new CrystalExpender(100, 50);

        receptivo = new Receptivo();
        receptivo.registra(parkTest.ufos);
        receptivo.registra(packExpender);
    }

    @Test
    public void dispatchTest() {

        CreditCard card = new CreditCard("Abradolf Lincler", "4916119711304546");
        receptivo.dispatch(card);

        assertEquals(2450, card.getCredit(), 0);
        assertTrue(parkTest.ufos.containsCard(card.getNumber()));
        assertEquals(99, ((CrystalExpender) packExpender).stock());
    }

    @Test
    public void dispatchNoCreditTest() {

        CreditCard card = new CreditCard("Abradolf Lincler", "4916119711304546");
        card.pay(3000);
        receptivo.dispatch(card);
        assertEquals(0, card.getCredit(), 0);
        assertFalse(parkTest.ufos.containsCard(card.getNumber()));
        assertEquals(100, ((CrystalExpender) packExpender).stock());
    }

}