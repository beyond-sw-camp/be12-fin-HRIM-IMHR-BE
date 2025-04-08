package com.example.be12hrimimhrbe.domain.member.model;

import com.example.be12hrimimhrbe.domain.department.model.Department;
import com.example.be12hrimimhrbe.domain.company.model.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @Column(name = "member_idx", nullable = false)
    private String memberIdx;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.EMPLOYEE;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @ManyToOne
    @JoinColumn(name = "company_idx")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "department_idx")
    private Department department;

    private LocalDateTime joinedAt = LocalDateTime.now();

    public enum Role { EMPLOYEE, MANAGER, EXECUTIVE, MASTER }
    public enum Status { PENDING, APPROVED }

    private int individualScore;

    @Column(length = 20)
    private String code;
}
