package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
        public void ticketNonImprimeSiMontantInsuffisant() {
                machine.insertMoney(10);
		machine.insertMoney(20);
                assertEquals("Le ticket a été imprimé alors que le montant est insuffisant",
                        machine.printTicket(), false);
                
        }
        
        @Test
        //  S4 : on imprime le ticket si le montant inséré est suffisant
        public void ticketImprimeSiMontantSuffisant() {
                machine.insertMoney(10);
		machine.insertMoney(20);
                machine.insertMoney(20);
                assertEquals("Le ticket n'a pas été imprimé alors que le montant est suffisant",
                        machine.printTicket(), true);
                
        }
        
        @Test
        //  S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
        public void ticketImprimeChangeBalance() {
                machine.insertMoney(20);
		machine.insertMoney(20);
                machine.insertMoney(20);
                machine.printTicket();
                assertEquals("Le ticket est imprimé mais la balance n'a pas changé",
                        machine.getBalance(), 20 + 20 + 20 - PRICE);    
        }

        @Test
        //  S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
        public void ticketImprimeChangeTotal() {
                machine.insertMoney(20);
		machine.insertMoney(20);
                machine.insertMoney(20);
                machine.printTicket();
                assertEquals("Le ticket est imprimé mais la total n'a pas bien changé",
                        machine.getTotal(), machine.getTotal(), PRICE);    
        }
        
        @Test
        //  S7 : refund() rend correctement la monnaie
        public void rendMonnaie() {
                machine.insertMoney(20);
		machine.insertMoney(20);
                machine.insertMoney(20);
                machine.printTicket();
                assertEquals("La monnaie n'a pas été bien rendue",
                        machine.refund(), 60-PRICE);    
        }
        
        @Test
        //  S8 : refund() remet la balance à zéro
        public void razBalance() {
                machine.insertMoney(20);
		machine.insertMoney(20);
                machine.insertMoney(20);
                machine.refund();
                assertEquals("La balance n'a pas été remise à zéro",
                        machine.getBalance(), 0);    
        }
        
        @Test
        //  S9 : on ne peut pas insérer un montant négatif
        public void montantNegatifInterdit() {
                try {
			machine.insertMoney(0); // Cette ligne doit lever une exception
			fail("La machine accepte des ùonta,ts négatifs");
		} catch (IllegalArgumentException e) {
		
		}   
        }
        
        @Test
        //  S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        public void TicketNegatifInterdit() {
                try {
			TicketMachine monTicket = new TicketMachine(0); // Cette ligne doit lever une exception
			fail();
		} catch (IllegalArgumentException e) {
		
		}   
        }
}
