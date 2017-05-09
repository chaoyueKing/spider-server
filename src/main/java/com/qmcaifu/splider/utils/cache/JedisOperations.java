package com.qmcaifu.splider.utils.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPubSub;

public interface JedisOperations {

	/**
	 * Redis基本（键）操作 删除
	 */
	public Long del(String... keys);

	/**
	 * Redis基本（键）操作 获取key的存储值类型
	 */
	public String type(String key);

	/**
	 * Redis基本（键）操作 获取键列表
	 */
	public Set<String> keys(String pattern);

	/**
	 * Redis基本（键）操作 查看某个key的剩余生存时间:time to live
	 * 
	 * @return 当 key 不存在或没有设置生存时间时，返回 -1 。否则，返回 key 的剩余生存时间(以秒为单位)。
	 */
	public Long ttl(String key);

	/**
	 * Redis基本（键）操作 查看某个key是否存在
	 */
	public Boolean exists(String key);

	/**
	 * Redis基本（键）操作 设置某一记录的生存时间，过期删除
	 * 
	 * @param key
	 *            设置可挥发的key
	 * @param seconds
	 *            超时时间，单位：秒
	 * @return
	 */
	public Long expire(String key, int seconds);

	/**
	 * Redis基本（键）操作 移除给定 key 的生存时间，将这个 key从可挥发的(带生存时间 key
	 * )转换成持久化的(一个不带生存时间、永不过期的 key )
	 */
	public Long persist(String key);

	/**
	 * Redis基本（字符串）操作 将设置某个key的值，如果 key 已经持有其他值，就覆写旧值，无视类型。
	 */
	public String set(String key, String value);

	/**
	 * Redis基本（字符串）操作 将 key 的值设为 value ，当且仅当 key不存在，若给定的 key 已经存在，则 SETNX
	 * 不做任何动作。 set not exists
	 */
	public Long setnx(String key, String value);

	/**
	 * Redis基本（字符串）操作 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
	 */
	public String setex(String key, int seconds, String value);

	/**
	 * Redis基本（字符串）操作 返回 key 所关联的字符串值。
	 */
	public String get(String key);

	/**
	 * 将 key 中储存的数字值增一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 */
	public Long incr(String key);

	/**
	 * 将 key 中储存的数字值增加固定制。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 */
	public Long incrBy(String key, long increase);

	/**
	 * 将 key 中储存的数字值减一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 */
	public Long decr(String key);

	/**
	 * 将 key 中储存的数字值减少固定制。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 */
	public Long decrBy(String key, long decrease);

	/**
	 * Hash操作
	 */
	public Long hset(String hkey, String field, String value);

	public Long hsetnx(String hkey, String field, String value);

	public String hmset(String hkey, Map<String, String> hash);

	public String hget(String hkey, String field);

	public Map<String, String> hgetAll(String hkey);

	public Long hdel(String hkey, String field);

	public Long hlen(String hkey);

	public Boolean hexists(String hkey, String field);

	public Long hincrBy(String hkey, String field, long increase);

	public Set<String> hkeys(String hkey);

	public List<String> hvals(String hkey);

	/**
	 * List操作
	 */
	public Long lpush(String lkey, String value);

	public Long rpush(String lkey, String value);

	public String lpop(String lkey);

	public String rpop(String lkey);

	public Long llen(String lkey);

	public List<String> lrange(String lkey, int start, int end);

	public Long lrem(String lkey, int count, String value);

	public String lset(String lkey, long index, String value);

	/**
	 * Set操作
	 */
	public Long sadd(String skey, String member);

	public Long srem(String skey, String member);

	public Set<String> smembers(String skey);

	public Boolean sismember(String skey, String member);

	public Long scard(String skey);

	public String spop(String skey);

	public Set<String> sinter(String... skeys);

	public Set<String> sunion(String... skeys);

	public Set<String> sdiff(String... skeys);

	/**
	 * Sorted set操作（有序集）
	 */
	public Long zadd(String zkey, double score, String member);

	public Long zrem(String zkey, String member);

	public Long zcard(String zkey);

	public Long zcount(String zkey, double min, double max);

	public Double zscore(String zkey, String member);

	public Double zincrby(String zkey, double score, String member);

	public Set<String> zrange(String zkey, int start, int end);

	public Set<String> zrevrange(String zkey, int start, int end);

	public Set<String> zrangeByScore(String zkey, double min, double max);

	public Set<String> zrangeByScore(String zkey, String min, String max);

	public Long zrank(String zkey, String member);

	public Long zrevrank(String zkey, String member);

	public Long zremrangeByScore(String zkey, double start, double end);

	public Long zremrangeByScore(String zkey, String start, String end);

	/**
	 * Pub/Sub操作
	 */
	public Long publish(String channel, String message);

	public void subscribe(JedisPubSub pubSub, String... channels);
}
