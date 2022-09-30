package com.smartequip.cache;

import java.util.Optional;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
public interface StoreProcess {

	void addItem(Smartequip smartequip, int answer);

	Optional<Integer> getItem(Smartequip smartequip);

	void deleteItem(Smartequip smartequip);
}
