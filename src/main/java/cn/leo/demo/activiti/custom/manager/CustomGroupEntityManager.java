package cn.leo.demo.activiti.custom.manager;

import org.activiti.engine.identity.*;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class CustomGroupEntityManager implements GroupIdentityManager, Session {
    private static Log log = LogFactory.getLog(CustomGroupEntityManager.class);

    @Override
    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertGroup(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateGroup(Group updatedGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        log.info("查询用户groups, userId:" + userId + ".");
        LinkedList<Group> groups = new LinkedList<>();
        return groups;
    }

    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNewGroup(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
