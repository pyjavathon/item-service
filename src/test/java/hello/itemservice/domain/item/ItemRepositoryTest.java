package hello.itemservice.domain.item;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ItemRepositoryTest {
	
	ItemRepository itemRepository = new ItemRepository();
	
	@AfterEach
	void afterEach() {
		itemRepository.clearStore();
	}
	
	@Test
	void save() {
		//given
		Item item = new Item("itemA",10000,10);
		
		//when
		Item savedItem = itemRepository.save(item);
		
		//then
		Item findItem = itemRepository.findById(item.getId());
		Assertions.assertThat(findItem).isEqualTo(savedItem);
		
	}
	
	@Test
	void findAll() {
		//given
		Item item1 = new Item("itemA",1000,10);
		Item item2 = new Item("itemB",2000,20);
		
		//when
		itemRepository.save(item1);
		itemRepository.save(item2);
		
		//then
		List<Item> result = itemRepository.findAll();
		Assertions.assertThat(result.size()).isEqualTo(2);
		Assertions.assertThat(result).contains(item1,item2);
	}
	
	@Test
	void updtaeItem() {
		//given
		Item item1 = new Item("itemA",1000,20);
		Item savedItem = itemRepository.save(item1);
		Long itemId = savedItem.getId();
		
		//when
		Item updateParam = new Item("itemA",3000,40);
		itemRepository.update(itemId,updateParam);
		
		//then
		Item findItem = itemRepository.findById(itemId);
		
		Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
		Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
	}

}
