package bj.ats.hm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "debut", nullable = false)
    private ZonedDateTime debut;

    @NotNull
    @Column(name = "fin", nullable = false)
    private ZonedDateTime fin;

    @Column(name = "duree")
    private Long duree;

    @OneToOne(mappedBy = "reservation")
    @JsonIgnore
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

    public ZonedDateTime getDate() {
        return date;
    }

    public Reservation date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getDebut() {
        return debut;
    }

    public Reservation debut(ZonedDateTime debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(ZonedDateTime debut) {
        this.debut = debut;
    }

    public ZonedDateTime getFin() {
        return fin;
    }

    public Reservation fin(ZonedDateTime fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(ZonedDateTime fin) {
        this.fin = fin;
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
            ", date='" + getDate() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}
