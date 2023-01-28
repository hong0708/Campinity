package com.ssafy.campinity.core.repository.campsiteGroup;

import com.ssafy.campinity.core.entity.campsiteGroup.CampsiteGroup;
import org.springframework.data.repository.CrudRepository;

public interface campsiteGroupRedisRepository extends CrudRepository<CampsiteGroup, String> {
}
