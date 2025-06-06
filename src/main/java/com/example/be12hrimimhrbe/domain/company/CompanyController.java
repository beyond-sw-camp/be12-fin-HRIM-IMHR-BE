package com.example.be12hrimimhrbe.domain.company;

import com.example.be12hrimimhrbe.domain.company.model.CompanyDto;
import com.example.be12hrimimhrbe.domain.member.model.CustomUserDetails;
import com.example.be12hrimimhrbe.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Tag(name = "회사 관리 기능")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/fetchMyCompany")
    @Operation(summary = "내 기업 조회", description = "자신이 속한 기업 조회하는 기능입니다.")
    public ResponseEntity<BaseResponse<CompanyDto.CompanyResponse>> fetchMyCompany(
            @AuthenticationPrincipal CustomUserDetails member
    ) {
        return ResponseEntity.ok().body(companyService.fetchMyCompany(member.getMember()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/scoreUpdate")
    @Operation(summary = "기업 목표 점수 수정", description = "기업 목표 점수를 수정하는 기능입니다.")
    public ResponseEntity<BaseResponse<String>> updateScore(
            @AuthenticationPrincipal CustomUserDetails member,
            @RequestBody CompanyDto.CompanyResponse dto
    ) {
        return ResponseEntity.ok().body(companyService.updateScore(member.getMember(), dto));
    }

    // EsgCompany 와 Company 통합 조회 기능
    @GetMapping("/list")
    @Operation(summary = "전체 기업 리스트", description = "페이지별로 전체 기업을 조회하는 기능입니다.")
    public ResponseEntity<BaseResponse<Page<CompanyDto.CompanyListResponse>>> allList(
            @AuthenticationPrincipal CustomUserDetails member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(companyService.allList(pageable, member.getMember(), keyword));
    }

    // 하나의 APi만 호출할때에 주석처리 해야함
    @GetMapping("/monthDashboard")
    @Operation(summary = "내 회사의 연도 및 월별 대시보드 조회", description = "연도 및 월별 내 회사의 대시보드를 조회하는 기능입니다.")
    public ResponseEntity<BaseResponse<CompanyDto.CompanyYearResponse>> monthDashboard (
            @AuthenticationPrincipal CustomUserDetails member,
            @RequestParam int month,
            @RequestParam int year
    ) {
        return ResponseEntity.ok(companyService.monthDashboard(member.getMember(), year, month));
    }

}
