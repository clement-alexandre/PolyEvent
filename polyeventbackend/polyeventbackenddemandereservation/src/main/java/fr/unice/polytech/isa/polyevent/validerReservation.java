package fr.unice.polytech.isa.polyevent;

import javax.ejb.Local;

@Local
public interface validerReservation {

    boolean accepterReservation();

}
