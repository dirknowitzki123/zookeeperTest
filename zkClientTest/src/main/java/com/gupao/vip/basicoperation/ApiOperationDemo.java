package com.gupao.vip.basicoperation;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApiOperationDemo {
	private final static String CONNECTSTRING="127.0.0.1:2181";

	private static ZkClient getInstance(){
		return new ZkClient(CONNECTSTRING, 10000);
	}

	public static void main(String[] args) throws InterruptedException {
		ZkClient zkClient = getInstance();
		//创建永久节点
		/*zkClient.createPersistent("/zkclientTest/zkclientTest1/zkclientTest11", true);

		//set值
		zkClient.writeData("/zkclientTest", "zkclient", -1);

		//get值
		System.out.println(zkClient.readData("/zkclientTest"));

		//获取子节点
		List<String> children = zkClient.getChildren("/zkclientTest");
		System.out.println("children is: " + children);*/

		//watcher机制
		//订阅子节点变化
		/*zkClient.subscribeChildChanges("/zkclientTest", new IZkChildListener() {
			@Override
			public void handleChildChange(String s, List<String> list) throws Exception {
				System.out.println("子节点发生变化"+s+"list is:"+list);
			}
		});

		zkClient.createPersistent("/zkclientTest/zkclientTest1", true);
		zkClient.writeData("/zkclientTest/zkclientTest1", "duxin", -1);
		TimeUnit.SECONDS.sleep(5);*/

		//watcher机制
		//订阅节点数据变化
		zkClient.subscribeDataChanges("/zkclientTest", new IZkDataListener() {
			@Override
			public void handleDataChange(String s, Object o) throws Exception {
				System.out.println("发生变化的节点是: "+s+"  变化之后的值是: "+ o.toString());
			}

			@Override
			public void handleDataDeleted(String s) throws Exception {
				System.out.println("被删除的节点是: "+s);
			}
		});
		zkClient.writeData("/zkclientTest", "xixi", -1);
		TimeUnit.SECONDS.sleep(5);
		zkClient.deleteRecursive("/zkclientTest");
		TimeUnit.SECONDS.sleep(5);


	}
}
