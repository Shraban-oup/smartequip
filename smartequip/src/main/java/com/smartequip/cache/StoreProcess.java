package com.smartequip.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 * 
 *         Storing token as Key until Human verification of client, then
 *         entrySet deleted.
 */
@Component
public class StoreProcess implements StoreInterface {

	public static ConcurrentHashMap<String, Smartequip> store = new ConcurrentHashMap<>();

	/**
	 * This will add token , smart equip object in cache.
	 */
	@Override
	public void addItem(String token, Smartequip smartequip) {
		store.put(token, smartequip);
	}

	/**
	 * This will get Value based on Key as Token.
	 */
	@Override
	public Optional<Smartequip> getItem(String token) {
		if (findItem(token)) {
			return Optional.ofNullable(store.get(token));
		}
		return Optional.empty();
	}

	/**
	 * This will verify token that is it exits or not
	 */
	@Override
	public boolean findItem(String token) {
		return store.containsKey(token);

	}

	/**
	 * This will delete entire entrySet
	 */
	@Override
	public void deleteItem(String token) {
		if (findItem(token)) {
			store.remove(token);
		}
	}

}