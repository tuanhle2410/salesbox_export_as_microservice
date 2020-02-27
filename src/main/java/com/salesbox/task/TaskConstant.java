package com.salesbox.task;

/**
 * @author phongnv on 2018-01-04
 */

public interface TaskConstant
{

    public static String PRE_EXPORT_FILE_NAME = "Tasks_Export_";


    public interface RequestParam
    {
        public String PAGEINDEX = "pageIndex";
        public String TOKEN = "token";
        public String PAGESIZE = "pageSize";
        public String SESSIONKEY = "sessionKey";
        public String ALL = "all";
    }

    public interface RequestBody
    {
        public String TASKFILTERDTO = "taskFilterDTO";
        public String TASKOWNERDTO = "taskOwnerDTO";
    }
}
