package com.gupao.vip.basicoperation;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.sql.SQLOutput;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_ADDED;

public class CuratorOperationDemo {

	public static void main(String[] args) throws Exception {
		CuratorFramework curatorFramework = CuratorClientUtil.getInstance();

		//创建节点
		/*curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/curatorTest/curatorTest1");

		//set get
		curatorFramework.setData().forPath("/curatorTest", "duxin".getBytes());
		System.out.println(new String(curatorFramework.getData().forPath("/curatorTest")));

		//删除
		curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curatorTest");

		//事务操作
		try {
			Collection<CuratorTransactionResult> resultCollections = curatorFramework.inTransaction()
					.create()
					.forPath("/trans","111".getBytes())
					.and()
					.setData()
					.forPath("/curator","111".getBytes()).and().commit();
			for (CuratorTransactionResult result:resultCollections){
				System.out.println(result.getForPath()+"->"+result.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		//时间监听
		eventTest(curatorFramework);

	}

	//watcher机制
	private static void eventTest(CuratorFramework curatorFramework) throws Exception {
		/*三种watcher来做节点的监听
				* Pathcache   监视一个路径下子节点的创建、删除、节点数据更新
				* NodeCache   监视一个节点的创建、更新、删除
				* TreeCache   pathcaceh + nodecache 的合体(监视路径下的创建、更新、删除事件)，缓存路径下的所有子节点的数据 */

		//NodaCache
		/*NodeCache nodeCache = new NodeCache(curatorFramework, "/curatorTest/curatorTest1");
		nodeCache.start();
		nodeCache.getListenable().addListener(
				()-> System.out.println("节点数据变化之后的结果是"+new String(nodeCache.getCurrentData().getData()))
		);
		//set节点
		curatorFramework.create().creatingParentsIfNeeded().forPath("/curatorTest/curatorTest1");
		curatorFramework.setData().forPath("/curatorTest/curatorTest1", "dddxxx".getBytes());
		TimeUnit.SECONDS.sleep(2);*/


		//Pathcache
		PathChildrenCache pathChildrenCache =
				new PathChildrenCache(curatorFramework, "/curatorTest", false);
		pathChildrenCache.start();
		pathChildrenCache.getListenable().addListener(
				(curatorFramework1, pathChildrenCacheEvent)-> {
					switch (pathChildrenCacheEvent.getType()){
						case CHILD_ADDED:
							System.out.println("新增子节点");
							break;
						case CHILD_UPDATED:
							System.out.println("更新子节点");
							break;
						case CHILD_REMOVED:
							System.out.println("删除子节点");
							break;
						default:
							break;
					}
				}
		);
		//子节点操作
		curatorFramework.create().creatingParentsIfNeeded().forPath("/curatorTest/curatorTest1");
		TimeUnit.SECONDS.sleep(2);
		curatorFramework.setData().forPath("/curatorTest/curatorTest1", "setDate".getBytes());
		TimeUnit.SECONDS.sleep(2);
		curatorFramework.delete().forPath("/curatorTest/curatorTest1");
		TimeUnit.SECONDS.sleep(2);
	}
}
