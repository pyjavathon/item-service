package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

	private static final Map<Long, Item> store = new HashMap<>();//ConcurrentHashMap<K, V> 실무에선 얘 사용해야 함 멀티스레드 환경에서 꼬일 수 있어서
	private static long sequence = 0L; // 얘도 마찬가지 atomicLong 써야함
	
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
	}
	
	public Item findById(Long id) {
		return store.get(id);
	}
	
	public List<Item> findAll(){
		return new ArrayList<>(store.values());
	}
	
	public void update(Long itemId, Item updateParam) {
		Item findItem = findById(itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}
	
	public void clearStore() {
		store.clear();
	}
}
