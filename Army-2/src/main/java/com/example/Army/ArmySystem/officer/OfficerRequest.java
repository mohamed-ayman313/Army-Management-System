package com.example.Army.ArmySystem.officer;

import com.example.Army.ArmySystem.position.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OfficerRequest {
    private String officerFirstName;
    private String officerLastName;
    private String officerArmyId;
    private List<Position> positions;

}
