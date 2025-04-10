package com.example.be12hrimimhrbe.domain.activity.model;

import com.example.be12hrimimhrbe.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    @Lob
    private String description;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    // 수행 시간
    @Column(updatable = false)
    private String performedAt;

    // 활동 시작 시간
    private LocalDate startDate;

    // 
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt ;

    private int ScoreType;

    public enum Type { VOLUNTEER, DONATION, EDUCATION }
    public enum Status { PENDING, APPROVED, REJECTED }

    // 해당 타입
    public enum ScoreType{E,S,G}
}