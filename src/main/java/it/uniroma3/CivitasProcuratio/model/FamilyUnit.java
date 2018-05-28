package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="nuclei_familiari")
@Proxy(lazy = false)
public class FamilyUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @OneToMany(mappedBy = "familyUnit")
    private List<Migrant> migrants;

    @OneToOne
    @JoinColumn(name = "family_head_id")
    private Migrant familyHead;

    public FamilyUnit() {
    }

    public FamilyUnit(List<Migrant> migrants, Migrant familyHead) {
        this.migrants = migrants;
        this.familyHead = familyHead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Migrant> getMigrants() {
        return migrants;
    }

    public void setMigrants(List<Migrant> migrants) {
        this.migrants = migrants;
    }

    public Migrant getFamilyHead() {
        return familyHead;
    }

    public void setFamilyHead(Migrant familyHead) {
        this.familyHead = familyHead;
    }

}
