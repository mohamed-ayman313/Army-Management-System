package com.example.Army.ArmySystem.position;
import com.example.Army.ArmySystem.ncofficer.Ncofficer;
import com.example.Army.ArmySystem.officer.Officer;
import com.example.Army.ArmySystem.soldier.Soldier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "Position")
@Data
public class Position {
    @Id

    private String positionName;
    private LocalDateTime startedAt;
    @JsonIgnore
    @OneToOne(mappedBy = "position")
    private Soldier soldier;
    @JsonIgnore
    @ManyToOne
//    @JoinColumn
    private Officer officer;
    @JsonIgnore
    @ManyToMany
//    @ManyToMany(mappedBy = "positions")
    private List<Ncofficer> ncofficers;

    public Position() {
    }

    public Position(String positionName) {
        this.positionName = positionName;
        this.startedAt = LocalDateTime.now();
        this.ncofficers = new ArrayList<>();
    }
}
