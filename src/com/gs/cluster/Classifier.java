/**
 * GS
 */
package com.gs.cluster;
import java.io.File;

/**
 * @author GaoShen
 * @packageName com.gs.cluster
 */
public interface Classifier {
	public String classify(File file);
	public String classify(String article);
	public void train(File trainDirectory);
	public String[] getClasses();
	public Classifier getInstance();
}
