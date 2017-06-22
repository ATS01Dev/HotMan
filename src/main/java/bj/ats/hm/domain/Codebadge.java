package bj.ats.hm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Codebadge.
 */
@Entity
@Table(name = "codebadge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "codebadge")
public class Codebadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @ManyToOne
    private Badge badges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Codebadge description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public Codebadge code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Badge getBadges() {
        return badges;
    }

    public Codebadge badges(Badge badge) {
        this.badges = badge;
        return this;
    }

    public void setBadges(Badge badge) {
        this.badges = badge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Codebadge codebadge = (Codebadge) o;
        if (codebadge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codebadge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Codebadge{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
