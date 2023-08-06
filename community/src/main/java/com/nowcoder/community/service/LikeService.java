package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId, int entityType, int entityId, int entityUserId){

        //编程式事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                //redis在开启和提交事务之间是把命令放在消息队列中，不会立即执行，故需要将查询放在事务开启提交范围之外
                Boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                //开启事务
                operations.multi();
                if (isMember) {
                    //如果userId在已点赞集合里面，则将该userId从集合当中移除
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    //如果userId不在已点赞集合里面，则在集合当中添加该userId
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }
                //提交事务
                return operations.exec();
            }
        });
    }

    //查询某实体的点赞数量
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    //查询某个用户的赞
    public int findUserLikeCount(int userId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();

    }

}
