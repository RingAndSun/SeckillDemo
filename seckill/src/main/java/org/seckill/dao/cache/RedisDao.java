package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


//該dao需要自己注入
public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }
    public Seckill getSeckill(long seckillId){
        //redis操作
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckillId;
                //並沒有實現內部序列化操作
                //get->byte[]->反序列化->Object(Seckill)
                //採用自定義序列化
                //Protostuff:對象必須是pojo
                byte[] bytes=jedis.get(key.getBytes());
                //緩存重穫取到
                if(bytes!=null){
                    //空對象
                    Seckill seckill=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill被反序列了
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    public String putSeckill(Seckill seckill){
        //set Object(Seckill)->序列化->byte[]
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckill.getSeckillId();
                byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int tomeout=60*60;
                String result=jedis.setex(key.getBytes(),tomeout,bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }
}
