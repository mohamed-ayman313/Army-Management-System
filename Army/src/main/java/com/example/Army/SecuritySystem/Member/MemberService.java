package com.example.Army.SecuritySystem.Member;

import com.example.Army.SecuritySystem.RegisterationException.ExistTokenException;
import com.example.Army.SecuritySystem.Token.ConfirmationToken;
//import com.example.Army.SecuritySystem.Token.ConfirmationTokenRepository;
import com.example.Army.SecuritySystem.Token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberDao memberDao;
//    private final MemberRepository memberRepository;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberDao.getMember(username);
//        Member member = memberRepository.findByMemberEmail(username);
//        if(member.getAccountLifeTime().isBefore(LocalDateTime.now())){
//            member.setAccountNonExpired(false);
//        }
//        if(member.getCredentialsLifeTime().isBefore(LocalDateTime.now())){
//            member.setCredentialsNonExpired(false);
//        }
//        return member;
    }

    public void signUp(Member member)  {

        member.setMemberPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setAccountLifeTime(LocalDateTime.now().plusMinutes(1));
        member.setCredentialsLifeTime(LocalDateTime.now().plusMinutes(5));
        memberDao.addMember(member);
//        memberRepository.save(member);
        String token = UUID.randomUUID().toString();
        System.out.println(token);

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),member);
        confirmationTokenService.saveToken(confirmationToken);
    }
    @Transactional
    public void enableMember(Member member) {
//        memberRepository.findByMemberEmail(member.getMemberEmail()).setEnabled(true);
        Member updatedMember=memberDao.getMember(member.getMemberEmail());
        updatedMember.setEnabled(true);
        memberDao.updateMember(updatedMember);
//        member.setEnabled(true);
    }

    public List<Member> getAllMembers() {
//        return memberRepository.findAll();
        return memberDao.getAllMembers();
    }
}
