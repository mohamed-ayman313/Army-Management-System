package com.example.Army.ArmySystem.soldier;

import com.example.Army.ArmySystem.position.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
//        name = "Soldier",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UniqueSoldierArmyId",
                        columnNames = {"soldierArmyId"}
                )
        }
)
@Data
public class Soldier {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer soldierId;
    private String soldierFirstName;
    private String soldierLastName;
    private String soldierArmyId;
    @JsonIgnore
    @OneToOne
    private Position position;

    public Soldier() {
    }

    public Soldier(String soldierFirstName, String soldierLastName, String soldierArmyId, Position position) {
        this.soldierFirstName = soldierFirstName;
        this.soldierLastName = soldierLastName;
        this.soldierArmyId = soldierArmyId;
        this.position = position;
    }

}
