package com.smartequip.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class StoreProcessTest {

	@Spy
	@InjectMocks
	private StoreProcess process;

	public static ConcurrentHashMap<String, Smartequip> storeItem = new ConcurrentHashMap<>();

	public static Smartequip smartequip;
	public static String generateToken;

	@BeforeAll
	public static void init() {
		generateToken = new TokenGenerator().generateToken();
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		smartequip = new Smartequip(questionNums, 30);
		storeItem.put(generateToken, smartequip);
		ReflectionTestUtils.setField(new StoreProcess(), "store", storeItem);
	}

	@Test
	void addItemTest() {
		process.addItem(generateToken, smartequip);
		verify(process, times(1)).addItem(generateToken, smartequip);
	}

	@Test
	void getItemTest_invalidToken() {
		assertEquals(false, process.getItem("1234").isPresent());
	}

	@Test
	void getItemTest_validToken() {
		Optional<Smartequip> result = this.process.getItem(generateToken);
		assertEquals(30, result.get().getAnsewer());

	}

	@Test
	void deleteItemTest() {
		this.process.deleteItem(generateToken);
		verify(this.process, Mockito.times(1)).deleteItem(generateToken);
	}
}
