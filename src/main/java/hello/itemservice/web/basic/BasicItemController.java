package hello.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;

@Controller
@RequestMapping("basic/items")
public class BasicItemController {

	private final ItemRepository itemRepository;
	
	@Autowired
	public BasicItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}
	
	@GetMapping("{itemId}")
	public String item(@PathVariable long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	@GetMapping("/add")
	public String addForm() {
		return "/basic/addForm";
	}
	
	
	
	//@PostMapping("/add")
	public String addItem1(@RequestParam String itemName,
					   @RequestParam int price,
					   @RequestParam int quantity,
					   Model model) {
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quantity);
		
		itemRepository.save(item);
		
		model.addAttribute("item", item);
		
		return "/basic/item";
	}
	
	@PostMapping("/add")
	public String addItem2(@ModelAttribute("item") Item item) {
		
		itemRepository.save(item);
		
		//model.addAttribute("item", item); 자동 추가 되므로 생략가능
		
		return "redirect:/basic/items/" + item.getId();
	}
	
	@PostMapping("/add")
	public String addItem3(Item item, RedirectAttributes redirectAttributes) {
		
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		
		return "redirect:/basic/items/{itemId}";
	}
	
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}
	
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
		itemRepository.update(itemId,item);
		return "redirect:/basic/items/{itemId}";
	}
	
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("itemA",1000,10));
		itemRepository.save(new Item("itemB",2000,20));
	}
}
