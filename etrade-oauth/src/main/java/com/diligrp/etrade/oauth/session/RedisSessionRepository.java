package com.diligrp.etrade.oauth.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * Session仓库Redis实现
 *
 * @author: brenthuang
 * @date: 2018/01/04
 */
public class RedisSessionRepository implements ISessionRepository {
    private static Logger LOG = LoggerFactory.getLogger(RedisSessionRepository.class);

    private static final String ICARD_JSESSION_PREFIX = "etrade:oauth:accessToken:";

    /**
     * Session序列化工具类
     */
    private ISessionSerializer sessionSerializer = new ProtocolStuffSerializer();

    /**
     * Jedis连接池配置
     */
    private JedisDataSource dataSource;

    @Override
    public SharedHttpSession loadSession(String sessionId, int maxInactiveInterval) {
        try (Jedis connection = dataSource.getConnection()) {
            Pipeline transaction = connection.pipelined();
            byte[] sessionKey = sessionSerializer.serializeKey(ICARD_JSESSION_PREFIX + sessionId);
            Response<byte[]> response = transaction.get(sessionKey);
            transaction.expire(sessionKey, maxInactiveInterval);
            transaction.sync();
            byte[] data = response.get();
            return data == null ? null : sessionSerializer.deserializeSession(data);
        } catch (Exception ex) {
            LOG.error("Redis loadSession exception", ex);
        }

        return null;
    }

    @Override
    public void saveSession(SharedHttpSession session, int maxInactiveInterval) {
        try (Jedis connection = dataSource.getConnection()) {
            Pipeline transaction = connection.pipelined();
            byte[] sessionKey = sessionSerializer.serializeKey(ICARD_JSESSION_PREFIX + session.getId());
            byte[] data = sessionSerializer.serializeSession(session);
            transaction.set(sessionKey, data);
            transaction.expire(sessionKey, maxInactiveInterval);
            transaction.sync();
        } catch (Exception ex) {
            LOG.error("Redis saveSession exception", ex);
        }
    }

    @Override
    public void removeSession(SharedHttpSession session) {
        try (Jedis connection = dataSource.getConnection()) {
            byte[] sessionKey = sessionSerializer.serializeKey(ICARD_JSESSION_PREFIX + session.getId());
            connection.del(sessionKey);
        } catch (Exception ex) {
            LOG.error("Redis removeSession exception", ex);
        }
    }

    @Override
    public boolean sessionExists(String sessionId)
    {
        try (Jedis connection = dataSource.getConnection()) {
            byte[] sessionKey = sessionSerializer.serializeKey(ICARD_JSESSION_PREFIX + sessionId);
            return connection.exists(sessionKey);
        } catch (Exception ex) {
            LOG.error("Redis sessionExists exception", ex);
        }

        return false;
    }

    public void setSessionSerializer(ISessionSerializer sessionSerializer) {
        this.sessionSerializer = sessionSerializer;
    }

    public void setDataSource(JedisDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
