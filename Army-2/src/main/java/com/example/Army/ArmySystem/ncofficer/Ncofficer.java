package com.example.Army.ArmySystem.ncofficer;

import com.example.Army.ArmySystem.position.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "UniqueNonCommissionedOfficerArmyId",
                columnNames = {"ncoArmyId"}
        )
})
@Data
public class Ncofficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ncoId;
    private String ncoFirstName;
    private String ncoLastName;
    private String ncoArmyId;
    @ManyToMany(mappedBy = "ncofficers")
    private List<Position> positions;

    public Ncofficer() {
    }

    public Ncofficer(String ncoFirstName, String ncoLastName, String ncoArmyId, List<Position> positions) {
        this.ncoFirstName = ncoFirstName;
        this.ncoLastName = ncoLastName;
        this.ncoArmyId = ncoArmyId;
        this.positions = positions;
    }

}
