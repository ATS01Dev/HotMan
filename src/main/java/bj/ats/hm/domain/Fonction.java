package bj.ats.hm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Fonction.
 */
@Entity
@Table(name = "fonction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fonction")
public class Fonction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "poste", nullable = false)
    private String poste;

    @NotNull
    @Column(name = "stardate", nullable = false)
    private LocalDate stardate;

    @Column(name = "typecontrat")
    private String typecontrat;

    @Column(name = "durrecontrat")
    private String durrecontrat;

    @Column(name = "datenaissane")
    private LocalDate datenaissane;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "email")
    private String email;

    @Column(name = "salary")
    private Long salary;

    @OneToOne
    @JoinColumn(unique = true)
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoste() {
        return poste;
    }

    public Fonction poste(String poste) {
        this.poste = poste;
        return this;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public LocalDate getStardate() {
        return stardate;
    }

    public Fonction stardate(LocalDate stardate) {
        this.stardate = stardate;
        return this;
    }

    public void setStardate(LocalDate stardate) {
        this.stardate = stardate;
    }

    public String getTypecontrat() {
        return typecontrat;
    }

    public Fonction typecontrat(String typecontrat) {
        this.typecontrat = typecontrat;
        return this;
    }

    public void setTypecontrat(String typecontrat) {
        this.typecontrat = typecontrat;
    }

    public String getDurrecontrat() {
        return durrecontrat;
    }

    public Fonction durrecontrat(String durrecontrat) {
        this.durrecontrat = durrecontrat;
        return this;
    }

    public void setDurrecontrat(String durrecontrat) {
        this.durrecontrat = durrecontrat;
    }

    public LocalDate getDatenaissane() {
        return datenaissane;
    }

    public Fonction datenaissane(LocalDate datenaissane) {
        this.datenaissane = datenaissane;
        return this;
    }

    public void setDatenaissane(LocalDate datenaissane) {
        this.datenaissane = datenaissane;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public Fonction phonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public Fonction email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSalary() {
        return salary;
    }

    public Fonction salary(Long salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public Fonction department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fonction fonction = (Fonction) o;
        if (fonction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fonction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fonction{" +
            "id=" + getId() +
            ", poste='" + getPoste() + "'" +
            ", stardate='" + getStardate() + "'" +
            ", typecontrat='" + getTypecontrat() + "'" +
            ", durrecontrat='" + getDurrecontrat() + "'" +
            ", datenaissane='" + getDatenaissane() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", salary='" + getSalary() + "'" +
            "}";
    }
}
