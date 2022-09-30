package com.smartequip.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class StoreProcessImplTest {

	@InjectMocks
	private StoreProcessImpl process;

	public static ConcurrentHashMap<Smartequip, Integer> storeItem = new ConcurrentHashMap<>();

	public static Smartequip smartequip;
	public static String generateToken;
	private static List<Integer> digitList;
	public static int answer;

	/**
	 * This will initiate before any test case runs.
	 */
	@BeforeAll
	public static void init() {
		digitList = new ArrayList<>();
		digitList.add(10);
		digitList.add(5);
		digitList.add(15);
		generateToken = "123456789103209-1232323";
		answer = 30;
		smartequip = new Smartequip(digitList, generateToken);
		storeItem.put(smartequip, answer);
		ReflectionTestUtils.setField(new StoreProcessImpl(), "cache", storeItem);

	}

	/**
	 * This test will execute first. This will test for addition in cache
	 */
	@Test
	@Order(1)
	void addItemTest() {
		String newToken = "12345670987722";
		Smartequip smartequipNew = new Smartequip(digitList, newToken);
		process.addItem(smartequipNew, answer);
		assertEquals(storeItem.get(smartequipNew), answer);
	}

	/**
	 * This test will execute 2nd. This will test for get value by invalid key.
	 */
	@Test
	@Order(2)
	void getItemTest_invalidToken() {
		String newToken = "12345dweqwe33434";
		Smartequip smartequipNew = new Smartequip(digitList, newToken);
		Optional<Integer> item = this.process.getItem(smartequipNew);
		assertFalse(item.isPresent());
	}

	/**
	 * This test will execute 3rd. This will test for get value by valid key.
	 */
	@Test
	@Order(3)
	void getItemTest_validToken() {
		Optional<Integer> result = this.process.getItem(smartequip);
		assertEquals(answer, result.get());

	}

	/**
	 * This test will execute 4rd. This will test to delete records.
	 */
	@Test
	@Order(4)
	void deleteItemTest() {
		this.process.deleteItem(smartequip);
		assertFalse(storeItem.containsKey(smartequip));
	}
}
