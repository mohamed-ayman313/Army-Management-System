package com.example.Army.ArmySystem.ncofficer;
import com.example.Army.ArmySystem.position.Position;
import lombok.Data;
import java.util.List;

@Data
public class NcofficerRequest {
    private String ncoFirstName;
    private String ncoLastName;
    private String ncoArmyId;
    private List<Position> positions;
}
