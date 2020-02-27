package com.salesbox.service.task;

import com.salesbox.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hunglv on 8/13/14.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ShowTaskServiceInternal extends BaseService
{

}
