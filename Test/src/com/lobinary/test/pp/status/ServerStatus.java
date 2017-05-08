package com.lobinary.test.pp.status;

public class ServerStatus {
	
	public static void main(String[] args) {
		new 检测状态线程("cashier:","http://localhost:9403/cashier/index.jsp").start();
		new 检测状态线程("account:","http://localhost:9302/pp-account-hessian/index.jsp").start();
		new 检测状态线程("boss:\t","http://localhost:9401/pp-boss/index.jsp").start();
		new 检测状态线程("config:\t","http://localhost:9303/pp-config-hessian/index.jsp").start();
		new 检测状态线程("order:\t","http://localhost:9402/pp-order-hessian/index.jsp").start();
		new 检测状态线程("router:\t","http://localhost:9404/pp-router-hessian/index.jsp").start();
		new 检测状态线程("user:\t","http://localhost:9301/pp-user-hessian/index.jsp").start();
		new 检测状态线程("portal:\t","http://localhost:9403/pp-portal/index.jsp").start();
	}
	
}
