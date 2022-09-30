package com.smartequip.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

/**
 * Storing token as Key until Human verification of client, then entrySet
 * deleted.
 * 
 * @author Shraban.Rana
 * 
 */
@Component
public class StoreProcessImpl implements StoreProcess {

	public static ConcurrentHashMap<Smartequip, Integer> cache = new ConcurrentHashMap<Smartequip, Integer>();

	/**
	 *add record
	 */
	@Override
	public void addItem(Smartequip smartequip, int answer) {
		cache.put(smartequip, answer);
	}

	/**
	 *get value if key is present. else returns null.
	 */
	@Override
	public Optional<Integer> getItem(Smartequip smartequip) {
		return Optional.ofNullable(cache.get(smartequip));
	}

	/**
	 *remove record.
	 */
	@Override
	public void deleteItem(Smartequip smartequip) {
		cache.remove(smartequip);
	}

}