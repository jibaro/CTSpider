package com.ctbri.spider.puter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Spider;

import com.ctbri.spider.cache.CacheHandler;
import com.ctbri.spider.cache.SystemConstants;
import com.ctbri.spider.utils.FileLoadTools;

/**
 * 
 *  Copyright 2014 SOGOU
 *  All right reserved.
 *	<p>
 *	The file tracker to track the file, if the menu of one product modified,
 *  the product will load again to update the information.
 *	</p>
 * @author Qun He
 * @Creat Time : 2014-9-2 下午2:51:12
 * @VersionFileTracker
 */
public class ShortageItemsLoader implements Runnable{

	private int period = 1000*3600*6;//1000 seconds
	private Spider spider = null;
	
	private static Logger logger = Logger.getLogger(ShortageItemsLoader.class);
		
	public ShortageItemsLoader(int period, Spider spider) {
		super();
		this.period = period;
		this.spider = spider;
	}

	@Override
	public void run() {
		File fileR = new File(SystemConstants.properties.getProperty(SystemConstants.SAVE_LOCATION)+"/RetriedItems");
		if(!fileR.exists()) fileR.mkdir();
		
		while(true){
			try {
				File[] filesSPrice = FileLoadTools.getFilesByDirectory(SystemConstants.properties.getProperty(SystemConstants.SAVE_LOCATION)+"/ShortOfPrice");
				File[] filesSAll = FileLoadTools.getFilesByDirectory(SystemConstants.properties.getProperty(SystemConstants.SAVE_LOCATION)+"/ShortOfAll");
				
				File[] needRecrawl = ArrayUtils.addAll(filesSPrice, filesSAll);
				
				synchronized (CacheHandler.readWriteLock) {
					for (File file : needRecrawl) {
						String line = null;
						BufferedReader fileReader = new BufferedReader(new FileReader(file));
						while ((line = fileReader.readLine()) != null) {
							String[] items = line.split(" ");
							spider.addUrl("http://item.jd.com/"+items[0]+".html");
							logger.info("Adding Failure Url to Scheduler :"+ items[0]);
						}
						fileReader.close();
						FileUtils.copyFileToDirectory(file, fileR);
						file.delete();
					}
				}
				Thread.sleep(period);
			} catch (Exception e) {
				logger.error("Error happens",e);
			}
		}
	}
}