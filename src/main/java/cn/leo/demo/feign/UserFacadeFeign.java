package cn.leo.demo.feign;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserFacadeFeign {
    private static Log log = LogFactory.getLog(UserFacadeFeign.class);


    public List<String> getGroupsByUser(String userId) {
        log.info("userId:" + userId + ".");
        LinkedList<String> list = new LinkedList<>();
        if("user222".equals(userId) || "user111".equals(userId))
        {
            list.add("group1");
        }
        return list;
    }
}
