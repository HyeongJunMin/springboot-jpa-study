package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  public Item findOne(Long id) {
    return itemRepository.findOne(id);
  }

  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  @Transactional
  public void updateItem(Long itemId, String name, int price, int stockQuantity) {
    Item findItem = itemRepository.findOne(itemId);
    findItem.setName(name);
    findItem.setPrice(price);
    findItem.setStockQuantity(stockQuantity);
    // merge, save 호출 필요없음. 영속상태이기 때문
  }
}
