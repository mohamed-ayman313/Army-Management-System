package com.example.Army.ArmySystem.soldier;

import com.example.Army.ArmySystem.position.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoldierGetRequest {
    private String soldierFirstName;
    private String soldierLastName;
    private String soldierArmyId;
    private Position position;
}
