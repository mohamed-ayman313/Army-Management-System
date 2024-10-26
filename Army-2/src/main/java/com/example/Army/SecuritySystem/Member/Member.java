package com.example.Army.SecuritySystem.Member;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberID;
    private String memberFirstName;
    private String memberLastName;
    private String memberArmyId;
    @Column(unique = true)
    private String memberEmail;
    private String memberPassword;
    @Enumerated(EnumType.STRING)
    private MemberDegree memberDegree;
//    @Enumerated(EnumType.STRING)
//    private MemberRole memberRole;
    private boolean locked;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private LocalDateTime AccountLifeTime;
    private LocalDateTime credentialsLifeTime;

    public Member(String memberFirstName, String memberLastName, String memberArmyId, String memberEmail, String memberPassword, MemberDegree memberDegree) {
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberArmyId = memberArmyId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberDegree = memberDegree;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
    }

    public Member() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(memberDegree.name());
        System.out.println("****** "+memberDegree.name()+" of "+ memberEmail);
//        SimpleGrantedAuthority authority2 = new SimpleGrantedAuthority(memberRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
