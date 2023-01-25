package com.daeho.JPALocalProject1.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.daeho.JPALocalProject1.entity.ClubMember;
import com.daeho.JPALocalProject1.entity.ClubMemberRole;
import com.daeho.JPALocalProject1.repository.ClubMemberRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        //1 - 80������ USER�� ����
        //81- 90������ USER,MANAGER
        //91- 100������ USER,MANAGER,ADMIN

        IntStream.rangeClosed(1,100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@zerock.org")
                    .name("�����"+i)
                    .fromSocial(false)
                    .roleSet(new HashSet<ClubMemberRole>())
                    .password(  passwordEncoder.encode("1111") )
                    .build();

            //default role
            clubMember.addMemberRole(ClubMemberRole.USER);

            if(i > 80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if(i > 90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);

        });

    }

    @Test
    public void testRead() {

        Optional<ClubMember> result = repository.findByEmail("user95@zerock.org", false);

        ClubMember clubMember = result.get();

        System.out.println(clubMember);

    }


}
