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

import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class StoreProcessTest {

	@InjectMocks
	private StoreProcess process;

	public static ConcurrentHashMap<String, Smartequip> storeItem = new ConcurrentHashMap<>();

	public static Smartequip smartequip;
	public static String generateToken;

	@BeforeAll
	public static void init() {

		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		smartequip = new Smartequip(questionNums, 30);
		generateToken = new TokenGenerator().generateToken(smartequip);
		storeItem.put(generateToken, smartequip);
		ReflectionTestUtils.setField(new StoreProcess(), "store", storeItem);
	}

	@Test
	@Order(1)
	void addItemTest() {

		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(10);
		questionNums1.add(15);
		questionNums1.add(15);
		Smartequip smartequip1 = new Smartequip(questionNums1, 40);
		String generateToken1 = new TokenGenerator().generateToken(smartequip1);
		process.addItem(generateToken1, smartequip1);
		assertEquals(storeItem.get(generateToken1).getAnsewer(), smartequip1.getAnsewer());
	}

	@Test
	void getItemTest_invalidToken() {
		Optional<Smartequip> item = this.process.getItem("1234");
		assertFalse(item.isPresent());
	}

	@Test
	@Order(2)
	void getItemTest_validToken() {
		Optional<Smartequip> result = this.process.getItem(generateToken);
		assertEquals(30, result.get().getAnsewer());

	}

	@Test
	@Order(3)
	void findItemTest() {
		boolean item = this.process.findItem(generateToken);
		assertEquals(true, item);
	}

	@Test
	@Order(4)
	void deleteItemTest() {
		this.process.deleteItem(generateToken);
		assertEquals(false, storeItem.containsKey(generateToken));
	}
}
