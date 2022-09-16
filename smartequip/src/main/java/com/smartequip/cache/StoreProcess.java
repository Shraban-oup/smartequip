package com.smartequip.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

@Component
public class StoreProcess implements StoreInterface {

	public static ConcurrentHashMap<String, Smartequip> store = new ConcurrentHashMap<>();

	@Override
	public void addItem(String token, Smartequip smartequip) {
		store.put(token, smartequip);
	}

	@Override
	public Optional<Smartequip> getItem(String token) {
		if (findItem(token)) {
			return Optional.ofNullable(store.get(token));
		}
		return Optional.empty();
	}

	@Override
	public boolean findItem(String token) {
		return store.containsKey(token);

	}

	@Override
	public void deleteItem(String token) {
		if (findItem(token)) {
			store.remove(token);
		}
	}

}