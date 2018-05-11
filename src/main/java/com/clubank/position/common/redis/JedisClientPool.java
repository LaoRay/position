package com.clubank.position.common.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.util.SafeEncoder;

/**
 * jedis缓存连接池工具类
 *
 */
public class JedisClientPool implements JedisClient {

	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(keyToBytes(key), valueToBytes(value));
		jedis.close();
		return result;
	}
	
	@Override
	public Long setnx(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.setnx(keyToBytes(key), valueToBytes(value));
		jedis.close();
		return result;
	}
	
	@Override
	public Long setnxNoEncode(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.setnx(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = valueFromBytes(jedis.get(keyToBytes(key)));
		jedis.close();
		return result;
	}
	
	@Override
	public String getNoEncode(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(keyToBytes(key));
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(keyToBytes(key), seconds);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(keyToBytes(key));
		jedis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(keyToBytes(key));
		jedis.close();
		return result;
	}

	@Override
	public Long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(keyToBytes(key));
		jedis.close();
		return result;
	}

	@Override
	public String watch(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.watch(keyToBytes(key));
		jedis.close();
		return result;
	}

	@Override
	public Transaction multi() {
		Jedis jedis = jedisPool.getResource();
		Transaction transaction = jedis.multi();
		return transaction;
	}
	
	@Override
	public Long lpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.lpush(keyToBytes(key), valueToBytes(value));
		jedis.close();
		return result;
	}
	
	@Override
	public Long rpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.rpush(keyToBytes(key), valueToBytes(value));
		jedis.close();
		return result;
	}
	
	@Override
	public String rpop(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = valueFromBytes(jedis.rpop(keyToBytes(key)));
		jedis.close();
		return result;
	}
	
	@Override
	public boolean ping() {
		Jedis jedis = null;
		boolean result = true;
		try {
			jedis = jedisPool.getResource();
			result = "PONG".equals(jedis.ping());
		} catch (Exception e) {
			result = false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	@Override
	public int llen(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result;
		try {
			result = jedis.llen(keyToBytes(key));
		} catch (Exception e) {
			result = (long) 0;
		} finally {
			jedis.close();
		}
		return result.intValue();
	}
	
	@Override
	public void incrByNoEncode(String key, int increment) {
		Jedis jedis = null;
		if (!exists(key)) {
			setnxNoEncode(key, "0");
		}
		jedis = jedisPool.getResource();
		watch(key);
		Transaction tx = jedis.multi(); // 开启事务
		tx.incrBy(key, increment);
		tx.exec(); // 提交事务
		jedis.close();
	}

	@Override
	public void decrByNoEncode(String key, int increment) {
		Jedis jedis = null;
		if (exists(key)) {
			jedis = jedisPool.getResource();
			watch(key);
			Transaction tx = jedis.multi(); // 开启事务
			tx.decrBy(key, increment);
			tx.exec(); // 提交事务
			jedis.close();
		}
	}

	@Override
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 转换key为二进制
	 * @param key
	 * @return
	 */
	private byte[] keyToBytes(String key) {
		return SafeEncoder.encode(key);
	}
	
	/**
	 * 转换value为二进制
	 * @param value
	 * @return
	 */
	private byte[] valueToBytes(Object value) {
		ObjectOutputStream objectOut = null;
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			objectOut = new ObjectOutputStream(bytesOut);
			objectOut.writeObject(value);
			objectOut.flush();
			return bytesOut.toByteArray();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if(objectOut != null)
				try {objectOut.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	/**
	 * 转换二进制value为string
	 * @return
	 */
	private String valueFromBytes(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		
		ObjectInputStream objectInput = null;
		try {
			ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes);
			objectInput = new ObjectInputStream(bytesInput);
			return objectInput.readObject().toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (objectInput != null)
				try {objectInput.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
}
