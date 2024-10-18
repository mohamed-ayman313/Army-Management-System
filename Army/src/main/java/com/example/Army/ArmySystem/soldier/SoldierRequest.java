package com.example.Army.ArmySystem.soldier;

import com.example.Army.ArmySystem.position.Position;
import lombok.Data;

@Data
public class SoldierRequest {
    private String soldierFirstName;
    private String soldierLastName;
    private String soldierArmyId;
    private Position position;
}
