package com.example.Army.SecuritySystem.ArmyRegisterationSystem;

import com.example.Army.SecuritySystem.Member.*;
import com.example.Army.SecuritySystem.RegisterationException.*;
import com.example.Army.SecuritySystem.Token.ConfirmationToken;
import com.example.Army.SecuritySystem.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberRegisterationService {
    private final EmailValidator emailValidator;
    private final MemberService memberService;
//    private final MemberRepository memberRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public void addMember(MemberRequest memberRequest) throws NotValidEmailException {

        Boolean isValidEmail = emailValidator.test(memberRequest.getMemberEmail());
        if(!isValidEmail){
            throw new NotValidEmailException();
        }
        Member member = new Member(
                memberRequest.getMemberFirstName(),
                memberRequest.getMemberLastName(),
                memberRequest.getMemberArmyId(),
                memberRequest.getMemberEmail(),
                memberRequest.getMemberPassword(),
                memberRequest.getMemberDegree()
        );
        memberService.signUp(member);
    }


    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    public void confirmToken(String token) throws ExpiredTokenException, NotFoundTokenException, AlreadyConfirmedTokenException {
        ConfirmationToken confirmationToken =confirmationTokenService.getConfirmationToken(token);
        if(confirmationToken==null){
            throw new NotFoundTokenException();
        }
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new ExpiredTokenException();
        }
        LocalDateTime confirmedAt = confirmationToken.getConfirmedAt();
        if(confirmedAt!=null){
            throw new AlreadyConfirmedTokenException();
        }
        confirmationTokenService.confirmToken(confirmationToken);
        memberService.enableMember(confirmationToken.getMember());
    }
}
