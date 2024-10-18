package com.example.Army.ArmySystem.officer;
import com.example.Army.ArmySystem.position.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UniqueOfficerArmyId",
                        columnNames = {"officerArmyId"}
                )
        }
)
@Data
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officerId;
    private String officerFirstName;
    private String officerLastName;
    private String officerArmyId;
    @JsonIgnore
    @OneToMany(mappedBy = "officer")
//    @OneToMany
//    @JoinColumn
    private List<Position> positions;


    public Officer() {
    }

    public Officer(String officerFirstName, String officerLastName, String officerArmyId, List<Position> positions) {
        this.officerFirstName = officerFirstName;
        this.officerLastName = officerLastName;
        this.officerArmyId = officerArmyId;
        this.positions = positions;
    }

}
