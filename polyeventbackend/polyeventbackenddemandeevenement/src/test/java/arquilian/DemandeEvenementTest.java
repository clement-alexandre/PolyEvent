package arquilian;

import fr.unice.polytech.isa.polyevent.entities.DemandeReservationSalle;
import fr.unice.polytech.isa.polyevent.entities.Evenement;
import fr.unice.polytech.isa.polyevent.entities.Organisateur;
import fr.unice.polytech.isa.polyevent.utils.Database;
import fr.unice.polytech.isa.polyevent.webservice.DemandeEvenement;
import fr.unice.polytech.isa.polyevent.webservice.DemanderEvenement;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class DemandeEvenementTest {

    // Classes to package into a deployable unit used to run the test
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Business Objects
                .addPackage(Database.class.getPackage())
                .addPackage(Organisateur.class.getPackage())
                .addPackage(DemandeReservationSalle.class.getPackage())
                .addPackage(Evenement.class.getPackage())
                // Components interfaces
                .addPackage(DemanderEvenement.class.getPackage())
                // Component implementation
                .addPackage(DemandeEvenement.class.getPackage());
    }

    @EJB private DemandeEvenement demandeEvenement;
    @EJB private Database memory;

    @Before public void flushDatabase() { memory.flush(); }

    @Test public void noEvenementByDefault() {
        assertEquals(0, memory.getEvenements().size());
    }

    @Ignore
    @Test public void shouldCreateAnEvent() {
        Organisateur organisateur = new Organisateur("jean");
        demandeEvenement.demanderCreationEvenement(organisateur, "hashcode", new Date(), new Date(), new ArrayList<>());
        assertEquals(1, memory.getEvenements().size());
    }

}