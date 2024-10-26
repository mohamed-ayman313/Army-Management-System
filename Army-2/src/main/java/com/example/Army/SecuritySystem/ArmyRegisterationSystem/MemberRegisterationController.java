package com.example.Army.SecuritySystem.ArmyRegisterationSystem;

import com.example.Army.SecuritySystem.Member.Member;
import com.example.Army.SecuritySystem.Member.MemberService;
import com.example.Army.SecuritySystem.RegisterationException.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberRegisterationController {
    private final MemberRegisterationService memberRegisterationService;
    private final MemberService memberService;
    @GetMapping("/getAllMembers")
    public List<Member> getAllMembers(){
        return memberRegisterationService.getAllMembers();
    }

    @PostMapping("/addMember")
    public String addMember(@RequestBody MemberRequest memberRequest){
        try {
            memberRegisterationService.addMember(memberRequest);
            return "Member added successfully";
        }catch (NotValidEmailException e){
            return "Not Valid Email";
        }

    }
    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam String token) {
        try {
            memberRegisterationService.confirmToken(token);
            return "Token confirmed successfully";
        } catch (NotFoundTokenException e) {
            return "Token is not found";
        } catch (ExpiredTokenException e){
            return "Token is expired";
        } catch (AlreadyConfirmedTokenException e) {
            return "Token is already confirmed";
        }
    }

}
