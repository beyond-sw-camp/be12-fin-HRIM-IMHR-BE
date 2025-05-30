package com.example.be12hrimimhrbe.domain.notification;

import com.example.be12hrimimhrbe.domain.activity.model.ActivityDto;
import com.example.be12hrimimhrbe.domain.member.model.CustomUserDetails;
import com.example.be12hrimimhrbe.domain.member.model.Member;
import com.example.be12hrimimhrbe.domain.notification.model.NotificationDto;
import com.example.be12hrimimhrbe.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
@Tag(name = "웹소켓 알람", description = "웹소켓 알람 컨트롤러")
public class NotificationController {
    private final NotificationService notificationService;

    @MessageMapping("/notification/activity/{memberIdx}")
    @Operation(summary = "활동내역 승인", description = "활동내역 승인 알림 기능 입니다.")
    public void activityApprove(@Payload NotificationDto.ActivityApproveReq dto) {

        notificationService.activityApprove(dto);
    }

    @MessageMapping("/notification/activityReq/{companyIdx}")
    @Operation(summary = "활동내역 승인 요청", description = "활동내역 승인 요청 알림 기능 입니다.")
    public void activityApproveReq(@Payload NotificationDto.ActivityApproveRequest dto
            , @DestinationVariable Long companyIdx) {
        notificationService.activityApproveReq(dto,companyIdx);
    }

    @MessageMapping("/notification/signup/{companyCode}")
    @Operation(summary = "활동내역 승인", description = "활동내역 승인 알림 기능 입니다.")
    public void signupApprove(@Payload NotificationDto.SignupApproveReq dto, @DestinationVariable String companyCode) {

        notificationService.signupApprove(companyCode, dto);
    }

    @MessageMapping("/notification/eventRegist/{companyIdx}")
    @Operation(summary = "회사 캠패인 추가", description = "회사 캠패인 추가 시 알림 기능 입니다.")
    public void EventRegist(@Payload NotificationDto.SignupApproveReq dto, @DestinationVariable Long companyIdx) {

        notificationService.EventRegist(companyIdx, dto);
    }

    @MessageMapping("/notification/eventcampaignMemberAdd/{eventIdx}")
    @Operation(summary = "회사 캠패인 추가", description = "회사 캠패인 추가 시 알림 기능 입니다.")
    public void eventcampaignMemberAdd(@Payload NotificationDto.eventcampaignMemberAddReq dto, @DestinationVariable Long eventIdx) {

        notificationService.eventcampaignMemberAddRegist(eventIdx, dto);
    }

    @GetMapping("/list")
    @Operation(summary = "유저의 알림 리스트", description = "해당 유저의 아림 리스트 입니다.")
    public ResponseEntity<BaseResponse<List<NotificationDto.NotificationResp>>> getMyNotifications(
            @AuthenticationPrincipal CustomUserDetails Member, @RequestParam("page") int page
    ) {
        return ResponseEntity.ok().body(notificationService.getMyNotifications(Member.getMember(), page, 9));
    }

    @PostMapping("/isRead/{idx}")
    @Operation(summary = "알림 읽음 처리", description = "알림을 읽음 처리하는 기능 입니다.")
    public ResponseEntity<BaseResponse<?>> isRead(
            @AuthenticationPrincipal CustomUserDetails Member,@Parameter(example = "1") @PathVariable Long idx
    ) {
        return ResponseEntity.ok().body(notificationService.isRead(Member.getMember(),idx));
    }

    @DeleteMapping("/remove/{idx}")
    @Operation(summary = "알림 삭제", description = "알림을 제거하는 기능 입니다.")
    public ResponseEntity<BaseResponse<?>> remove(
            @AuthenticationPrincipal CustomUserDetails Member, @PathVariable Long idx
    ) {
        return ResponseEntity.ok().body(notificationService.remove(idx));
    }
}
