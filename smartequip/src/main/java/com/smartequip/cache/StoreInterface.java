package com.smartequip.cache;

import java.util.Optional;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
public interface StoreInterface {

	public void addItem(String token,Smartequip smartequip);
	public Optional<Smartequip> getItem(String token);
	public boolean findItem(String token);
	public void deleteItem(String token);
}
