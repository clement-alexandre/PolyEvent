package fr.unice.polytech.isa.polyevent.caissier.webservice;

import fr.unice.polytech.isa.polyevent.ChercherEvenement;
import fr.unice.polytech.isa.polyevent.caissier.bean.Caisse;
import fr.unice.polytech.isa.polyevent.comptable.Facturer;
import fr.unice.polytech.isa.polyevent.entities.Evenement;
import fr.unice.polytech.isa.polyevent.entities.Token;
import fr.unice.polytech.isa.polyevent.entities.exceptions.EvenementInconnuException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Date;
import java.util.Optional;

@WebService(targetNamespace = "http://www.polytech.unice.fr/si/4a/isa/polyevent/payerEvenement")
@Stateless(name = "PayerEvenementWS")
public class Payer implements PayerEvenement
{
    @EJB
    private Caisse caisse;

    @Override
    public String payerEvenement(Token token, String nom, Date dateDebut, Date dateFin, String creditCard)
    {
        return caisse.payer(token.getOrganisateur(), nom, dateDebut, dateFin, creditCard).toString();
    }
}
