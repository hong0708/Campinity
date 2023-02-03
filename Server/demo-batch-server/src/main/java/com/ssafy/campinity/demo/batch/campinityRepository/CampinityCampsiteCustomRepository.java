package com.ssafy.campinity.demo.batch.campinityRepository;

import com.ssafy.campinity.core.dto.CampsiteHighestRankingReqDTO;
import com.ssafy.campinity.core.dto.CampsiteHottestRankingReqDTO;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CampinityCampsiteCustomRepository {

    @PersistenceContext(unitName = "campinityEntityManager")
    private EntityManager em;

    public List<CampsiteHottestRankingReqDTO> findCampsiteHottestRankingData(LocalDate start, LocalDate end) {
        String sql = "SELECT c.uuid AS campsiteId, c.first_image_url AS firstImageUrl, c.camp_name AS campName,\n" +
                "c.do_name AS doName, c.sigungu_name AS sigunguName, \n" +
                "(SELECT COUNT(*) FROM question WHERE question.campsite_id = c.id AND \n" +
                " DATE(question.created_at) BETWEEN '" + start  + "' AND '" + end + "') AS questionCnt,\n" +
                "(SELECT COUNT(*) FROM question JOIN answer ON question.campsite_id = c.id AND question.id = answer.question_id AND\n" +
                " DATE(answer.created_at) BETWEEN '" + start + "' AND '" + end + "') AS answerCnt,\n" +
                "(SELECT COUNT(*) FROM message WHERE message.campsite_id = c.id AND\n" +
                " DATE(message.created_at) BETWEEN '" + start + "' AND '" + end + "') AS messageCnt FROM campsite AS c;";
        Query query = em.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<CampsiteHottestRankingReqDTO> results = jpaResultMapper.list(query, CampsiteHottestRankingReqDTO.class);

        return results;
    }

    public List<CampsiteHighestRankingReqDTO> findCampsiteHighestRankingData(LocalDate start, LocalDate end) {
        String sql = "SELECT c.uuid AS campsiteId, c.first_image_url AS firstImageUrl, c.camp_name AS campName, \n" +
                "c.do_name AS doName, c.sigungu_name AS sigunguName, (SELECT COALESCE(AVG(review.rate), 0) \n" +
                "FROM review WHERE c.id = review.campsite_id AND Date(review.created_at) BETWEEN '" + start
                + "' AND '" + end + "') AS totalRate\n" +
                "FROM campsite AS c;";

        Query query = em.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<CampsiteHighestRankingReqDTO> results = jpaResultMapper.list(query, CampsiteHighestRankingReqDTO.class);

        return results;
    }
}
