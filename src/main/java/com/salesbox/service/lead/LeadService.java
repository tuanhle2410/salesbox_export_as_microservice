package com.salesbox.service.lead;

import com.salesbox.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: luult
 * Date: 7/16/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LeadService extends BaseService
{

}
