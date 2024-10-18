package com.example.Army.SecuritySystem.ArmyRegisterationSystem;

import com.example.Army.SecuritySystem.Member.MemberDegree;
import lombok.Data;

@Data
public class MemberRequest {
    private String memberFirstName;
    private String memberLastName;
    private String memberArmyId;
    private String memberEmail;
    private String memberPassword;
    private MemberDegree memberDegree;
}
