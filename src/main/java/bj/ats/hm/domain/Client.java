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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fistname", nullable = false)
    private String fistname;

    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Column(name = "cartnumenber", nullable = false)
    private String cartnumenber;

    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "datecome", nullable = false)
    private ZonedDateTime datecome;

    @Column(name = "datego")
    private ZonedDateTime datego;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "reservation")
    private Boolean reservation;

    @OneToOne
    @JoinColumn(unique = true)
    private Rooms room;

    @ManyToOne
    private Groupe groupe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFistname() {
        return fistname;
    }

    public Client fistname(String fistname) {
        this.fistname = fistname;
        return this;
    }

    public void setFistname(String fistname) {
        this.fistname = fistname;
    }

    public String getLastname() {
        return lastname;
    }

    public Client lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCartnumenber() {
        return cartnumenber;
    }

    public Client cartnumenber(String cartnumenber) {
        this.cartnumenber = cartnumenber;
        return this;
    }

    public void setCartnumenber(String cartnumenber) {
        this.cartnumenber = cartnumenber;
    }

    public String getTelephone() {
        return telephone;
    }

    public Client telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDatecome() {
        return datecome;
    }

    public Client datecome(ZonedDateTime datecome) {
        this.datecome = datecome;
        return this;
    }

    public void setDatecome(ZonedDateTime datecome) {
        this.datecome = datecome;
    }

    public ZonedDateTime getDatego() {
        return datego;
    }

    public Client datego(ZonedDateTime datego) {
        this.datego = datego;
        return this;
    }

    public void setDatego(ZonedDateTime datego) {
        this.datego = datego;
    }

    public Long getDuration() {
        return duration;
    }

    public Client duration(Long duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean isReservation() {
        return reservation;
    }

    public Client reservation(Boolean reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    public Rooms getRoom() {
        return room;
    }

    public Client room(Rooms rooms) {
        this.room = rooms;
        return this;
    }

    public void setRoom(Rooms rooms) {
        this.room = rooms;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Client groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", fistname='" + getFistname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", cartnumenber='" + getCartnumenber() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", datecome='" + getDatecome() + "'" +
            ", datego='" + getDatego() + "'" +
            ", duration='" + getDuration() + "'" +
            ", reservation='" + isReservation() + "'" +
            "}";
    }
}
