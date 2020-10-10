package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm form) {
    Book book = Book.from(form);
    itemService.saveItem(book);
    return "redirect:/items";
  }

  @GetMapping("/items")
  public String list(Model model) {
    model.addAttribute("items", itemService.findItems());
    return "/items/itemList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable Long itemId, Model model) {
    Book book = (Book) itemService.findOne(itemId);
    BookForm bookForm = BookForm.from(book);
    model.addAttribute("form", bookForm);
    return "items/updateItemForm";
  }

  @PostMapping("/items/{itemId}/edit")
  public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
//    Book book = Book.from(form);
//    itemService.saveItem(book);
//    위처럼 싹 모는것 보다 아래처럼 필요한 값만 보내는게 맞음. VO로 묶든가.
    itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
    return "redirect:/items";
  }

}
