package com.example.be12hrimimhrbe.domain.score;

import com.example.be12hrimimhrbe.domain.member.model.Member;
import com.example.be12hrimimhrbe.domain.score.model.Score;
import com.example.be12hrimimhrbe.domain.score.model.ScoreDto;
import com.example.be12hrimimhrbe.global.response.BaseResponse;
import com.example.be12hrimimhrbe.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public BaseResponse<ScoreDto.DashBoardRsp> dashboard(Long companyIdx) {
        List<Score> scores=scoreRepository.findByCompany(companyIdx);
        List<ScoreDto.ChangeScoreRsp> list = new ArrayList<>();
        String companyName = null;
        for (Score score : scores) {
            if (companyName==null) {
                companyName= score.getCompany().getName();
            }
            ScoreDto.ChangeScoreRsp dto= new ScoreDto.ChangeScoreRsp(score);
            list.add(dto);
        }

        list.sort(Comparator.comparingInt(ScoreDto.ChangeScoreRsp::getYear));

        ScoreDto.DashBoardRsp result=ScoreDto.DashBoardRsp.builder()
                .companyName(companyName)
                .changeScoreRsp(list)
                .build();
        System.out.println("실행");
        return new BaseResponse<>(BaseResponseMessage.COMPANY_ALL_LIST_SUCCESS, result);
    }

    public BaseResponse<String> eScore(Long companyIdx) {
        List<Score> scores=scoreRepository.findByCompany(companyIdx);
        Score lastScore = new Score();
        if (scores != null && !scores.isEmpty()) {
            lastScore = scores.get(scores.size() - 1);
            // 이제 lastScore를 사용할 수 있음
        } else {
            // 리스트가 비어있을 경우 처리
            System.out.println("점수 리스트가 비어 있습니다.");
        }

        return new BaseResponse<>(BaseResponseMessage.COMPANY_ALL_LIST_SUCCESS, lastScore.getEnvironmentScore());
    }
}
