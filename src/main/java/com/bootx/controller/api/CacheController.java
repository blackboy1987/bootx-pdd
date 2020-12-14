
package com.bootx.controller.api;

import com.bootx.controller.admin.BaseController;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("apiCacheController")
@RequestMapping("/api/cache")
public class CacheController extends BaseController {

	/**
	 * CacheManager
	 */
	private static final CacheManager CACHEMANAGER = CacheManager.create();

	/**
	 * 清除缓存
	 */
	@GetMapping("/info")
	public Map<String,List<Map<String,String>>> info() {
		Map<String,List<Map<String,String>>> caches = new HashMap<>();
		String[] cacheNames = CACHEMANAGER.getCacheNames();
		for (String cacheName:cacheNames) {
			Cache cache = CACHEMANAGER.getCache(cacheName);
			if(cache==null){
				continue;
			}
			List keys = cache.getKeys();
			List<Map<String,String>> cacheList = new ArrayList<>();
			for (Object key:keys) {
				Map<String,String> map = new HashMap<>();
				map.put(key+"",cache.get(key).getObjectValue()+"");
				cacheList.add(map);
			}
			caches.put(cacheName,cacheList);
		}

		return caches;


	}
}