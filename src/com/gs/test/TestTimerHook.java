/**
 * GS
 */
package com.gs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gs.utils.TimerHook;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestTimerHook {

	@Test
	public void test() {
		TimerHook hook = new TimerHook(new TestThread(), new Worker(), 6000);
	}

}
