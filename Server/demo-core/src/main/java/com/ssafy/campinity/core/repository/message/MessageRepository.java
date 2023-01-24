package com.ssafy.campinity.core.repository.message;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findMessagesByCampsiteAndLatitudeBetweenAndLongitudeBetweenAndExpiredIsFalse(
            Campsite campsite,
            double bottomRightLat,
            double topLeftLat,
            double topLeftLng,
            double bottomRightLng);

    Optional<Message> findByUuidAndExpiredIsFalse(UUID messageId);

    List<Message> findByCampsite_idAndExpiredIsFalse(int campsiteId);

    void deleteById(Integer id);
}
