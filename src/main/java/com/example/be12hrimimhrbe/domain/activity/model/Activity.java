package com.example.be12hrimimhrbe.domain.activity.model;

import com.example.be12hrimimhrbe.domain.company.model.Company;
import com.example.be12hrimimhrbe.domain.member.model.Member;
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
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;


    @Enumerated(EnumType.STRING)
    private Type type;

    // 임시
    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    private String title;

    @Lob
    private String description;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    // 수행 시간
    private int performedAt;

    private int donation;

    // 
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt ;


    public enum Type { VOLUNTEER, DONATION, EDUCATION }

    // 임시 환경 교육(E), 다양성 교육(사회 S), 윤리경영   교육(지배구조 C)
    public enum EducationType { ENVIRONMENTAL_EDUCATION, SOCIAL_EDUCATION, GOVERNANCE_EDUCATION}
    // 임시 부서도 추가해주면좋겠다 ㅠㅠㅠㅠ
    public int score;

    public enum Status { PENDING, APPROVED, REJECTED }

    public Activity(Activity activity,Activity.Status update){
        this.idx = activity.idx;
        this.member = activity.member;
        this.type = activity.type;
        this.title = activity.title;
        this.description = activity.description;
        this.fileUrl = activity.fileUrl;
        this.status = update;
        this.performedAt = activity.performedAt;
        this.donation = activity.donation;
    }
}