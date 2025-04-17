package com.example.be12hrimimhrbe.domain.campaign.model;

import com.example.be12hrimimhrbe.domain.member.model.Member;
import com.example.be12hrimimhrbe.domain.event.model.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long eventIdx;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "event_idx")
    private Event event;
}