package com.example.demo.messagingredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiverRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverRedis.class);
    private AtomicInteger counter = new AtomicInteger(); // Counter

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}

/*
FIXME: Commented-out code for Messaging with Redis of this exception.

021-06-08 18:49:18.256  INFO 71796 --- [           main] com.example.demo.DemoApplication         : Sending message...
Exception in thread "main" org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to localhost:6379
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1621)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1529)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1315)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getConnection(LettuceConnectionFactory.java:1298)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1039)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:371)
	at org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:193)
	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)
	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:209)
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:189)
	at org.springframework.data.redis.core.RedisTemplate.convertAndSend(RedisTemplate.java:834)
	at com.example.demo.DemoApplication.main(DemoApplication.java:75)
Caused by: io.lettuce.core.RedisConnectionException: Unable to connect to localhost:6379
	at io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:78)
	at io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:56)
Caused by: io.lettuce.core.RedisConnectionException: Unable to connect to localhost:6379

	at io.lettuce.core.AbstractRedisClient.getConnection(AbstractRedisClient.java:330)
	at io.lettuce.core.RedisClient.connect(RedisClient.java:216)
	at org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.lambda$getConnection$1(StandaloneConnectionProvider.java:115)
	at java.util.Optional.orElseGet(Optional.java:267)
	at org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.getConnection(StandaloneConnectionProvider.java:115)
	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1527)
	... 11 more
Caused by: io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: localhost/127.0.0.1:6379
Caused by: io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: localhost/127.0.0.1:6379

Caused by: java.net.ConnectException: Connection refused
Caused by: java.net.ConnectException: Connection refused

	at sun.nio.ch.SocketChannelImpl.checkConnect(Native Method)
	at sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:717)
	at io.netty.channel.socket.nio.NioSocketChannel.doFinishConnect(NioSocketChannel.java:330)
	at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe.finishConnect(AbstractNioChannel.java:334)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:707)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:655)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:581)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.run(Thread.java:748) */

// Resolved the issue by starting the web server:
// After, brew install redis
// 1 - In terminal, cd /
// 2 - In terminal, cd /usr/local/Cellar/redis/6.2.4/bin
// 3 - In terminal, open redis-server (open up another terminal & start up server)
/* Last login: Thu Jun 10 16:53:50 on ttys015

The default interactive shell is now zsh.
To update your account to use zsh, please run `chsh -s /bin/zsh`.
For more details, please visit https://support.apple.com/kb/HT208050.
abenitezMBP:~ abenitez$ /usr/local/Cellar/redis/6.2.4/bin/redis-server ; exit;
66856:C 10 Jun 2021 17:02:31.973 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
66856:C 10 Jun 2021 17:02:31.973 # Redis version=6.2.4, bits=64, commit=00000000, modified=0, pid=66856, just started
66856:C 10 Jun 2021 17:02:31.973 # Warning: no config file specified, using the default config. In order to specify a config file use /usr/local/Cellar/redis/6.2.4/bin/redis-server /path/to/redis.conf
66856:M 10 Jun 2021 17:02:31.975 * Increased maximum number of open files to 10032 (it was originally set to 2560).
66856:M 10 Jun 2021 17:02:31.975 * monotonic clock: POSIX clock_gettime
                _._
           _.-``__ ''-._
      _.-``    `.  `_.  ''-._           Redis 6.2.4 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 66856
  `-._    `-._  `-./  _.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |           https://redis.io
  `-._    `-._`-.__.-'_.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |
  `-._    `-._`-.__.-'_.-'    _.-'
      `-._    `-.__.-'    _.-'
          `-._        _.-'
              `-.__.-'

66856:M 10 Jun 2021 17:02:31.976 # Server initialized
66856:M 10 Jun 2021 17:02:31.976 * Ready to accept connections */