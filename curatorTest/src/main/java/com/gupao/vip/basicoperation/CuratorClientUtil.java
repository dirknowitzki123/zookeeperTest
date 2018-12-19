package com.gupao.vip.basicoperation;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClientUtil {
	private static CuratorFramework curatorFramework;
	private final static String CONNECTSTRING="127.0.0.1:2181";

	//创建会话
	public static CuratorFramework getInstance(){
		//普通方式
		/*curatorFramework = CuratorFrameworkFactory
				.newClient(CONNECTSTRING, 5000, 5000,
						new ExponentialBackoffRetry(1000,3));
		curatorFramework.start();
		return curatorFramework;*/

		//fluent风格
		curatorFramework = CuratorFrameworkFactory.builder().connectString(CONNECTSTRING)
				.sessionTimeoutMs(5000).connectionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000,3))
				.build();
		curatorFramework.start();
		return curatorFramework;
	}
}
