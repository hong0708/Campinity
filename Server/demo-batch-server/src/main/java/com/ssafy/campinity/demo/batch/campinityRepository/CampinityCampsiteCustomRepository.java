package com.ssafy.campinity.demo.batch.campinityRepository;

import com.ssafy.campinity.core.dto.CampsiteHighestRankingReqDTO;
import com.ssafy.campinity.core.dto.CampsiteHottestRankingReqDTO;
import com.ssafy.campinity.core.dto.CampsiteRankingResDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CampinityCampsiteCustomRepository {

    @PersistenceContext(unitName = "campinityEntityManager")
    private EntityManager em;

    private static Logger logger = LogManager.getLogger(CampinityCampsiteCustomRepository.class);

    public List<CampsiteRankingResDTO> findCampsiteHottestRankingData(LocalDate start, LocalDate end) {
        String sql = "SELECT c.uuid AS campsiteId, c.first_image_url AS firstImageUrl, c.camp_name AS campName,\n" +
                "c.do_name AS doName, c.sigungu_name AS sigunguName, \n" +
                "(SELECT COUNT(*) FROM question WHERE question.campsite_id = c.id AND \n" +
                " DATE(question.created_at) BETWEEN '" + start  + "' AND '" + end + "') AS questionCnt,\n" +
                "(SELECT COUNT(*) FROM question JOIN answer ON question.campsite_id = c.id AND question.id = answer.question_id AND\n" +
                " DATE(answer.created_at) BETWEEN '" + start + "' AND '" + end + "') AS answerCnt,\n" +
                "(SELECT COUNT(*) FROM fcm_token WHERE fcm_token.campsite_uuid = c.uuid) AS fcmTokenCnt, " +
                "(SELECT COUNT(*) FROM message WHERE message.campsite_id = c.id AND\n" +
                " DATE(message.created_at) BETWEEN '" + start + "' AND '" + end + "') AS messageCnt FROM campsite AS c;";
        Query query = em.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<CampsiteHottestRankingReqDTO> results = jpaResultMapper.list(query, CampsiteHottestRankingReqDTO.class);

        Collections.sort(results, new Comparator<CampsiteHottestRankingReqDTO>() {
            @Override
            public int compare(CampsiteHottestRankingReqDTO o1, CampsiteHottestRankingReqDTO o2) {
                return (3 * (o2.getQuestionCnt() + o2.getAnswerCnt()) + 2 * o2.getMessageCnt() + 2 * o2.getFcmTokenCnt()) -
                        (3 * (o1.getQuestionCnt() + o1.getAnswerCnt()) + 2 * o1.getMessageCnt() + 2 * o2.getFcmTokenCnt());
            }
        });

        List<CampsiteRankingResDTO> campsiteRankingDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CampsiteHottestRankingReqDTO camp = results.get(i);
            campsiteRankingDatas.add(CampsiteRankingResDTO.builder().campsiteId(camp.getCampsiteId()).campName(camp.getCampName())
                    .doName(camp.getDoName()).sigunguName(camp.getSigunguName()).firstImageUrl(camp.getFirstImageUrl())
                    .ranking(i + 1).build());
            logger.info("--------------" + camp.getCampsiteId() + "--------------" + camp.getMessageCnt() + "--------------" +
                    camp.getAnswerCnt() + "--------------" + camp.getQuestionCnt() +  "--------------" +
                    camp.getFcmTokenCnt() + "--------------" + camp.getMessageCnt());
        }

        return campsiteRankingDatas;
    }

    public List<CampsiteRankingResDTO> findCampsiteHighestRankingData(LocalDate start, LocalDate end) {
        String sql = "SELECT c.uuid AS campsiteId, c.first_image_url AS firstImageUrl, c.camp_name AS campName, \n" +
                "c.do_name AS doName, c.sigungu_name AS sigunguName, (SELECT COALESCE(AVG(review.rate), 0) \n" +
                "FROM review WHERE c.id = review.campsite_id AND Date(review.created_at) BETWEEN '" + start
                + "' AND '" + end + "') AS totalRate\n" +
                "FROM campsite AS c;";

        Query query = em.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<CampsiteHighestRankingReqDTO> results = jpaResultMapper.list(query, CampsiteHighestRankingReqDTO.class);

        Collections.sort(results, new Comparator<CampsiteHighestRankingReqDTO>() {
            @Override
            public int compare(CampsiteHighestRankingReqDTO o1, CampsiteHighestRankingReqDTO o2) {
                if (o2.getTotalRate() - o1.getTotalRate() == 0.0) {
                    return 0;
                } else if (o2.getTotalRate() - o1.getTotalRate() > 0.0){
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        List<CampsiteRankingResDTO> campsiteHighestRankingData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CampsiteHighestRankingReqDTO camp = results.get(i);
            campsiteHighestRankingData.add(CampsiteRankingResDTO.builder().campsiteId(camp.getCampsiteId()).ranking(i + 1)
                    .doName(camp.getDoName()).sigunguName(camp.getSigunguName()).campName(camp.getCampName())
                    .firstImageUrl(camp.getFirstImageUrl()).build());
            logger.info("--------------" + camp.getCampsiteId() + "--------------" + camp.getTotalRate());
        }

        return campsiteHighestRankingData;
    }
}
