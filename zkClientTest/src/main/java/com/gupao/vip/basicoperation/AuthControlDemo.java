package com.gupao.vip.basicoperation;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AuthControlDemo {
	private final static String CONNECTSTRING="127.0.0.1:2181";

	private static ZkClient getInstance(){
		return new ZkClient(CONNECTSTRING, 10000);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		ZkClient zkClient = getInstance();

		//创建ACL
		List<ACL> aclList = new ArrayList<>();
		aclList.add(new ACL(ZooDefs.Perms.ALL, new Id("digest",
				DigestAuthenticationProvider.generateDigest("admin:123456"))));
		//创建节点
		zkClient.createPersistent("/zkClientAuthTest/zkClientAuthTest1", true, aclList);
		//增加权限
		zkClient.addAuthInfo("digest", "admin:123456".getBytes());
		//zkClient.setAcl("/zkClientAuthTest", aclList);
		//写数据
		zkClient.writeData("/zkClientAuthTest/zkClientAuthTest1", "heihei", -1);
		System.out.println(zkClient.readData("/zkClientAuthTest/zkClientAuthTest1"));
	}
}
