package com.clubank.position.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * jedis接口
 *
 */
public interface JedisClient {

	/**
	 * 设置指定 key 的值
	 * @return
	 */
	String set(String key, String value);
	
	/**
	 * 只有在 key 不存在时设置 key 的值
	 * @return
	 */
	Long setnx(String key, String value);
	
	/**
	 * 只有在 key 不存在时设置 key 的值(不转变二进制)
	 * @return
	 */
	Long setnxNoEncode(String key, String value);
	
	/**
	 * 获取指定 key 的值
	 * @return
	 */
	String get(String key);
	
	/**
	 * 获取指定 key 的值(不转变二进制)
	 * @return
	 */
	String getNoEncode(String key);
	
	/**
	 * 该命令用于在 key 存在时删除 key
	 * @return
	 */
	Long del(String key);

	/**
	 * 检查给定 key 是否存在
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 为给定 key 设置过期时间
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
	 * @return
	 */
	Long ttl(String key);

	/**
	 * 将 key 中储存的数字值增一
	 * @return
	 */
	Long incr(String key);

	/**
	 * 监视一个key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断
	 * @param keys
	 * @return
	 */
	String watch(String key);
	
	/**
	 * 标记一个事务块的开始
	 * @return
	 */
	Transaction multi();
	
	/**
	 * 将一个或多个值插入到列表头部
	 * @return
	 */
	Long lpush(String key, String value);
	
	/**
	 * 在列表中添加一个或多个值
	 * @return
	 */
	Long rpush(String key, String value);
	
	/**
	 * 移除并获取列表最后一个元素
	 * @return
	 */
	String rpop(String key);
	
	/**
	 * 返回列表的长度
	 * @return
	 */
	int llen(String key);
	
	/**
	 * 检查服务是否运行
	 * @return
	 */
	boolean ping();
	
	/**
	 * 将 key 中储存的数字值加上给定的增量值
	 * @return
	 */
	void incrByNoEncode(String key, int increment);
	
	/**
	 * 将 key 中储存的数字值减去给定的增量值
	 * @return
	 */
	void decrByNoEncode(String key, int increment);
	
	Jedis getJedis();
}
