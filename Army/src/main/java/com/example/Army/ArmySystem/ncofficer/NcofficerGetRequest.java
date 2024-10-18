package com.example.Army.ArmySystem.ncofficer;

import com.example.Army.ArmySystem.position.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NcofficerGetRequest {
    private String ncoFirstName;
    private String ncoLastName;
    private String ncoArmyId;
    private List<Position> ncoPositions;

}
