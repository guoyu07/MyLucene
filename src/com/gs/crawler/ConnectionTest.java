/**
 * GS
 */
package com.gs.crawler;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class ConnectionTest {
	/**
	 * @param url the url to be tested
	 * @param time the limit of timeout
	 * @return
	 */
	public boolean test(String url,int time){
		ConnectThread thread = null;
		try {
			thread = new ConnectThread();
			thread.setUrl(url);
			thread.start();
			long start = System.currentTimeMillis();
			int use =0;
			while(use < time ){
				long now = System.currentTimeMillis();
				use = (int) (now - start);
				if(!thread.isAlive()){break;}
			}
			if (thread.isAlive()) {
				thread.stop();
				System.out.println("Interrupted! Bad Connection");
				thread.releaseConnection();
				return false;
			}
			thread.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}
