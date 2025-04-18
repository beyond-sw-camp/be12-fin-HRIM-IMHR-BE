package com.example.be12hrimimhrbe.domain.member;

import com.example.be12hrimimhrbe.domain.company.model.Company;
import com.example.be12hrimimhrbe.domain.member.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberIdAndIsAdminAndStatus(String memberId, Boolean isAdmin, Member.Status status);
    Optional<Member> findByNameAndEmailAndIsAdmin(String name, String email, Boolean isAdmin);
    Optional<Member> findByMemberIdAndEmailAndIsAdmin(String memberId, String email, Boolean isAdmin);
    Optional<Member> findByEmailAndIsAdmin(String email, Boolean isAdmin);
    Optional<Member> findByMemberIdAndIsAdmin(String memberId, Boolean isAdmin);
    List<Member> findAllByCompany(Company company);

    Member findByIdx(Long idx);

    @EntityGraph(attributePaths = {"company"})
    @Query("SELECT m from Member m " +
            "LEFT JOIN m.company c " +
            "where c.code=:companyCode")
    List<Member> findAllByCompanyCode(String companyCode);
}
