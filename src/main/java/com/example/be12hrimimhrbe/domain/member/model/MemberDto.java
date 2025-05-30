package com.example.be12hrimimhrbe.domain.member.model;

import com.example.be12hrimimhrbe.domain.activity.model.Activity;
import com.example.be12hrimimhrbe.domain.activity.model.ActivityDto;
import com.example.be12hrimimhrbe.domain.activity.model.EsgActivity;
import com.example.be12hrimimhrbe.domain.campaign.model.Campaign;
import com.example.be12hrimimhrbe.domain.company.model.Company;
import com.example.be12hrimimhrbe.domain.department.model.Department;
import com.example.be12hrimimhrbe.domain.department.model.DepartmentDto;
import com.example.be12hrimimhrbe.domain.event.model.Event;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberShortResponse {
        private Long idx;
        private Member.Status status;
        private String memberId;
        private Boolean isAdmin;
        private String name;
        private DepartmentDto.DepartmentInfoResponse department;
        private LocalDateTime joinedAt;
        public static MemberShortResponse fromEntity(Member member) {
            return MemberShortResponse.builder()
                    .idx(member.getIdx())
                    .status(member.getStatus())
                    .isAdmin(member.getIsAdmin())
                    .memberId(member.getMemberId())
                    .name(member.getName())
                    .department(member.getDepartment() == null ? null : DepartmentDto.DepartmentInfoResponse.fromEntity(member.getDepartment()))
                    .joinedAt(member.getJoinedAt())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class FindIdRequest {
        private String name;
        private String email;
        private String way;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class FindPWRequest {
        private String memberId;
        private String email;
        private String way;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ResetPasswordRequest {
        private String oldPassword;
        private String newPassword;
        private String uuid;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class InfoResponse {
        private Long idx;
        private String name;
        private String email;
        private String memberId;
        private String company;
        private DepartmentDto.DepartmentInfoResponse department;
        private Boolean isAdmin;
        private Boolean hasProdAuth;
        private Boolean hasPartnerAuth;
        private List<String> hrRoles;
        private Member.Status status;
        private LocalDate joinedAt;
        public static InfoResponse fromEntity(Member member, List<String> roles) {
            return InfoResponse.builder()
                    .idx(member.getIdx())
                    .name(member.getName())
                    .memberId(member.getMemberId())
                    .email(member.getEmail())
                    .company(member.getCompany().getName())
                    .department(member.getDepartment() == null ? null : DepartmentDto.DepartmentInfoResponse.fromEntity(member.getDepartment()))
                    .isAdmin(member.getIsAdmin())
                    .hasProdAuth(member.getHasProdAuth())
                    .hasPartnerAuth(member.getHasPartnerAuth())
                    .hrRoles(roles)
                    .status(member.getStatus())
                    .joinedAt(member.getJoinedAt().toLocalDate())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class InfoDetailResponse {
        private InfoResponse info;
        private List<DepartmentDto.DepartmentInfoResponse> departments;
        public static InfoDetailResponse fromEntity(InfoResponse info, List<Department> departments) {
            return InfoDetailResponse.builder()
                    .info(info)
                    .departments(departments.stream().map(DepartmentDto.DepartmentInfoResponse::fromEntity).toList())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class InfoDetailRequest {
        private Department department;
        private Boolean isAdmin;
        private Boolean hasProdAuth;
        private Boolean hasPartnerAuth;
        private List<String> hrRoles;
        public Member toEntity(Long idx) {
            return Member.builder()
                    .idx(idx)
                    .isAdmin(isAdmin)
                    .hasProdAuth(hasProdAuth)
                    .hasPartnerAuth(hasPartnerAuth)
                    .department(department)
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ActivityResponse {
        private Long member_idx;
        private List<ActivityItem> activities;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ActivityItem {
        private Long activityIdx;
        private String activityId;
        private Long campaignIdx;
        private String type;
        private String content;
        private String title;
        private LocalDate date;
        public static ActivityItem fromActivity(Activity activity) {
            return ActivityItem.builder()
                    .activityIdx(activity.getIdx())
                    .type(activity.getType().toString())
                    .title(activity.getTitle())
                    .content(activity.getDescription())
                    .date(activity.getCreatedAt().toLocalDate())
                    .build();
        }
        public static ActivityItem fromActivity(EsgActivity activity) {
            return ActivityItem.builder()
                    .activityId(activity.getId())
                    .type(activity.getSubject())
                    .title(activity.getSubject())
                    .date(activity.getCreatedAt().toLocalDate())
                    .build();
        }
        public static ActivityItem fromCampaign(Campaign campaign, Event event) {
            return ActivityItem.builder()
                    .campaignIdx(campaign.getIdx())
                    .content(event.getContent())
                    .title(event.getTitle())
                    .date(event.getEndDate())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class PersonalSignupRequest {
        @NotBlank(message = "이름은 필수값입니다.")
        private String name;
        @NotBlank(message = "ID는 필수값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$", message = "ID는 영어 대소문자, 숫자 혼합하여 6~20글자 사이")
        private String memberId;
        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "이메일이 양식에 맞지 않습니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 영어 대소문자, 숫자 혼합하여 8~20글자 사이")
        private String password;
        @NotBlank(message = "회사코드는 필수값입니다.")
        private String companyCode;
        private String employeeCode;
        public Member toMember(String encryptedPassword, Company company) {
            return Member.builder().memberId(this.memberId)
                    .name(this.name)
                    .email(this.email)
                    .isAdmin(false)
                    .hasPartnerAuth(false)
                    .hasProdAuth(false)
                    .status(Member.Status.APPROVED)
                    .company(company)
                    .code(employeeCode)
                    .password(encryptedPassword)
                    .joinedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class PersonalSignupResponse {
        private Long member_idx;
        private String name;
        private String memberId;
        private String email;
        private String companyCode;
        private String employeeCode;
        public static PersonalSignupResponse fromMember(Member member) {
            return PersonalSignupResponse.builder()
                    .member_idx(member.getIdx())
                    .name(member.getName())
                    .memberId(member.getMemberId())
                    .email(member.getEmail())
                    .companyCode(member.getCompany().getCode())
                    .employeeCode(member.getCode())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class CompanySignupRequest {
        @NotBlank(message = "이름은 필수값입니다.")
        private String name;
        @NotBlank(message = "ID는 필수값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$", message = "ID는 영어 대소문자, 숫자 혼합하여 6~20글자 사이")
        private String memberId;
        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 영어 대소문자, 숫자 혼합하여 8~20글자 사이")
        private String password;
        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "이메일이 양식에 맞지 않습니다.")
        private String email;
        @NotBlank(message = "회사명은 필수값입니다.")
        private String companyName;
        @NotBlank(message = "사업자등록번호는 필수값입니다.")
        private String registrationNumber;
        public Member toMember(String encryptedPassword, Company company) {
            return Member.builder().memberId(this.memberId)
                            .name(this.name)
                            .email(this.email)
                            .isAdmin(true)
                            .hasPartnerAuth(false)
                            .hasProdAuth(false)
                            .status(Member.Status.APPROVED)
                            .company(company)
                            .password(encryptedPassword)
                            .joinedAt(LocalDateTime.now())
                            .build();
        }
        public Company toCompany(String imgUrl) {
            return Company.builder()
                    .name(this.companyName)
                    .registrationNumber(registrationNumber)
                    .code(registrationNumber)
                    .imgUrl(imgUrl)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class CompanySignupResponse {
        private Long member_idx;
        private String name;
        private String memberId;
        private String email;
        private String companyName;
        private String registrationNumber;
        public static CompanySignupResponse fromMember(Member member) {
            return CompanySignupResponse.builder()
                    .member_idx(member.getIdx())
                    .name(member.getName())
                    .email(member.getEmail())
                    .memberId(member.getMemberId())
                    .companyName(member.getCompany().getName())
                    .registrationNumber(member.getCompany().getRegistrationNumber())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        private String memberId;
        private String password;
        private String way;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberReportReq{
        private String startMonth;
        private String endMonth;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberReportDetailResp{
        private String memberName;
        private String departmentName;
        private int individualScore;
        private List<String> feedbackResponseAnswerText;
        private List<ActivityDto.ActivityReportDetailResp> activityList;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberReportUserFindReq {
        private MemberReportUserFindType type;
        private String findContent;
        private String startMonth;
        private String endMonth;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberReportListResp{
        private Long memberIdx;
        private String memberName;
        private String memberId;
        private String departmentName;
        private String startMonth;
        private String endMonth;
    }

    public static enum MemberReportUserFindType{
        NAME,ID,CODE
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberReportUserFindResp {
        private Long memberIdx;
        private String memberName;
        private String memberId;
        private String departmentName;
        private String startMonth;
        private String endMonth;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class TokenInfoResponse {
        private Long idx;
        private String name;
        private String memberId;
        private String email;
        private Boolean isAdmin;
        private Boolean hasPartnerAuth;
        private Boolean hasProdAuth;
        private Member.Status status;
        private Long companyIdx;
        private Long departmentIdx;
        private List<String> hrAuthorities;

        public static TokenInfoResponse fromMember(Member member, List<String> hrAuthorities) {
            return TokenInfoResponse.builder()
                    .idx(member.getIdx())
                    .name(member.getName())
                    .memberId(member.getMemberId())
                    .email(member.getEmail())
                    .isAdmin(member.getIsAdmin())
                    .hasPartnerAuth(member.getHasPartnerAuth())
                    .hasProdAuth(member.getHasProdAuth())
                    .status(member.getStatus())
                    .companyIdx(member.getCompany().getIdx())
                    .departmentIdx(member.getDepartment().getIdx())
                    .hrAuthorities(hrAuthorities)
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class MemberScoreResponse {
        private Long memberIdx;
        private String memberName;
        private int memberSScore;
        private int memberEScore;
        private int memberGScore;
        private int averageScore;
        private int ranking;

        public static MemberScoreResponse from(Member member, int e, int s, int g) {
            return MemberScoreResponse.builder()
                    .memberIdx(member.getIdx())
                    .memberName(member.getName())
                    .memberEScore(e)
                    .memberSScore(s)
                    .memberGScore(g)
                    .averageScore((e+s+g)/3)
                    .build();
        }
    }
}
