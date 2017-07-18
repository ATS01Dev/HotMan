package bj.ats.hm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "evenement")
    private String evenement;

    @NotNull
    @Column(name = "date_revservation", nullable = false)
    private ZonedDateTime date_Revservation;

    @Column(name = "date_debut")
    private ZonedDateTime date_debut;

    @Column(name = "date_fin")
    private ZonedDateTime date_fin;

    @Column(name = "duree")
    private Long duree;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Rooms rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Reservation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvenement() {
        return evenement;
    }

    public Reservation evenement(String evenement) {
        this.evenement = evenement;
        return this;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public ZonedDateTime getDate_Revservation() {
        return date_Revservation;
    }

    public Reservation date_Revservation(ZonedDateTime date_Revservation) {
        this.date_Revservation = date_Revservation;
        return this;
    }

    public void setDate_Revservation(ZonedDateTime date_Revservation) {
        this.date_Revservation = date_Revservation;
    }

    public ZonedDateTime getDate_debut() {
        return date_debut;
    }

    public Reservation date_debut(ZonedDateTime date_debut) {
        this.date_debut = date_debut;
        return this;
    }

    public void setDate_debut(ZonedDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public ZonedDateTime getDate_fin() {
        return date_fin;
    }

    public Reservation date_fin(ZonedDateTime date_fin) {
        this.date_fin = date_fin;
        return this;
    }

    public void setDate_fin(ZonedDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public Long getDuree() {
        return duree;
    }

    public Reservation duree(Long duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public Reservation rooms(Rooms rooms) {
        this.rooms = rooms;
        return this;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", evenement='" + getEvenement() + "'" +
            ", date_Revservation='" + getDate_Revservation() + "'" +
            ", date_debut='" + getDate_debut() + "'" +
            ", date_fin='" + getDate_fin() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}
