package fr.unice.polytech.isa.polyevent.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Expose
    private String nom;

    @NotNull
    @Expose
    private Date debut;

    @NotNull
    @Expose
    private Date fin;

    @ManyToOne(cascade = CascadeType.ALL)
    private Organisateur organisateur;

    @ElementCollection
    @Expose
    private List<Reservation> reservations;

    @NotNull
    @Expose
    private Statut statut;


    public Evenement() {

    }


    public Evenement(String nom, Date debut, Date fin, Organisateur organisateur, List<Reservation> reservations, Statut statut) {
        this.nom = nom;
        this.debut = debut;
        this.fin = fin;
        this.organisateur = organisateur;
        this.reservations = reservations;
        this.statut = statut;
        this.organisateur.getEvenements().add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public String getNom() {
        return nom;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return  Objects.equals(nom, evenement.nom) &&
                Objects.equals(debut, evenement.debut) &&
                Objects.equals(fin, evenement.fin) &&
                Objects.equals(organisateur, evenement.organisateur);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nom, debut, fin, organisateur);
    }
}
