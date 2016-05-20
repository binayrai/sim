package com.elite.service;

import com.elite.domain.Parent;
import com.elite.dto.*;

/**
 * Created by binaychap on 2/24/2016.
 */
public interface ParentService {

    PagedResultDTO<ParentDTO> list(ParentSearchRequest parentSearchRequest);

    void save(ParentDTO dto);

    ParentDTO get(long id);

    boolean deleteParent(long parentId);

    ParentSurveyDTO findAllByParentId(long id);
}
