package cn.leo.demo.activiti.custom.manager;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class CustomUserEntityManager implements UserIdentityManager, Session {
    private static Log log = LogFactory.getLog(CustomUserEntityManager.class);

    @Override
    public User createNewUser(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(User updatedUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findUserById(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        log.info("查询用户group, userId:" + userId + ".");
        // 查询自己的业务系统
        if("user222".equals(userId) || "user111".equals(userId))
        {
            LinkedList<Group> groups = new LinkedList<>();
            Group group = new GroupEntity();
            group.setId("group1");
            groups.add(group);
            return groups;
        }
        return null;
    }

    @Override
    public UserQuery createNewUserQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNewUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Picture getUserPicture(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserPicture(String userId, Picture picture) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
