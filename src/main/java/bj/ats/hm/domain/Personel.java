package bj.ats.hm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import bj.ats.hm.domain.enumeration.Sexe;

/**
 * A Personel.
 */
@Entity
@Table(name = "personel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "personel")
public class Personel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fistname", nullable = false)
    private String fistname;

    @NotNull
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @OneToOne
    @JoinColumn(unique = true)
    private Fonction fonction;

    @OneToMany(mappedBy = "personel")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horraires> horraires = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFistname() {
        return fistname;
    }

    public Personel fistname(String fistname) {
        this.fistname = fistname;
        return this;
    }

    public void setFistname(String fistname) {
        this.fistname = fistname;
    }

    public String getLastname() {
        return lastname;
    }

    public Personel lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Personel sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Personel fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Set<Horraires> getHorraires() {
        return horraires;
    }

    public Personel horraires(Set<Horraires> horraires) {
        this.horraires = horraires;
        return this;
    }

    public Personel addHorraire(Horraires horraires) {
        this.horraires.add(horraires);
        horraires.setPersonel(this);
        return this;
    }

    public Personel removeHorraire(Horraires horraires) {
        this.horraires.remove(horraires);
        horraires.setPersonel(null);
        return this;
    }

    public void setHorraires(Set<Horraires> horraires) {
        this.horraires = horraires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personel personel = (Personel) o;
        if (personel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personel{" +
            "id=" + getId() +
            ", fistname='" + getFistname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", sexe='" + getSexe() + "'" +
            "}";
    }
}
