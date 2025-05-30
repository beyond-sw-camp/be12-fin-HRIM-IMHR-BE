package com.example.be12hrimimhrbe.domain.member;

import com.example.be12hrimimhrbe.domain.company.model.Company;
import com.example.be12hrimimhrbe.domain.department.model.Department;
import com.example.be12hrimimhrbe.domain.member.model.Member;
import com.example.be12hrimimhrbe.domain.score.model.Score;
import org.springframework.data.domain.Pageable;
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
    List<Member> findAllByDepartmentAndIdxNotAndIsAdmin(Department department, Long idx, Boolean isAdmin);
    List<Member> findAllByDepartmentAndIsAdmin(Department department, Boolean isAdmin);

    Member findByIdx(Long idx);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.company c " +
            "LEFT JOIN FETCH m.department d " +
            "where c.code=:companyCode")
    List<Member> findAllByCompanyCode(String companyCode);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.company c " +
            "LEFT JOIN FETCH m.department d " +
            "where c.idx=:companyIdx")
    List<Member> findAllByCompanyIdx(Long companyIdx);

    List<Member> findAllByIdxInAndIsAdminFalse(List<Long> memberIds);

    // 1, 2, 3 등 맴버 조회
    @Query("SELECT m FROM Member m WHERE m.company.idx = :companyIdx " +
            "AND m.status = 'APPROVED'" +
            "ORDER BY (m.eScore + m.sScore + m.gScore) DESC")
    List<Member> findTop3ByCompanyOrderByTotalScore(Long companyIdx, Pageable pageable);


    List<Member> findAllByDepartmentIdx(Long departmentIdx);

    List<Member> findAllByDepartment(Department d);
}
